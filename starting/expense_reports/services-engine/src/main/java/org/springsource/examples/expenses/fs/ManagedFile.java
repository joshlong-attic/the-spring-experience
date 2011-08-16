package org.springsource.examples.expenses.fs;

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

	public ManagedFile(String extension, double byteSize, String originalFileName) {
		setup(extension, byteSize, originalFileName);
	}

	public ManagedFile(File file) {
		if (file == null) {
			throw new IllegalArgumentException("the file can't be null");
		}
		setup(deriveExtension(file ), file.length(),file.getName());
	}

	protected void setup(String extension, double byteSize, String ogFileName){
		this.extension = extension;
		this.byteSize = byteSize ;
		this.originalFileName = ogFileName ;
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

	public double getByteSize() {
		return this.byteSize;
	}

	public String getOriginalFileName() {
		return this.originalFileName;
	}

	public boolean isReady() {
		return this.ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}


}