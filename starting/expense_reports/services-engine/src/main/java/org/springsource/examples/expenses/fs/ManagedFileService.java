package org.springsource.examples.expenses.fs;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.File;
import java.util.*;

@Service
public class ManagedFileService {

	private String assetPathMask = "%010d/%010d.%s";

	private static String MANAGED_FILE_MOUNT_DIRECTORY = "managedFiles";

	private Log log = LogFactory.getLog(getClass());

	@Value("#{systemProperties['user.home']}")
	private String rootFileSystem;

	@PersistenceContext private EntityManager entityManager;

	private int numberOfVolumes = 5;

	@Inject private PlatformTransactionManager platformTransactionManager;

	@PostConstruct
	public void setup() throws Exception {
		TransactionTemplate template = new TransactionTemplate(this.platformTransactionManager);
		template.execute(new TransactionCallback<Object>() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				for (int i = 0; i < numberOfVolumes; i++) {

					StorageNode node = createStorageNode("d00" + i, 1099511627776L);
					setStorageNodeReady(node.getStorageNodeId(), true);

				}
				return null;
			}
		});
	}

	public String getFilePathExtension(String p) {
		if (!StringUtils.isEmpty(p)) {
			int li = p.lastIndexOf(".");
			if (li != -1) {
				String ext = StringUtils.defaultString(p.substring(li));
				if (ext.startsWith(".")) {
					ext = ext.substring(1);
				}

				if (!ext.contains(" ")) {
					return ext.toLowerCase();
				}
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public String getWebPathForManagedFile(long managedFileId, String host) {

		ManagedFile mf = getManagedFileById(managedFileId);
		long lid = mf.getManagedFileId();
		long rootFolderId = deriveFolderIdFor(lid);
		log.debug("rootFolderId:" + rootFolderId);
		StorageNode storageNodeForManagedFile = getStorageNodeForManagedFile(mf.getManagedFileId());
		if (log.isDebugEnabled()) {
			log.debug("storage node:" + storageNodeForManagedFile.getStorageNodeId());
		}
		String mountPrefix = storageNodeForManagedFile.getMountPrefix();
		String pathString = String.format("/%s/%s/media/" + assetPathMask, buildFileSystemPrefix(), mountPrefix, rootFolderId, lid, mf.getExtension());
		return String.format("%s%s", StringUtils.defaultString(host), pathString);
	}

	/*
	 todo a better paritioning scheme besides modula? This approach assumes volumes mounted at the root, /media/d001, ... /media/../d005, etc.
	 */
	private long deriveFolderIdFor(long managedFileId) {
		return managedFileId % this.numberOfVolumes;
	}

	@Transactional(readOnly = true)
	public String getPathForManagedFile(long mfId) {
		ManagedFile managedFile = getManagedFileById(mfId);
		long lid = managedFile.getManagedFileId();
		long rootFolderId = deriveFolderIdFor(lid);

		if (log.isDebugEnabled()) {
			log.debug("rootFolderId:" + rootFolderId);
		}

		StorageNode sn = getStorageNodeForManagedFile(managedFile.getManagedFileId());

		if (log.isDebugEnabled()) {
			log.debug("storage node:" + sn.getStorageNodeId());
		}

		String mountPrefix = sn.getMountPrefix();
		return String.format("%s/%s/media/" + assetPathMask, buildFileSystemPrefix(), mountPrefix, rootFolderId, lid, managedFile.getExtension());
	}

	@Transactional
	public StorageNode createStorageNode(String mountPrefix, double totalByteCapacity, int priority) {
		StorageNode sn = createStorageNode(mountPrefix, totalByteCapacity);
		sn.setPriority(priority);
		entityManager.merge(sn);
		return sn;
	}

	@Transactional
	public StorageNode createStorageNode(String mountPrefix, double totalByteCapacity) {

		StorageNode existingStorageNode = getStorageNodeByMountPrefix(mountPrefix);

		if (null != existingStorageNode) {
			return existingStorageNode;
		}
		try {
			StorageNode newStorageNode = new StorageNode();
			newStorageNode.setBytesUsed(0);
			newStorageNode.setMountPrefix(mountPrefix);
			newStorageNode.setOffline(false);
			newStorageNode.setReady(false);
			newStorageNode.setPriority(0);
			newStorageNode.setTotalByteCapacity(totalByteCapacity);
			entityManager.persist(newStorageNode);
			return newStorageNode;
		} catch (Throwable th) {
			if (log.isErrorEnabled()) {
				log.error("couldn't create a storage node.", th);
			}
		}

		return null;
	}

	/**
	 * todo spin off some sort of background file deletion process
	 *
	 * @param managedFileId the ID of the managed file to be deleted.
	 */
	@Transactional
	public void deleteManagedFile(long managedFileId) {
		ManagedFile managedFile = getManagedFileById(managedFileId);
		StorageNode sn = managedFile.getStorageNode();
		managedFile.setStorageNode(null);
		entityManager.merge(managedFile);

		// update the storage node, as well
		if (managedFile.isReady() && managedFile.getByteSize() > 0) {
			sn.setBytesUsed(sn.getBytesUsed() - managedFile.getByteSize());
			entityManager.merge(sn);
		}
		if (log.isDebugEnabled()) {
			String path = getPathForManagedFile(managedFileId);
			log.debug(String.format("just called delete managed file on mf DB object for %s. you should perform a rm -rf %s", managedFileId + "", path));
		}
		entityManager.remove(managedFile);
	}

	private String mapPrefixToWeb(String mPrefix) {
		return String.format("%s/%s", MANAGED_FILE_MOUNT_DIRECTORY, mPrefix);
	}


	private File buildFileSystemPrefix() {
		File usrHome = new File(this.rootFileSystem);
		return new File(usrHome, MANAGED_FILE_MOUNT_DIRECTORY);
	}


	@Transactional(readOnly = true)
	public ManagedFile getManagedFileById(long id) {
		return entityManager.find(ManagedFile.class, id);
	}

	@Transactional
	public void setManagedFileReady(final long managedFileId, final boolean ready) {

		ManagedFile managedFile = getManagedFileById(managedFileId);
		boolean readyAlready = managedFile.isReady();
		managedFile.setReady(ready);

		entityManager.merge(managedFile);

		StorageNode storageNode = managedFile.getStorageNode();
		if (storageNode == null) {
			throw new RuntimeException(String.format("You cant mark a managed file as ready without it being assigned to a storage node! Managed File: %s", managedFile.getManagedFileId()));
		}
		// ie, the bytes are ALREADY there
		// on the storage node, now account for it in
		// in the book keeping
		if (ready) {
			storageNode.setBytesUsed(storageNode.getBytesUsed() + managedFile.getByteSize());
		}

		// if the managed file was previously marked as ready (and
		// thus, we would
		// have allocated space for it), then we deduct that
		// allotment
		if (!ready && readyAlready) {
			storageNode.setBytesUsed(storageNode.getBytesUsed() + managedFile.getByteSize());
			if (log.isDebugEnabled()) {
				log.debug("Discounting " + managedFile.getByteSize() + " bytes from storageNode #" + storageNode.getStorageNodeId());
			}
		}
		entityManager.merge(storageNode);
	}

	@Transactional
	public void setStorageNodeReady(long storageNodeId, boolean readyYesOrNo) {
		StorageNode storageNode = entityManager.find(StorageNode.class, storageNodeId);
		storageNode.setReady(readyYesOrNo);
		entityManager.merge(storageNode);
	}

	@Transactional
	public void setManagedFilePriority(long managedFileId, int priority) {
		ManagedFile managedFile = getManagedFileById(managedFileId);
		managedFile.setPriority(priority);
		entityManager.merge(managedFile);
	}


	@Transactional
	public ManagedFile createManagedFile(String originalFileName, String extension, double byteSize, int priority) {

		ManagedFile managedFile = new ManagedFile();
		managedFile.setByteSize(byteSize);
		managedFile.setExtension(StringUtils.defaultString(extension).toLowerCase());
		managedFile.setOriginalFileName(originalFileName);
		managedFile.setReady(false);
		managedFile.setPriority(priority);
		entityManager.persist(managedFile);


		StorageNode storageNodeForManagedFile = getStorageNodeForManagedFile(managedFile.getManagedFileId());
		if (log.isDebugEnabled()) {
			log.debug(String.format("the storage node for managed file # %s is # %s",
							   managedFile.getManagedFileId(), storageNodeForManagedFile.getStorageNodeId()));
		}
		entityManager.merge(managedFile);

		// takes care of bookkeeping (e.g., updating the bytes used)
		addManagedFileToStorageNode(managedFile.getManagedFileId(), storageNodeForManagedFile.getStorageNodeId());

		return getManagedFileById(managedFile.getManagedFileId());
	}

	@Transactional(readOnly = true)
	public Collection<StorageNode> getAvailableStorageNodes() {
		String hql = String.format("select sn FROM %s sn WHERE sn.offline = false and sn.ready=true and sn.bytesUsed < (sn.totalByteCapacity * 0.95)", StorageNode.class.getName());

		TypedQuery<StorageNode> storageNodeTypedQuery = entityManager.createQuery(hql, StorageNode.class);

		List<StorageNode> nodes = storageNodeTypedQuery.getResultList();

		if (log.isDebugEnabled()) {
			log.debug("found " + nodes.size() + " nodes.");
		}

		return nodes;
	}

	@Transactional(readOnly = true)
	public StorageNode getStorageNodeByMountPrefix(String mountPrefix) {

		String storageNodeByMountPrefix = String.format("select sn FROM %s sn WHERE sn.mountPrefix = :mp", StorageNode.class.getName());

		TypedQuery<StorageNode> sns = entityManager.createQuery(storageNodeByMountPrefix, StorageNode.class);
		sns.setParameter("mp", StringUtils.defaultString(mountPrefix));

		List<StorageNode> nodes = sns.getResultList();

		if (nodes != null && nodes.size() > 0) {
			return nodes.iterator().next();
		}
		return null;
	}

	@Transactional(readOnly = true)
	public StorageNode getStorageNodeById(long storageNodeId) {
		return entityManager.find(StorageNode.class, storageNodeId);
	}

	/**
	 * Managed Files need to be stored somewhere, typically on a SAN volume or a cloud store. A {@link StorageNode} could just be the abstraction
	 * for a folder structure on a single file-system, of course.
	 * <p/>
	 * This method blindly assigns managed files to storage nodes. It is expected that the safety of the operation's been determined apriori
	 *
	 * @param managedFileId the ID of the managed file to add
	 * @param storageNodeId the ID of the storage node
	 * @see #getStorageNodeForManagedFile(long)
	 */
	@Transactional
	public void addManagedFileToStorageNode(long managedFileId, long storageNodeId) {
		ManagedFile managedFile = getManagedFileById(managedFileId);
		StorageNode storageNode = getStorageNodeById(storageNodeId);
		// ie, this managed file cant be added to a storage node if the storage
		// node has less space available than is needed
		double available = storageNode.getTotalByteCapacity() - storageNode.getBytesUsed();
		double needed = managedFile.getByteSize();

		if (available > needed) {
			managedFile.setStorageNode(storageNode);
			entityManager.persist(managedFile);

			if (log.isDebugEnabled()) {
				log.debug(String.format("assigned storagenode.id==%s to managedfile.id==%s",
						                       storageNode.getStorageNodeId(), managedFile.getManagedFileId()));
			}
		} else {
			throw new RuntimeException(String.format("can't add the managed file (# %s) to the " +
					                                         "storage node (# %s), not sufficient space", managedFileId, storageNodeId));
		}

	}


	/**
	 * Retreives a {@link StorageNode storage node} for this managed file.
	 * The business logic of  determining whether space is available or not lives in this code, as well.
	 *
	 * @param managedFileId the id of the managed file
	 * @return the storage node assigned to a managed file.
	 */
	@Transactional
	public StorageNode getStorageNodeForManagedFile(long managedFileId) {
		ManagedFile mf = getManagedFileById(managedFileId);
		if (mf.getStorageNode() != null) {
			return getStorageNodeById(mf.getStorageNode().getStorageNodeId());
		}
		ArrayList<StorageNode> nodes = new ArrayList<StorageNode>();
		nodes.addAll(getAvailableStorageNodes());
		Collections.sort(nodes, new Comparator<StorageNode>() {
			public int compare(StorageNode storageNode, StorageNode storageNode1) {
				return storageNode.getPriority() - storageNode1.getPriority();
			}
		});
		final int priority = mf.getPriority();
		StorageNode storageNode = null;
		storageNode = (StorageNode) CollectionUtils.find(nodes, new Predicate() {
			public boolean evaluate(Object o) {
				StorageNode sn = (StorageNode) o;
				return sn.getPriority() == priority && (sn.getBytesUsed() <= (.95 * sn.getTotalByteCapacity()));
			}
		});
		if (null != storageNode) {
			if (log.isDebugEnabled()) {
				log.debug("am assigning " + storageNode.getMountPrefix() + " to mf " + mf.getManagedFileId());
			}
		}

		if (storageNode == null) {

			storageNode = (StorageNode) CollectionUtils.find(nodes, new Predicate() {
				public boolean evaluate(Object o) {
					StorageNode sn = (StorageNode) o;
					return sn.getPriority() != priority && (sn.getBytesUsed() <= (.95 * sn.getTotalByteCapacity()));
				}
			});
			if (null != storageNode) {
				if (log.isDebugEnabled()) {
					log.debug("assigning " + storageNode.getMountPrefix() + " to managed file " + mf.getManagedFileId());
				}
			}
		}

		if (storageNode == null) {
			throw new RuntimeException(String.format("Can't allocate disk storage! No space for a managed file with priority %s on _ANY_ nodes!", mf.getPriority()));
		}

		addManagedFileToStorageNode(managedFileId, storageNode.getStorageNodeId());

		return getStorageNodeById(getManagedFileById(managedFileId).getStorageNode().getStorageNodeId());
	}
}
