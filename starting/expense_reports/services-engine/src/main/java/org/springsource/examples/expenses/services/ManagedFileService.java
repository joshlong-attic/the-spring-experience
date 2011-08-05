package org.springsource.examples.expenses.services;

import org.springframework.stereotype.Service;
import org.springsource.examples.expenses.model.ManagedFile;

/**
 * Service charged with maintaining the file system of uploads
 *
 * @author Josh Long
 */
@Service
public interface ManagedFileService {

	ManagedFile getManagedFileById(long mfId) ;

	String getManagedFilePath (long mfId) ;
}
