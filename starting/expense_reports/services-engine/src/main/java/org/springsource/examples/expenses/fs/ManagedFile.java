package org.springsource.examples.expenses.fs;

import org.springsource.examples.expenses.Attachment;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Josh Long
 */
@Entity
public class ManagedFile implements java.io.Serializable {
	private long managedFileId;
	private StorageNode storageNode;
	private String extension;
	private double byteSize;
	private String originalFileName;
	private boolean ready;
	private String mountPrefix;
	private int priority;
	private Set<Attachment> attachments = new HashSet<Attachment>(0);

	public ManagedFile() {
	}

	public ManagedFile(long managedFileId, String extension, double byteSize, String originalFileName, boolean ready, String mountPrefix, int priority) {
		this.managedFileId = managedFileId;
		this.extension = extension;
		this.byteSize = byteSize;
		this.originalFileName = originalFileName;
		this.ready = ready;
		this.mountPrefix = mountPrefix;
		this.priority = priority;
	}

	public ManagedFile(long managedFileId, StorageNode storageNode, String extension, double byteSize, String originalFileName, boolean ready, String mountPrefix, int priority, Set<Attachment> attachments) {
		this.managedFileId = managedFileId;
		this.storageNode = storageNode;
		this.extension = extension;
		this.byteSize = byteSize;
		this.originalFileName = originalFileName;
		this.ready = ready;
		this.mountPrefix = mountPrefix;
		this.priority = priority;
		this.attachments = attachments;
	}


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