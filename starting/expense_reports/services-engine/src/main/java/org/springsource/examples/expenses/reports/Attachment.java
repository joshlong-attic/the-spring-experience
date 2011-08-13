package org.springsource.examples.expenses.reports;

import org.springsource.examples.expenses.fs.ManagedFile;

/**
 * An attachment manages a single uploaded {@link ManagedFile file}. It also contains a description which the user provides.
 * <p/>
 * This object belongs to the {@link LineItem} object, which may have 0-N {@link Attachment} entities.
 *
 * @author Josh Long
 */

public class Attachment {

	private long attachmentId;

	private ManagedFile managedFile;

	private String description;

	public long getAttachmentId() {
		return this.attachmentId;
	}

	public void setAttachmentId(long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public ManagedFile getManagedFile() {
		return this.managedFile;
	}

	public void setManagedFile(ManagedFile managedFile) {
		this.managedFile = managedFile;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}