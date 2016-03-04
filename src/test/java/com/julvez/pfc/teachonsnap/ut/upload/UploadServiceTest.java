package com.julvez.pfc.teachonsnap.ut.upload;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.upload.UploadService;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.ut.service.ServiceTest;

public abstract class UploadServiceTest extends ServiceTest<UploadService> {

	protected String audioType = "audio/mp3"; 
	protected String videoType = "video/mp4"; 
	protected String imageType = "image/jpg"; 
	protected String invalidType = "invalid/type"; 
	
	protected int idUser = 1;
	
	
	@Test
	public void testGetTemporaryFile() {
		User user = new User();
		user.setId(idUser);
		FileMetadata file = test.getTemporaryFile(user, 0);		
		assertNull(file);
		
		List<FileMetadata> uploadFiles = new ArrayList<FileMetadata>();
		FileMetadata uploadFile = new FileMetadata();
		uploadFiles.add(uploadFile);
		
		test.addTemporaryFiles(user, uploadFiles);
		
		file = test.getTemporaryFile(user, 0);		
		assertNotNull(file);
		assertEquals(uploadFile.getFileName(), file.getFileName());
	}

	@Test
	public void testGetTemporaryFiles() {
		User user = new User();
		user.setId(idUser);
		
		List<FileMetadata> files = test.getTemporaryFiles(user);		
		assertNull(files);
		
		List<FileMetadata> uploadFiles = new ArrayList<FileMetadata>();
		FileMetadata uploadFile = new FileMetadata();
		uploadFiles.add(uploadFile);
		
		test.addTemporaryFiles(user, uploadFiles);
		
		files = test.getTemporaryFiles(user);
		assertNotNull(files);
		assertEquals(uploadFiles.get(0).getFileName(), files.get(0).getFileName());
	}

	@Test
	public void testRemoveTemporaryFile() {
		User user = new User();
		user.setId(idUser);
		
		List<FileMetadata> uploadFiles = new ArrayList<FileMetadata>();
		FileMetadata uploadFile = new FileMetadata();
		uploadFiles.add(uploadFile);
		
		test.addTemporaryFiles(user, uploadFiles);
		
		FileMetadata file = test.getTemporaryFile(user, 0);		
		assertNotNull(file);
		
		test.removeTemporaryFile(user, 0);
		file = test.getTemporaryFile(user, 0);		
		assertNull(file);	
	}

	@Test
	public void testRemoveTemporaryFiles() {
		User user = new User();
		user.setId(idUser);
		
		List<FileMetadata> uploadFiles = new ArrayList<FileMetadata>();
		FileMetadata uploadFile = new FileMetadata();
		uploadFiles.add(uploadFile);
		
		test.addTemporaryFiles(user, uploadFiles);
		
		List<FileMetadata> files = test.getTemporaryFiles(user);		
		assertNotNull(files);
		
		test.removeTemporaryFiles(user);
		files = test.getTemporaryFiles(user);
		assertNull(files);	
	}

	@Test
	public void testGetMediaType() {
		assertEquals(MediaType.AUDIO, test.getMediaType(audioType));
		assertEquals(MediaType.IMAGE, test.getMediaType(imageType));
		assertEquals(MediaType.VIDEO, test.getMediaType(videoType));
		assertNull(test.getMediaType(invalidType));
		assertNull(test.getMediaType(null));
	}
}
