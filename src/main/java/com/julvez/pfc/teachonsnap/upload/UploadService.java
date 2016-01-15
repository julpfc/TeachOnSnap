package com.julvez.pfc.teachonsnap.upload;

import java.util.List;

import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Provides the functionality to upload files to the application.
 */
public interface UploadService {

	/**
	 * Returns file metadata from user's stored file at the upload repository
	 * (identified by the user and the index position)
	 * @param user 's file metadata
	 * @param index at the user's upload repository
	 * @return File's metadata if exists for this user-index, null otherwise
	 */
	public FileMetadata getTemporaryFile(User user, int index);
	
	/**
	 * Removes file metadata from user's stored file at the upload repository
	 * (identified by the user and the index position), closing the file's input stream
	 * @param user 's file metadata
	 * @param index at the user's upload repository
	 */
	public void removeTemporaryFile(User user, int index);

	/**
	 * Returns a list of file metadata from user's upload repository
	 * @param user 's files metadatas
	 * @return List of files metadatas
	 */
	public List<FileMetadata> getTemporaryFiles(User user);
	
	/**
	 * Removes all file metadata from user's upload repository
	 * @param user 's files metadatas
	 */
	public void removeTemporaryFiles(User user);
	
	/**
	 * Adds a list of file metadatas to the user's upload repository
	 * @param user 's files metadatas
	 * @param uploadFiles list of file metadatas
	 */
	public void addTemporaryFiles(User user, List<FileMetadata> uploadFiles);
	
	/**
	 * Returns the corresponding MediaType from the file content-type if
	 * it's recognized by the application. Null otherwise.
	 * @param contentType from afile to determine its MediaType
	 * @return MediaType corresponding to the specified contentType if it's supported by the application.
	 */
	public MediaType getMediaType(String contentType);

}
