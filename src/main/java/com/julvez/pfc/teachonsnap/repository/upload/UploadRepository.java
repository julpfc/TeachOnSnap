package com.julvez.pfc.teachonsnap.repository.upload;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.media.MediaType;
import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;

public interface UploadRepository {

	public FileMetadata getTemporaryFile(int idUser, MediaType contentType, int index);

	public List<FileMetadata> getTemporaryFiles(int idUser, MediaType contentType);

	public void addTemporaryFiles(int idUser, MediaType contentType, List<FileMetadata> uploadFiles);
	
	public void close();

	public void removeTemporaryFile(int idUser, MediaType contentType, int index);

}
