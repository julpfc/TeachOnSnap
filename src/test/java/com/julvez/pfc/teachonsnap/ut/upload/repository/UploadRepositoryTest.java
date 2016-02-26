package com.julvez.pfc.teachonsnap.ut.upload.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.upload.repository.UploadRepository;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryTest;

public abstract class UploadRepositoryTest extends RepositoryTest<UploadRepository> {

	protected int idUser = 1;
		
	@Test
	public void testGetTemporaryFile() {
		FileMetadata file = test.getTemporaryFile(idUser, 0);		
		assertNull(file);
		
		List<FileMetadata> uploadFiles = new ArrayList<FileMetadata>();
		FileMetadata uploadFile = new FileMetadata();
		uploadFiles.add(uploadFile);
		
		test.addTemporaryFiles(idUser, uploadFiles);
		
		file = test.getTemporaryFile(idUser, 0);		
		assertNotNull(file);
		assertSame(uploadFile, file);
	}

	@Test
	public void testGetTemporaryFiles() {
		List<FileMetadata> files = test.getTemporaryFiles(idUser);		
		assertNull(files);
		
		List<FileMetadata> uploadFiles = new ArrayList<FileMetadata>();
		FileMetadata uploadFile = new FileMetadata();
		uploadFiles.add(uploadFile);
		
		test.addTemporaryFiles(idUser, uploadFiles);
		
		files = test.getTemporaryFiles(idUser);
		assertNotNull(files);
		assertSame(uploadFiles.get(0), files.get(0));
	}

	@Test
	public void testClose() {
		List<FileMetadata> uploadFiles = new ArrayList<FileMetadata>();
		FileMetadata uploadFile = new FileMetadata();
		InputStream input = mock(InputStream.class);
		uploadFile.setContent(input);
		uploadFiles.add(uploadFile);
		
		test.addTemporaryFiles(idUser, uploadFiles);
		
		test.close();
				
		try {
			verify(input).close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}

	@Test
	public void testRemoveTemporaryFile() {
		List<FileMetadata> uploadFiles = new ArrayList<FileMetadata>();
		FileMetadata uploadFile = new FileMetadata();
		uploadFiles.add(uploadFile);
		
		test.addTemporaryFiles(idUser, uploadFiles);
		
		FileMetadata file = test.getTemporaryFile(idUser, 0);		
		assertNotNull(file);
		
		test.removeTemporaryFile(idUser, 0);
		file = test.getTemporaryFile(idUser, 0);		
		assertNull(file);		
	}

	@Test
	public void testRemoveTemporaryFiles() {
		List<FileMetadata> uploadFiles = new ArrayList<FileMetadata>();
		FileMetadata uploadFile = new FileMetadata();
		uploadFiles.add(uploadFile);
		
		test.addTemporaryFiles(idUser, uploadFiles);
		
		List<FileMetadata> files = test.getTemporaryFiles(idUser);		
		assertNotNull(files);
		
		test.removeTemporaryFiles(idUser);
		files = test.getTemporaryFiles(idUser);
		assertEquals(0 , files.size());		
	}

}
