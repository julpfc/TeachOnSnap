package com.julvez.pfc.teachonsnap.upload.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;

public interface UploadRepository {

	public FileMetadata getTemporaryFile(int idUser, MediaType contentType, int index);

	public List<FileMetadata> getTemporaryFiles(int idUser, MediaType contentType);

	public void addTemporaryFiles(int idUser, MediaType contentType, List<FileMetadata> uploadFiles);
	
	public void close();

	public void removeTemporaryFile(int idUser, MediaType contentType, int index);

	public void removeTemporaryFiles(int idUser);

}
