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
@Table(name = "storage_node", schema = "public")
public class StorageNode implements java.io.Serializable {
	private long storageNodeId;
	private Long version;
	private double bytesUsed;
	private Date dateCreated;
	private Date dateModified;
	private String mountPrefix;
	private boolean offline;
	private int priority;
	private boolean ready;
	private double totalByteCapacity;
	private Set<ManagedFile> managedFiles = new HashSet<ManagedFile>(0);

	public StorageNode() {
	}

	public StorageNode(long storageNodeId, double bytesUsed, Date dateCreated, Date dateModified, String mountPrefix, boolean offline, int priority, boolean ready, double totalByteCapacity) {
		this.storageNodeId = storageNodeId;
		this.bytesUsed = bytesUsed;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.mountPrefix = mountPrefix;
		this.offline = offline;
		this.priority = priority;
		this.ready = ready;
		this.totalByteCapacity = totalByteCapacity;
	}

	public StorageNode(long storageNodeId, double bytesUsed, Date dateCreated, Date dateModified, String mountPrefix, boolean offline, int priority, boolean ready, double totalByteCapacity, Set<ManagedFile> managedFiles) {
		this.storageNodeId = storageNodeId;
		this.bytesUsed = bytesUsed;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.mountPrefix = mountPrefix;
		this.offline = offline;
		this.priority = priority;
		this.ready = ready;
		this.totalByteCapacity = totalByteCapacity;
		this.managedFiles = managedFiles;
	}

	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	@Column(name = "storage_node_id", unique = true, nullable = false)
	public long getStorageNodeId() {
		return this.storageNodeId;
	}

	public void setStorageNodeId(long storageNodeId) {
		this.storageNodeId = storageNodeId;
	}

	@Version
	@Column(name = "version")
	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Column(name = "bytes_used", nullable = false, precision = 17, scale = 17)
	public double getBytesUsed() {
		return this.bytesUsed;
	}

	public void setBytesUsed(double bytesUsed) {
		this.bytesUsed = bytesUsed;
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

	@Column(name = "mount_prefix", nullable = false, length = 10)
	public String getMountPrefix() {
		return this.mountPrefix;
	}

	public void setMountPrefix(String mountPrefix) {
		this.mountPrefix = mountPrefix;
	}

	@Column(name = "offline", nullable = false)
	public boolean isOffline() {
		return this.offline;
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
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

	@Column(name = "total_byte_capacity", nullable = false, precision = 17, scale = 17)
	public double getTotalByteCapacity() {
		return this.totalByteCapacity;
	}

	public void setTotalByteCapacity(double totalByteCapacity) {
		this.totalByteCapacity = totalByteCapacity;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "storageNode")
	public Set<ManagedFile> getManagedFiles() {
		return this.managedFiles;
	}

	public void setManagedFiles(Set<ManagedFile> managedFiles) {
		this.managedFiles = managedFiles;
	}
}