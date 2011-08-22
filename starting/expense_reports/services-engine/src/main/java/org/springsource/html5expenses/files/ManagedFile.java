package org.springsource.html5expenses.files;

public class ManagedFile {
	private Long id;

	private String extension;
	private double byteSize;
	private String originalFileName;
	private boolean ready;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public double getByteSize() {
		return byteSize;
	}

	public void setByteSize(double byteSize) {
		this.byteSize = byteSize;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}
}
