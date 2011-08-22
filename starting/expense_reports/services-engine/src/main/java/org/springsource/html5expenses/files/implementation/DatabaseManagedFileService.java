package org.springsource.html5expenses.files.implementation;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.html5expenses.files.ManagedFileService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;

public class DatabaseManagedFileService implements ManagedFileService {

	private int numberOfVolumes = 5; // arbitrary for now
	private static String MANAGED_FILE_MOUNT_DIRECTORY = "managedFiles";

	private String assetPathMask = "%010d/%010d.%s";

	@Value("#{systemProperties['user.home']}")
	private String rootFileSystem;

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	@Override
	public String getLocalPathForManagedFile(Long managedFileId) {
		ManagedFile managedFile = getManagedFileById(managedFileId);
		long lid = managedFile.getId();
		long rootFolderId = deriveFolderIdFor(lid);
		String mountPrefix = "";
		return String.format("%s/%s/media/" + assetPathMask, buildFileSystemPrefix(), mountPrefix, rootFolderId, lid, managedFile.getExtension());
	}

	@Transactional(readOnly = true)
	@Override
	public String getWebUrlForManagedFile(String host, Long managedFileId) {

		ManagedFile managedFile = getManagedFileById(managedFileId);
		long lid = managedFile.getId();
		long rootFolderId = deriveFolderIdFor(lid);

		String mountPrefix = "";
		String pathString = String.format("/%s/%s/media/" + assetPathMask, buildFileSystemPrefix(), mountPrefix, rootFolderId, lid, managedFile.getExtension());
		return String.format("%s%s", StringUtils.defaultString(host), pathString);

	}

	@Transactional
	@Override
	public ManagedFile createManagedFile(double byteSize, String originalFileName) {
		ManagedFile managedFile = new ManagedFile(getFilePathExtension(originalFileName), byteSize, originalFileName);
		managedFile.setReady(false);
		entityManager.persist(managedFile);
		return getManagedFileById(managedFile.getId());
	}

	@Override
	@Transactional
	public void removeManagedFile(Long managedFileId) {
		throw new IllegalStateException("TODO");
	}

	@Override
	@Transactional
	public void setManagedFileReady(Long managedFileId, boolean ready) {
		ManagedFile mf = getManagedFileById(managedFileId);
		mf.setReady(ready);
		entityManager.merge(mf);
	}

	@Transactional(readOnly = true)
	protected ManagedFile getManagedFileById(Long id) {
		return this.entityManager.find(ManagedFile.class, id);
	}

	@Transactional(readOnly = true)
	private long deriveFolderIdFor(long managedFileId) {
		return managedFileId % this.numberOfVolumes;
	}

	@Transactional(readOnly = true)
	private File buildFileSystemPrefix() {
		File usrHome = new File(this.rootFileSystem);
		return new File(usrHome, MANAGED_FILE_MOUNT_DIRECTORY);
	}

	private String getFilePathExtension(String p) {
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

}
