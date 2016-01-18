package com.julvez.pfc.teachonsnap.media;

import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Provides the functionality to work with lesson's media files.
 */
public interface MediaFileService {
	
	/**
	 * Return the lesson's associated media files
	 * @param idLessonMedia Lesson media id
	 * @return List of MediaFiles for this lesson
	 */
	public List<MediaFile> getLessonMedias(int idLessonMedia);

	/**
	 * Save a file as a Media file for this lesson
	 * @param lesson to save file in
	 * @param file to be saved as MediaFile
	 * @return new MediaFile's id created
	 * @see MediaFile
	 */
	public int saveMediaFile(Lesson lesson, FileMetadata file);

	/**
	 * Return list of accepted media types by the application
	 * @returnlist of accepted media types
	 */
	public List<String> getAcceptedFileTypes();

	/**
	 * Remove all media files from this lesson
	 * @param lesson to remove media files from
	 * @return modified lesson, null otherwise
	 */
	public Lesson removeMediaFiles(Lesson lesson);

	/**
	 * Returns if the author exceeded his disk quota in the current media repository
	 * @param author to check quota
	 * @param newFileSize new file size in bbytes to check if fits on the remaining quota capacity
	 * @return true if author's quota is exceeded
	 */
	public boolean isAuthorQuotaExceeded(User author, long newFileSize);

	/**
	 * Returns if the current media repository is full and the new file size is over its capacity
	 * @param idMediaRepository Repository to check if the max capacity is reached.
	 * @param newFileSize to check if fits on the remaining repository's capacity
	 * @return true if repository is full and no files with this size are allowed
	 */
	public boolean isRepositoryFull(short idMediaRepository, long newFileSize);		
	
}
