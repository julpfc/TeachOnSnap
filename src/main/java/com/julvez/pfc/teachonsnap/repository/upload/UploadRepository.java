package com.julvez.pfc.teachonsnap.repository.upload;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.upload.ContentType;
import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;

public interface UploadRepository {

	public FileMetadata getTemporaryFile(int idUser, ContentType contentType, int index);

	public List<FileMetadata> getTemporaryFiles(int idUser, ContentType contentType);

	public void addTemporaryFiles(int idUser, ContentType contentType, List<FileMetadata> uploadFiles);
	
	public void close();

	public void removeTemporaryFile(int idUser, ContentType contentType, int index);

}
