package org.springsource.html5expenses.files.implementation;


import org.springsource.html5expenses.files.ManagedFile;
import org.springsource.html5expenses.files.ManagedFileService;

public class DatabaseManagedFileService  implements ManagedFileService{
	@Override
	public String getLocalPathForManagedFile(Long managedFileId) {
	 return null ;
	}

	@Override
	public String getWebUrlForManagedFile(String host, Long managedFileId) {
		return null;
	}

	@Override
	public ManagedFile createManagedFile(String extension, double byteSize, String originalFileName) {
		return null;
	}

	@Override
	public void removeManagedFile(long managedFileId) {
	}

	@Override
	public void setManagedFileReady(long managedFileId, boolean ready) {

	}
}
