package com.julvez.pfc.teachonsnap.service.upload;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.media.MediaType;
import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;
import com.julvez.pfc.teachonsnap.model.user.User;

public interface UploadService {

	FileMetadata getTemporaryFile(User user, MediaType contentType, int index);

	List<FileMetadata> getTemporaryFiles(User user, MediaType contentType);

	void addTemporaryFiles(User user, MediaType contentType, List<FileMetadata> uploadFiles);

	void removeTemporaryFile(User user, MediaType contentType, int index);
	
	

}
