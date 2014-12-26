package com.julvez.pfc.teachonsnap.service.upload.impl;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.repository.upload.UploadRepository;
import com.julvez.pfc.teachonsnap.repository.upload.UploadRepositoryFactory;
import com.julvez.pfc.teachonsnap.service.upload.UploadService;

public class UploadServiceImpl implements UploadService {
	
	private UploadRepository uploadRepository = UploadRepositoryFactory.getRepository();

	@Override
	public FileMetadata getTemporaryFile(User user, int index) {
		FileMetadata file = null;
		
		if(user!=null && index>=0){
			file = uploadRepository.getTemporaryFile(user.getId(),index);
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
			uploadRepository.addTemporaryFiles(user.getId(),uploadFiles);
		}
		
	}

	@Override
	public void removeTemporaryFile(User user, int index) {
		if(user!=null && index>=0){
			uploadRepository.removeTemporaryFile(user.getId(),index);
		}
		
	}

}
