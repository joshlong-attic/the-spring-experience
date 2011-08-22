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

package org.springsource.examples.expenses;

import junit.framework.Assert;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springsource.html5expenses.config.ServicesConfiguration;
import org.springsource.html5expenses.files.ManagedFile;
import org.springsource.html5expenses.files.ManagedFileService;
import org.springsource.html5expenses.files.implementation.DatabaseManagedFileService;

import javax.inject.Inject;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Tests the {@link org.springsource.html5expenses.files.ManagedFileService managed file service}
 *
 * @author Josh Long
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {ServicesConfiguration.class})
public class TestManagedFileService {

	@Inject ManagedFileService fileService;

	String receiptClassPath = "receipt.jpg";




	@Before
	public void before() throws Throwable {
	   if(fileService instanceof DatabaseManagedFileService){
		   ((DatabaseManagedFileService)fileService).setRootFileSystem(SystemUtils.getJavaIoTmpDir().getAbsolutePath());
	   }
	}

	@After
	public void after() throws Throwable {
	}

	@Test
	public void testCreatingManagedFile() throws Throwable {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(receiptClassPath);

		ManagedFile mf = this.fileService.createManagedFile(243243, "receipt.jpg");
		Assert.assertFalse("the file's not ready", mf.isReady())  ;
		String localPath = fileService.getLocalPathForManagedFile(mf.getId());
		OutputStream outputSteam  = new FileOutputStream(localPath) ;
		IOUtils.copy( inputStream ,  outputSteam );
		IOUtils.closeQuietly(inputStream);
		IOUtils.closeQuietly(outputSteam);

		fileService.setManagedFileReady(mf.getId(), true);

		Assert.assertNotNull(mf.getId());

	}
}
