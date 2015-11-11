package com.julvez.pfc.teachonsnap.upload.impl;

import java.util.List;

import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.upload.UploadService;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.upload.repository.UploadRepository;
import com.julvez.pfc.teachonsnap.upload.repository.UploadRepositoryFactory;
import com.julvez.pfc.teachonsnap.user.model.User;

public class UploadServiceImpl implements UploadService {
	
	private UploadRepository uploadRepository = UploadRepositoryFactory.getRepository();

	@Override
	public FileMetadata getTemporaryFile(User user, int index) {
		FileMetadata file = null;
		
		if(user !=null && index >= 0){
			file = uploadRepository.getTemporaryFile(user.getId(), index);
		}
		
		return file;
	}

	@Override
	public List<FileMetadata> getTemporaryFiles(User user) {
		List<FileMetadata> files = null;
		
		if(user!=null){
			files = uploadRepository.getTemporaryFiles(user.getId());
		}
				
		return files;
	}

	@Override
	public void addTemporaryFiles(User user, List<FileMetadata> uploadFiles) {
		if(user!=null && uploadFiles!=null && uploadFiles.size()>0){
			uploadRepository.addTemporaryFiles(user.getId(), uploadFiles);
		}
		
	}

	@Override
	public void removeTemporaryFile(User user, int index) {
		if(user!=null && index>=0){
			uploadRepository.removeTemporaryFile(user.getId(), index);
		}
		
	}

	@Override
	public void removeTemporaryFiles(User user) {
		if(user!=null){
			uploadRepository.removeTemporaryFiles(user.getId());
		}
		
	}

	@Override
	public MediaType getMediaType(String contentType) {
		MediaType type = null;
		
		if(contentType != null){
			String[] matches = contentType.split("/");
			
			if(matches!=null && matches.length>0){
				try{
					type = MediaType.valueOf(matches[0].toUpperCase());
				}
				catch(IllegalArgumentException iae){
					iae.getStackTrace();
				}
			}
		}		
		
		return type;
	}

}
