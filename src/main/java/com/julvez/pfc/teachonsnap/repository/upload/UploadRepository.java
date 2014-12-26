package com.julvez.pfc.teachonsnap.repository.upload;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;

public interface UploadRepository {

	public FileMetadata getTemporaryFile(int idUser, int index);

	public List<FileMetadata> getTemporaryFiles(int idUser);

	public void addTemporaryFiles(int idUser, List<FileMetadata> uploadFiles);
	
	public void close();

	public void removeTemporaryFile(int idUser, int index);

}
