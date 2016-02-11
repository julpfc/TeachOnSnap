package com.julvez.pfc.teachonsnap.ut.manager.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import com.julvez.pfc.teachonsnap.manager.file.FileManager;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerTest;

public abstract class FileManagerTest extends ManagerTest<FileManager> {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
		
	@Test
	public void testCopyStream() {
		String fileName = "foo.txt";
		String fileName2 = "foo2.txt";
		try {
			File file = folder.newFile(fileName);
			InputStream is = Mockito.spy(new FileInputStream(file));			
				
			Assert.assertTrue(test.copyStream(is, file.getParent() + File.separator, fileName2));
			Mockito.verify(is, Mockito.atLeastOnce()).close();

			File file2 = new File(file.getParent() + File.separator +  fileName2);
			Assert.assertTrue(file2.exists());
			Assert.assertEquals(file.getParent(), file2.getParent());
		
		} catch (IOException e) {			
			e.printStackTrace();			
		}
		
		Assert.assertFalse(test.copyStream(null, NULL_STRING, NULL_STRING));
	}

	@Test
	public void testGetFileExtension() {
		String file = "fileName.extension";
		String fileDot = "fileName.with.middle.dots.extension";
		String fileStartDot = ".extension";
		String fileEndDot = "fileName.";
		String fileBlank = "fileName.     ";
		
		Assert.assertEquals("extension", test.getFileExtension(file));		
		Assert.assertEquals("extension", test.getFileExtension(fileDot));		
		Assert.assertEquals("extension", test.getFileExtension(fileStartDot));		
		Assert.assertNull(test.getFileExtension(fileEndDot));		
		Assert.assertEquals(BLANK_STRING, test.getFileExtension(fileBlank));		

		Assert.assertNull(test.getFileExtension(NULL_STRING));		
		Assert.assertNull(test.getFileExtension(BLANK_STRING));
		Assert.assertNull(test.getFileExtension(EMPTY_STRING));		
	}

	@Test
	public void testDelete() {
		String fileName = "foo.txt";		
		try {
			File file = folder.newFile(fileName);
				
			Assert.assertTrue(test.delete(file.getParent() + File.separator, fileName));
			
			File file2 = new File(file.getParent() + File.separator +  fileName);
			Assert.assertFalse(file2.exists());

			Assert.assertTrue(test.delete(file.getParent() + File.separator, fileName));
			
		
		} catch (IOException e) {			
			e.printStackTrace();			
		}
		
		Assert.assertFalse(test.delete(NULL_STRING, NULL_STRING));
	}

}
