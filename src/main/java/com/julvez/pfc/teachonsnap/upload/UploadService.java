package com.julvez.pfc.teachonsnap.upload;

import java.util.List;

import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.user.model.User;

public interface UploadService {

	FileMetadata getTemporaryFile(User user, MediaType contentType, int index);
	void removeTemporaryFile(User user, MediaType contentType, int index);

	List<FileMetadata> getTemporaryFiles(User user, MediaType contentType);
	void removeTemporaryFiles(User user);
	
	void addTemporaryFiles(User user, MediaType contentType, List<FileMetadata> uploadFiles);

	
	

}
