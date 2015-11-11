package com.julvez.pfc.teachonsnap.upload;

import java.util.List;

import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.user.model.User;

public interface UploadService {

	FileMetadata getTemporaryFile(User user, int index);
	void removeTemporaryFile(User user, int index);

	List<FileMetadata> getTemporaryFiles(User user);
	void removeTemporaryFiles(User user);
	
	void addTemporaryFiles(User user, List<FileMetadata> uploadFiles);
	MediaType getMediaType(String contentType);

	
	

}
