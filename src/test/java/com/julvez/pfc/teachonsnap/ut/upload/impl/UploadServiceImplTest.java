package com.julvez.pfc.teachonsnap.ut.upload.impl;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.upload.UploadService;
import com.julvez.pfc.teachonsnap.upload.impl.UploadServiceImpl;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.upload.repository.UploadRepository;
import com.julvez.pfc.teachonsnap.ut.upload.UploadServiceTest;

public class UploadServiceImplTest extends UploadServiceTest {

	@Mock
	private UploadRepository uploadRepository;
	
	@Mock
	private LogManager logger;
		
	@Override
	protected UploadService getService() {
		return new UploadServiceImpl(uploadRepository, logger);
	}
	
	@Test
	public void testGetTemporaryFile() {
		FileMetadata file = new FileMetadata();		
		when(uploadRepository.getTemporaryFile(idUser, 0)).thenReturn(null, file);
		
		super.testGetTemporaryFile();
		
		verify(uploadRepository, times(2)).getTemporaryFile(anyInt(), anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testGetTemporaryFiles() {
		List<FileMetadata> uploadFiles = new ArrayList<FileMetadata>();
		FileMetadata uploadFile = new FileMetadata();
		uploadFiles.add(uploadFile);
		
		when(uploadRepository.getTemporaryFiles(idUser)).thenReturn(null, uploadFiles);
		
		super.testGetTemporaryFiles();
		
		verify(uploadRepository, times(2)).getTemporaryFiles(anyInt());
	}

	@Test
	public void testRemoveTemporaryFile() {
		FileMetadata file = new FileMetadata();		
		when(uploadRepository.getTemporaryFile(idUser, 0)).thenReturn(file, (FileMetadata)null);
		
		super.testRemoveTemporaryFile();
		
		verify(uploadRepository).removeTemporaryFile(anyInt(), anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testRemoveTemporaryFiles() {
		List<FileMetadata> uploadFiles = new ArrayList<FileMetadata>();
		FileMetadata uploadFile = new FileMetadata();
		uploadFiles.add(uploadFile);
		
		when(uploadRepository.getTemporaryFiles(idUser)).thenReturn(uploadFiles, (List<FileMetadata>)new ArrayList<FileMetadata>());
		
		super.testRemoveTemporaryFiles();
		
		verify(uploadRepository).removeTemporaryFiles(anyInt());
	}
}
