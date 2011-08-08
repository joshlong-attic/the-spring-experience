package org.springsource.examples.expenses.fs;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Josh Long
 */
@Entity
public class StorageNode {
	private long storageNodeId;
	private int priority;
	private boolean offline;
	private boolean ready;
	private double bytesUsed;
	private String mountPrefix;
	private double totalByteCapacity;
	private Set<ManagedFile> managedFiles = new HashSet<ManagedFile>(0);

	@Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	public long getStorageNodeId() {
		return this.storageNodeId;
	}

	public void setStorageNodeId(long storageNodeId) {
		this.storageNodeId = storageNodeId;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isOffline() {
		return this.offline;
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
	}

	public boolean isReady() {
		return this.ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public double getBytesUsed() {
		return this.bytesUsed;
	}

	public void setBytesUsed(double bytesUsed) {
		this.bytesUsed = bytesUsed;
	}

	public String getMountPrefix() {
		return this.mountPrefix;
	}

	public void setMountPrefix(String mountPrefix) {
		this.mountPrefix = mountPrefix;
	}

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