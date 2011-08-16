package org.springsource.examples.expenses.fs;

/**
 * simple service to manage files for receipt storage "just the contracts ma'am"
 *
 * @author Josh Long
 */
public interface ManagedFileService {

	String getLocalPathForManagedFile( Long managedFileId );

	String getWebUrlForManagedFile( String host, Long managedFileId ) ;

	/**
	 * create a new managed file in the system (presumably there's some
	 * service specific logic about where this managed file lives)
	 */
	ManagedFile createManagedFile( String extension, double byteSize, String originalFileName);

	/**
	 * to reclaim hard disk space and remove the entry from the database
	 * @param managedFileId
	 */
	void removeManagedFile( long managedFileId ) ;

	/**
	 * to be called when the managed file's been fully written to the file system
	 */
	void setManagedFileReady(long managedFileId, boolean ready);
}
