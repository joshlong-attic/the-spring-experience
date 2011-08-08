package org.springsource.examples.expenses.fs;

import org.springsource.examples.expenses.reports.Attachment;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Josh Long
 */
@Entity
public class ManagedFile {
	private long managedFileId;
	private StorageNode storageNode;
	private String extension;
	private double byteSize;
	private String originalFileName;
	private boolean ready;
	private String mountPrefix;
	private int priority;
	private Set<Attachment> attachments = new HashSet<Attachment>(0);


	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	public long getManagedFileId() {
		return this.managedFileId;
	}

	public void setManagedFileId(long managedFileId) {
		this.managedFileId = managedFileId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storage_node_id")
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

	public String getMountPrefix() {
		return this.mountPrefix;
	}

	public void setMountPrefix(String mountPrefix) {
		this.mountPrefix = mountPrefix;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "managedFile")
	public Set<Attachment> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}
}