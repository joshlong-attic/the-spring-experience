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

package org.springsource.html5expenses.files;

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
	ManagedFile createManagedFile(  double byteSize, String originalFileName);

	/**
	 * to reclaim hard disk space and remove the entry from the database
	 * @param managedFileId
	 */
	void removeManagedFile(  Long managedFileId ) ;

	/**
	 * to be called when the managed file's been fully written to the file system
	 */
	void setManagedFileReady(Long managedFileId, boolean ready);
}
