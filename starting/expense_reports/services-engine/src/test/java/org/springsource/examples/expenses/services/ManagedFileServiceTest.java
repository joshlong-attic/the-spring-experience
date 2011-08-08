package org.springsource.examples.expenses.services;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.examples.expenses.fs.ManagedFile;
import org.springsource.examples.expenses.fs.ManagedFileService;
import org.springsource.examples.expenses.config.ServiceConfiguration;
import org.springsource.examples.expenses.fs.StorageNode;

import javax.inject.Inject;
import java.io.File;

/**
 * test the {@link ManagedFileService}
 *
 * @author Josh Long
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {ServiceConfiguration.class})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ManagedFileServiceTest {

	@Inject private ManagedFileService managedFileService;

	private ManagedFile managedFile;
	private StorageNode node;
	private Log log = LogFactory.getLog(getClass());

	@Before
	public void before() {
		File file = new File(SystemUtils.getUserHome(), "foo.txt");
		int randomNumberPriority = (int) (Math.PI * (Math.random() * 34302)); // the priority is random and suitably large neough to be unique for this test

		if (log.isDebugEnabled()) {
			log.debug(String.format("random priority is %s", randomNumberPriority + ""));
		}
		node = managedFileService.createStorageNode("test", 10000000000000L, randomNumberPriority);
		managedFile = managedFileService.createManagedFile(file.getAbsolutePath(), managedFileService.getFilePathExtension(file.getAbsolutePath()), 1024, randomNumberPriority);
		managedFileService.setManagedFileReady(managedFile.getManagedFileId(), true);
	}

	@Test
	public void testPriorityAlgorithmWorks() throws Throwable {
		Assert.assertTrue("the priority is suitbly unique that only one node could match it, " +
				                  "and thus should have been assigned to the managed file. " +
				                  "If not, that's an error.",node.getPriority() == managedFile.getPriority());
	}

	@Test
	public void testCreatingManagedFiles() throws Throwable {
		Assert.assertNotNull(managedFile);
		StorageNode node = managedFile.getStorageNode();
		Assert.assertNotNull(node);
		Assert.assertTrue(managedFile.getStorageNode().getStorageNodeId() == node.getStorageNodeId());
	}

	@Test
	public void testAnalysingManagedFiles() throws Throwable {
		int priority = managedFile.getPriority();
		int newPriority = priority + 1;
		managedFileService.setManagedFilePriority(managedFile.getManagedFileId(), newPriority);
		ManagedFile refreshed = managedFileService.getManagedFileById(managedFile.getManagedFileId());
		Assert.assertTrue("the priority should be reflected on the updated entity.", refreshed.getPriority() == newPriority);
		Assert.assertTrue(managedFileService.getManagedFileById(managedFile.getManagedFileId()).isReady());
		managedFileService.setManagedFileReady(managedFile.getManagedFileId(), true);
		Assert.assertTrue(managedFileService.getManagedFileById(managedFile.getManagedFileId()).isReady());
	}

	@Test
	public void testAnalysingStorageNode() {
		StorageNode node = managedFile.getStorageNode();
		Assert.assertTrue(node.getBytesUsed() >= managedFile.getByteSize());
	}

	/**
	 * The bytes used by a {@link org.springsource.examples.expenses.fs.ManagedFile} are not reflected in the {@link StorageNode storage nodes} {@link StorageNode#bytesUsed} property
	 * <em>until</em> the {@link org.springsource.examples.expenses.fs.ManagedFile#ready} property is set to <code>true</code>. Once set, the {@link org.springsource.examples.expenses.fs.ManagedFileService#deleteManagedFile(long)}
	 * method must take into account that the number's been added and subtract it. But, it must not do so if the file's not {@link org.springsource.examples.expenses.fs.ManagedFile#ready}, otherwise
	 * we risk out of bounds type problems (deleting bytes that weren't added, etc.)
	 *
	 * @throws Throwable if anything should go wrong.
	 */
	@Test
	public void testDeletingManagedFile() throws Throwable {
		long bytesUsed = (long) managedFile.getByteSize();

		StorageNode node = managedFile.getStorageNode();
		long nodeBytesUsed = (long) node.getBytesUsed();

		managedFileService.deleteManagedFile(managedFile.getManagedFileId());

		StorageNode updatedStorageNode = managedFileService.getStorageNodeById(node.getStorageNodeId());
		Assert.assertTrue(updatedStorageNode.getBytesUsed() == (nodeBytesUsed - bytesUsed));

	}


}
