package org.springsource.examples.expenses.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ManagedFile generated by hbm2java
 */
@Entity
@EntityListeners(org.springsource.examples.expenses.services.util.AuditingJpaEntityFieldListener.class)
@Table(name = "managed_file", schema = "public")
public class ManagedFile implements java.io.Serializable {
	private long managedFileId;
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

	public ManagedFile(long managedFileId, String extension, double byteSize, String originalFileName, boolean ready, String mountPrefix, int priority, Set<Attachment> attachments) {
		this.managedFileId = managedFileId;
		this.extension = extension;
		this.byteSize = byteSize;
		this.originalFileName = originalFileName;
		this.ready = ready;
		this.mountPrefix = mountPrefix;
		this.priority = priority;
		this.attachments = attachments;
	}

	private java.util.Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created", nullable = false, length = 10)
	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dc) {
		this.dateCreated = dc;
	}


	private java.util.Date dateModified;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modified", nullable = false, length = 10)
	public Date getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(Date dc) {
		this.dateModified = dc;
	}


	private java.lang.Long version;

	@javax.persistence.Version
	public java.lang.Long getVersion() {
		return version;
	}

	public void setVersion(java.lang.Long value) {
		this.version = value;
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

	@Column(name = "extension", nullable = false, length = 10)
	public String getExtension() {
		return this.extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Column(name = "byte_size", nullable = false, precision = 17, scale = 17)
	public double getByteSize() {
		return this.byteSize;
	}

	public void setByteSize(double byteSize) {
		this.byteSize = byteSize;
	}

	@Column(name = "original_file_name", nullable = false)
	public String getOriginalFileName() {
		return this.originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	@Column(name = "ready", nullable = false)
	public boolean isReady() {
		return this.ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	@Column(name = "mount_prefix", nullable = false, length = 10)
	public String getMountPrefix() {
		return this.mountPrefix;
	}

	public void setMountPrefix(String mountPrefix) {
		this.mountPrefix = mountPrefix;
	}

	@Column(name = "priority", nullable = false)
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