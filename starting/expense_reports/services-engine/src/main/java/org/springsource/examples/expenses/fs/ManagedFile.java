package org.springsource.examples.expenses.fs;

import org.springsource.examples.expenses.reports.Attachment;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Metadata to describe a file, as written on a file system.
 *
 * @author Josh Long
 */

public class ManagedFile {
	private long managedFileId;
	private StorageNode storageNode;
	private String extension;
	private double byteSize;
	private String originalFileName;
	private boolean ready;
	private int priority;
	private Set<Attachment> attachments = new HashSet<Attachment>(0);

	public long getManagedFileId() {
		return this.managedFileId;
	}

	public void setManagedFileId(long managedFileId) {
		this.managedFileId = managedFileId;
	}

	public StorageNode getStorageNode() {
		return this.storageNode;
	}

	public void setStorageNode(StorageNode storageNode) {
		this.storageNode = storageNode;
	}

	public String getExtension() {
		return this.extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public double getByteSize() {
		return this.byteSize;
	}

	public void setByteSize(double byteSize) {
		this.byteSize = byteSize;
	}

	public String getOriginalFileName() {
		return this.originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public boolean isReady() {
		return this.ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Set<Attachment> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}
}