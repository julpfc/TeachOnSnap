package com.julvez.pfc.teachonsnap.upload.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.upload.UploadService;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;

/**
 * Repository to access/modify upload files.
 * <p>
 * To be used only by the {@link UploadService}'s implementation
 */
public interface UploadRepository {

	/**
	 * Returns file metadata from user's stored file
	 * (identified by the user and the index position)
	 * @param idUser user's file metadata
	 * @param index at the user's upload repository
	 * @return File's metadata if exists for this user-index, null otherwise
	 */
	public FileMetadata getTemporaryFile(int idUser, int index);

	/**
	 * Returns a list of file metadata from user's upload repository
	 * @param idUser user's files metadatas
	 * @return List of files metadatas
	 */
	public List<FileMetadata> getTemporaryFiles(int idUser);

	/**
	 * Adds a list of file metadatas to the user's upload repository
	 * @param idUser user's files metadatas
	 * @param uploadFiles list of file metadatas
	 */
	public void addTemporaryFiles(int idUser, List<FileMetadata> uploadFiles);
	
	/**
	 * Closes the repository. Closes all files input streams.
	 */
	public void close();

	/**
	 * Removes file metadata from user's stored file
	 * (identified by the user and the index position), closing the file's input stream
	 * @param idUser user's file metadata
	 * @param index at the user's upload repository
	 */
	public void removeTemporaryFile(int idUser, int index);

	/**
	 * Removes all file metadata from user's upload repository
	 * @param idUser user's files metadatas
	 */
	public void removeTemporaryFiles(int idUser);

}
