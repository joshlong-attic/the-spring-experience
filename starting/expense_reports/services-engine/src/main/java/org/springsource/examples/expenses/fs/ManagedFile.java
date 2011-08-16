/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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