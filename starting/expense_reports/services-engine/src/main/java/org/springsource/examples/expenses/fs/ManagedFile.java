package org.springsource.examples.expenses.fs;

import org.springframework.util.Assert;

import java.io.File;

/**
 * Metadata to describe a file, as written on a file system.
 *
 * @author Josh Long
 */

public class ManagedFile {
	private String extension;
	private double byteSize;
	private String originalFileName;
	private boolean ready;
	private int priority;

	public ManagedFile( File file) {
		Assert.notNull(file, "the file can't be null");
		this.originalFileName = file.getName();
		this.byteSize = file.length();
		this.extension = deriveExtension(file);
	}

	private String deriveExtension(File file) {
		String name = file.getName();
		if (name != null && name.lastIndexOf(".") != -1) {
			String ext = name.substring(name.lastIndexOf("."));
			return ext.toLowerCase();
		}
		return null;
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

}