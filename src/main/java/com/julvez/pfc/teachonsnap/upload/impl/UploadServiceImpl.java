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
	public FileMetadata getTemporaryFile(User user, MediaType contentType, int index) {
		FileMetadata file = null;
		
		if(user!=null && index>=0){
			file = uploadRepository.getTemporaryFile(user.getId(),contentType,index);
		}
		
		return file;
	}

	@Override
	public List<FileMetadata> getTemporaryFiles(User user, MediaType contentType) {
		List<FileMetadata> files = null;
		
		if(user!=null){
			files = uploadRepository.getTemporaryFiles(user.getId(),contentType);
		}
				
		return files;
	}

	@Override
	public void addTemporaryFiles(User user, MediaType contentType, List<FileMetadata> uploadFiles) {
		if(user!=null && uploadFiles!=null && uploadFiles.size()>0){
			uploadRepository.addTemporaryFiles(user.getId(),contentType,uploadFiles);
		}
		
	}

	@Override
	public void removeTemporaryFile(User user, MediaType contentType, int index) {
		if(user!=null && index>=0){
			uploadRepository.removeTemporaryFile(user.getId(),contentType,index);
		}
		
	}

	@Override
	public void removeTemporaryFiles(User user) {
		if(user!=null){
			uploadRepository.removeTemporaryFiles(user.getId());
		}
		
	}

}
