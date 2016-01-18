package com.julvez.pfc.teachonsnap.media.repository;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.media.MediaFileService;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.media.model.MediaFileRepositoryPath;
import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;

/**
 * Repository to access/modify data related to lesson's media files.
 * <p>
 * To be used only by the {@link MediaFileService}'s implementation
 */
public interface MediaFileRepository {

	/**
	 * Return the lesson's associated media files
	 * @param idLessonMedia Lesson media id
	 * @return List of MediaFiles for this lesson
	 */
	public List<MediaFile> getLessonMedias(int idLessonMedia);

	/**
	 * Get mime type id for this MediaType and file content type 
	 * @param mediaType MediaType for this mime
	 * @param fileType content type for this mime
	 * @return mimetype's id if exists in the repository
	 */
	public short getMimeTypeID(MediaType mediaType, String fileType);

	/**
	 * Returns application's default repository
	 * @return default repository id
	 */
	public short getDefaultRepositoryID();

	/**
	 * Save a file as a Media file for this lesson
	 * @param idLesson to save file in
	 * @param file to be saved as MediaFile
	 * @param repoPath Repository where save the file to
	 * @param idMediaMimeType File's mimetype
	 * @return new MediaFile's id created
	 * @see MediaFile
	 */	 
	public int saveMediaFile(int idLesson, FileMetadata file, MediaFileRepositoryPath repoPath, short idMediaMimeType);

	/**
	 * Returns the MediaFileRepositoryPath object for this id
	 * @param idMediaRepository to look for
	 * @return the MediaFileRepositoryPath if exists for this id, null otherwise
	 */
	public MediaFileRepositoryPath getMediaFileRepositoryPath(short idMediaRepository);

	/**
	 * Creates a new mime type for this MediaType, contentType and file extension
	 * @param mediaType MediaType of this mimetype
	 * @param fileType content type associated to this mimetype
	 * @param fileName to extract the file extension from
	 * @return new created mime type id
	 */
	public short createMimeTypeID(MediaType mediaType, String fileType, String fileName);
	
	/**
	 * Remove all media files from this lesson
	 * @param idLesson to remove media files from
	 * @param medias to be removed
	 * @param repoPath repository where the medias will be removed
	 * @return true if success
	 */
	public boolean removeMediaFiles(int idLesson, ArrayList<MediaFile> medias, MediaFileRepositoryPath repoPath);

	/**
	 * Returns used repository size in bytes for this user
	 * @param idUser to check used respository size
	 * @return user's used repository size in bytes
	 */
	public long getAuthorQuotaUsed(int idUser);

	/**
	 * Returns current repository size in bytes
	 * @param idMediaRepository Reposiotry to check in
	 * @return repository's current size in bytes
	 */
	public long getRepositorySize(short idMediaRepository);
		
}
