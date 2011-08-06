package org.springsource.examples.expenses.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Josh Long
 */
@Entity
@EntityListeners(org.springsource.examples.expenses.services.util.AuditingJpaEntityFieldListener.class)
@Table(name = "managed_file", schema = "public")
public class ManagedFile implements java.io.Serializable {
	private long managedFileId;
	private Long version;
	private StorageNode storageNode;
	private double byteSize;
	private Date dateCreated;
	private Date dateModified;
	private String extension;
	private String mountPrefix;
	private String originalFileName;
	private int priority;
	private boolean ready;
	private Set<Attachment> attachments = new HashSet<Attachment>(0);

	public ManagedFile() {
	}

	public ManagedFile(long managedFileId, double byteSize, Date dateCreated, Date dateModified, String extension, String mountPrefix, String originalFileName, int priority, boolean ready) {
		this.managedFileId = managedFileId;
		this.byteSize = byteSize;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.extension = extension;
		this.mountPrefix = mountPrefix;
		this.originalFileName = originalFileName;
		this.priority = priority;
		this.ready = ready;
	}

	public ManagedFile(long managedFileId, StorageNode storageNode, double byteSize, Date dateCreated, Date dateModified, String extension, String mountPrefix, String originalFileName, int priority, boolean ready, Set<Attachment> attachments) {
		this.managedFileId = managedFileId;
		this.storageNode = storageNode;
		this.byteSize = byteSize;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.extension = extension;
		this.mountPrefix = mountPrefix;
		this.originalFileName = originalFileName;
		this.priority = priority;
		this.ready = ready;
		this.attachments = attachments;
	}

	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	@Column(name = "managed_file_id", unique = true, nullable = false)
	public long getManagedFileId() {
		return this.managedFileId;
	}

	public void setManagedFileId(long managedFileId) {
		this.managedFileId = managedFileId;
	}

	@Version
	@Column(name = "version")
	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storage_node_id")
	public StorageNode getStorageNode() {
		return this.storageNode;
	}

	public void setStorageNode(StorageNode storageNode) {
		this.storageNode = storageNode;
	}

	@Column(name = "byte_size", nullable = false, precision = 17, scale = 17)
	public double getByteSize() {
		return this.byteSize;
	}

	public void setByteSize(double byteSize) {
		this.byteSize = byteSize;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created", nullable = false, length = 29)
	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modified", nullable = false, length = 29)
	public Date getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@Column(name = "extension", nullable = false, length = 10)
	public String getExtension() {
		return this.extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Column(name = "mount_prefix", nullable = false, length = 10)
	public String getMountPrefix() {
		return this.mountPrefix;
	}

	public void setMountPrefix(String mountPrefix) {
		this.mountPrefix = mountPrefix;
	}

	@Column(name = "original_file_name", nullable = false)
	public String getOriginalFileName() {
		return this.originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	@Column(name = "priority", nullable = false)
	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Column(name = "ready", nullable = false)
	public boolean isReady() {
		return this.ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "managedFile")
	public Set<Attachment> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}
}