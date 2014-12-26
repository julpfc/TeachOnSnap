package com.julvez.pfc.teachonsnap.service.upload;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;
import com.julvez.pfc.teachonsnap.model.user.User;

public interface UploadService {

	FileMetadata getTemporaryFile(User user, int index);

	List<FileMetadata> getTemporaryFiles(User user);

	void addTemporaryFiles(User user, List<FileMetadata> uploadFiles);

	void removeTemporaryFile(User user, int index);
	
	

}
