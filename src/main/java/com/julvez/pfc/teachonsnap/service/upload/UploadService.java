package com.julvez.pfc.teachonsnap.service.upload;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.upload.ContentType;
import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;
import com.julvez.pfc.teachonsnap.model.user.User;

public interface UploadService {

	FileMetadata getTemporaryFile(User user, ContentType contentType, int index);

	List<FileMetadata> getTemporaryFiles(User user, ContentType contentType);

	void addTemporaryFiles(User user, ContentType contentType, List<FileMetadata> uploadFiles);

	void removeTemporaryFile(User user, ContentType contentType, int index);
	
	

}
