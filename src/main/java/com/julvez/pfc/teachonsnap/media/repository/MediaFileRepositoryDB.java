package com.julvez.pfc.teachonsnap.media.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.file.FileManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.media.model.MediaFileRepositoryPath;
import com.julvez.pfc.teachonsnap.media.model.MediaPropertyName;
import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;

/**
 * Repository implementation to access/modify data from a Database
 * <p>
 * {@link DBManager} is used to provide database access
 */
public class MediaFileRepositoryDB implements MediaFileRepository {

	/** Database manager providing access/modification capabilities */
	private DBManager dbm;
	
	/** File manager providing access to the repository's file system */
	private FileManager fileManager;
	
	/** Property manager providing access to properties files */
	private PropertyManager properties;
	
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param dbm Database manager providing access/modification capabilities
	 * @param fileManager File manager providing access to the repository's file system
	 * @param properties Property manager providing access to properties files
	 */
	public MediaFileRepositoryDB(DBManager dbm, FileManager fileManager,
			PropertyManager properties) {
		
		if(dbm == null || fileManager == null || properties == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		
		this.dbm = dbm;
		this.fileManager = fileManager;
		this.properties = properties;
	}

	@Override
	public List<MediaFile> getLessonMedias(int idLessonMedia) {
		return dbm.getQueryResultList("SQL_MEDIA_GET_MEDIAFILES", MediaFile.class, idLessonMedia);
	}

	@Override
	public short getMimeTypeID(MediaType mediaType, String fileType) {
		short id = -1;
		if(mediaType != null && fileType != null){
			Byte obj = dbm.getQueryResultUnique("SQL_MEDIA_GET_MIMETYPEID", Byte.class, mediaType.ordinal(),fileType);
			if(obj!=null)
				id = obj;
		}
		return id; 		
	}

	@Override
	public short getDefaultRepositoryID() {
		long id = properties.getNumericProperty(MediaPropertyName.DEFAULT_REPOSITORY);
		
		if(id == -1){
			id = 1;
		}		
		return (short)id; 
	}

	@Override
	public int saveMediaFile(int idLesson, FileMetadata file,
			MediaFileRepositoryPath repoPath, short idMediaMimeType) {
		
		int idMediaFile = -1;
		
		//Begin database transaction
		Object session = dbm.beginTransaction();
		
		//Create lesson media
		int idLessonMedia = createLessonMedia(session, idLesson);
		
		if(idLessonMedia>0 && file != null && repoPath != null){
			//Create media file
			long fileSize = Long.parseLong(file.getFileSize());
			idMediaFile = createMediaFile(session, idLessonMedia, repoPath.getId(), idMediaMimeType, file.getFileName(), fileSize);
			
			if(idMediaFile>0){
				//get file path
				String path = repoPath.getURI() + repoPath.getFilePathSeparator() +
						idLessonMedia + repoPath.getFilePathSeparator()	+ 
						idMediaFile + repoPath.getFilePathSeparator();
				
				//Copy file to the repository file system
				boolean copyOK = fileManager.copyStream(file.getContent(),path,file.getFileName());
				
				if(!copyOK){
					idMediaFile = -1;
				}				
			}
		}
		
		//if success, commit, rollback otherwise
		if(idMediaFile>0){
			dbm.endTransaction(true, session);
		}
		else{
			dbm.endTransaction(false, session);
		}

		return idMediaFile;
	}

	@Override
	public MediaFileRepositoryPath getMediaFileRepositoryPath(short idMediaRepository) {
		return dbm.getQueryResultUnique("SQL_MEDIA_GET_MEDIAREPOSITORY", MediaFileRepositoryPath.class, idMediaRepository);
	}

	@Override
	public short createMimeTypeID(MediaType mediaType, String fileType,	String fileName) {
		if(mediaType != null && fileType != null && fileName != null){
			String ext = fileManager.getFileExtension(fileName);
			return (short) dbm.insertQueryAndGetLastInserID("SQL_MEDIA_CREATE_MIMETYPE", mediaType.ordinal(), fileType, ext);
		}
		else return -1;
	}

	@Override
	public boolean removeMediaFiles(int idLesson, ArrayList<MediaFile> medias, MediaFileRepositoryPath repoPath) {

		int affectedRows = -1;
		int idLessonMedia = -1;
		
		if(medias != null){
			//Begin database transaction
			Object session = dbm.beginTransaction();
			
			//for each media file
			for(MediaFile file:medias){
				affectedRows = -1;
				
				idLessonMedia = file.getIdLessonMedia();
				
				//get file path
				String path = repoPath.getURI() + repoPath.getFilePathSeparator() +
						file.getIdLessonMedia() + repoPath.getFilePathSeparator()	+ 
						file.getId() + repoPath.getFilePathSeparator();
	
				//detele file from filesystem
				boolean deleteOK = fileManager.delete(path, file.getFilename());
				
				//delete media file 
				if(deleteOK){
					affectedRows = dbm.updateQuery_NoCommit(session, "SQL_MEDIA_DELETE_MEDIAFILE", file.getId());
					
					if(affectedRows < 0){
						break;
					}
				}				
			}
			
			//delete lesson media
			if(affectedRows >= 0){
				affectedRows = dbm.updateQuery_NoCommit(session, "SQL_MEDIA_DELETE_LESSONMEDIA", idLessonMedia);
			}		
			
			//if success, commit, rollback otherwise
			if(affectedRows >= 0){
				dbm.endTransaction(true, session);
			}
			else{
				dbm.endTransaction(false, session);
			}
		}
		return affectedRows >= 0;
	}

	@Override
	public long getAuthorQuotaUsed(int idUser) {
		long quota = -1;
		BigDecimal result = dbm.getQueryResultUnique("SQL_MEDIA_GET_AUTHOR_QUOTA_USED", BigDecimal.class, idUser);
		
		if(result!=null){
			quota = result.longValue();
		}
		return quota;
	}

	@Override
	public long getRepositorySize(short idMediaRepository) {
		long size = -1;
		BigDecimal result = dbm.getQueryResultUnique("SQL_MEDIA_GET_REPOSITORY_SIZE_USED", BigDecimal.class, idMediaRepository);
		
		if(result!=null){
			size = result.longValue();
		}
		return size;		
	}

	/**
	 * Creates a new media file
	 * @param session Open transaction in the database
	 * @param idLessonMedia for this media file
	 * @param idMediaRepository where the media file will be stored
	 * @param idMediaMimeType for this file
	 * @param fileName File's name
	 * @param fileSize File's size in bytes
	 * @return new media file id created, -1 otherwise
	 */
	private int createMediaFile(Object session, int idLessonMedia,
			short idMediaRepository, short idMediaMimeType, String fileName, long fileSize) {
		return (int)dbm.insertQueryAndGetLastInserID_NoCommit(session,"SQL_MEDIA_CREATE_MEDIAFILE", idLessonMedia,
				idMediaRepository, idMediaMimeType, fileName, fileSize);
	}

	/**
	 * Creates a new lesson media
	 * @param session Open transaction in database
	 * @param idLesson Lesson to create a lesson media
	 * @return idLessonMEdia created, -1 otherwise
	 */
	private int createLessonMedia(Object session, int idLesson){
		return (int)dbm.insertQueryAndGetLastInserID_NoCommit(session,"SQL_MEDIA_CREATE_LESSONMEDIA", idLesson);
	}
}
