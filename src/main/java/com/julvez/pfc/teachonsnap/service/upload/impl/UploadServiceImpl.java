package com.julvez.pfc.teachonsnap.service.upload.impl;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.upload.ContentType;
import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.repository.upload.UploadRepository;
import com.julvez.pfc.teachonsnap.repository.upload.UploadRepositoryFactory;
import com.julvez.pfc.teachonsnap.service.upload.UploadService;

public class UploadServiceImpl implements UploadService {
	
	private UploadRepository uploadRepository = UploadRepositoryFactory.getRepository();

	@Override
	public FileMetadata getTemporaryFile(User user, ContentType contentType, int index) {
		FileMetadata file = null;
		
		if(user!=null && index>=0){
			file = uploadRepository.getTemporaryFile(user.getId(),contentType,index);
		}
		
		return file;
	}

	@Override
	public List<FileMetadata> getTemporaryFiles(User user, ContentType contentType) {
		List<FileMetadata> files = null;
		
		if(user!=null){
			files = uploadRepository.getTemporaryFiles(user.getId(),contentType);
		}
				
		return files;
	}

	@Override
	public void addTemporaryFiles(User user, ContentType contentType, List<FileMetadata> uploadFiles) {
		if(user!=null && uploadFiles!=null && uploadFiles.size()>0){
			uploadRepository.addTemporaryFiles(user.getId(),contentType,uploadFiles);
		}
		
	}

	@Override
	public void removeTemporaryFile(User user, ContentType contentType, int index) {
		if(user!=null && index>=0){
			uploadRepository.removeTemporaryFile(user.getId(),contentType,index);
		}
		
	}

}