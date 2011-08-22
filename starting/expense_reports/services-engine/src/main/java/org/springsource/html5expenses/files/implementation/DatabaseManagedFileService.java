/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springsource.html5expenses.files.implementation;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.html5expenses.files.ManagedFileService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;

public class DatabaseManagedFileService implements ManagedFileService {

	private Log log = LogFactory.getLog(getClass()) ;

	// assumes we want to distribute the files over 5 'buckets'
	private int numberOfVolumes = 5;
	private static String MANAGED_FILE_MOUNT_DIRECTORY = "managedFiles";

	private String assetPathMask = "%010d/%010d.%s";

	@Value("#{systemProperties['user.home']}")
	private String rootFileSystem;

	public void setRootFileSystem(String rootFileSystem) {
		this.rootFileSystem = rootFileSystem;
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public org.springsource.html5expenses.files.ManagedFile getManagedFile(Long mfId) {
	 return  fromManagedFile(getManagedFileById(mfId));
	}

	@Transactional(readOnly = true)
	@Override
	public String getLocalPathForManagedFile(Long managedFileId) {
		ManagedFile managedFile = getManagedFileById(managedFileId);
		long lid = managedFile.getId();
		long rootFolderId = deriveFolderIdFor(lid);
		String mountPrefix = "mount";
		String path =String.format("%s/%s/media/" + assetPathMask, buildFileSystemPrefix(), mountPrefix, rootFolderId, lid, managedFile.getExtension());
		guaranteeTreeExists(path);
		return path ;
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
	public org.springsource.html5expenses.files.ManagedFile createManagedFile(double byteSize, String originalFileName) {
		ManagedFile managedFile = new ManagedFile(getFilePathExtension(originalFileName), byteSize, originalFileName);
		managedFile.setReady(false);
		entityManager.persist(managedFile);
		return fromManagedFile(getManagedFileById(managedFile.getId()));
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

	private org.springsource.html5expenses.files.ManagedFile fromManagedFile(ManagedFile f) {
		org.springsource.html5expenses.files.ManagedFile mf = new org.springsource.html5expenses.files.ManagedFile();
		mf.setByteSize(f.getByteSize());
		mf.setExtension(f.getExtension());
		mf.setId(f.getId());
		mf.setReady(f.isReady());
		mf.setOriginalFileName(f.getOriginalFileName());
		return mf;
	}

	/**
	 * precondition; the input must be a file path
	 *
	 * @param path the path itself (including the file)
	 * @return whether or not that can be written to
	 */
	private boolean guaranteeTreeExists(String path) {
		if (log.isDebugEnabled()) {
			log.debug(String.format("testing to see if %s exists.", path));
		}
		File f = new File(path);
		File dir = f.getParentFile();
		if (!dir.exists()) {
			return dir.mkdirs() && dir.exists();
		}
		return dir.exists();
	}

}
