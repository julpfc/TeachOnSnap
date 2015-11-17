package com.julvez.pfc.teachonsnap.media.repository;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.file.FileManager;
import com.julvez.pfc.teachonsnap.manager.file.FileManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.media.model.MediaFileRepositoryPath;
import com.julvez.pfc.teachonsnap.media.model.MediaPropertyName;
import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;

public class MediaFileRepositoryDB implements MediaFileRepository {

	private DBManager dbm = DBManagerFactory.getDBManager();
	private FileManager fileManager = FileManagerFactory.getManager();
	private PropertyManager properties = PropertyManagerFactory.getManager();
	
	@Override
	public List<MediaFile> getLessonMedias(int idLessonMedia) {
		return dbm.getQueryResultList("SQL_MEDIA_GET_MEDIAFILES", MediaFile.class, idLessonMedia);
	}

	@Override
	public short getMimeTypeID(MediaType mediaType, String fileType) {
		short id = -1;
		Byte obj = dbm.getQueryResultUnique("SQL_MEDIA_GET_MIMETYPEID", Byte.class, mediaType.ordinal(),fileType);
		if(obj!=null)
			id = obj;
		return id; 		
	}

	@Override
	public short getDefaultRepositoryID() {
		int id = properties.getNumericProperty(MediaPropertyName.MEDIAFILE_DEFAULT_REPOSITORY);
		
		if(id == -1){
			id=1;
		}
		
		return (short)id; 
	}

	@Override
	public int saveMediaFile(int idLesson, FileMetadata file,
			MediaFileRepositoryPath repoPath, short idMediaMimeType) {
		
		int idMediaFile = -1;
		
		Object session = dbm.beginTransaction();
		
		int idLessonMedia = createLessonMedia(session, idLesson);
		
		if(idLessonMedia>0){
			int fileSize = Integer.parseInt(file.getFileSize());
			idMediaFile = createMediaFile(session, idLessonMedia, repoPath.getId(), idMediaMimeType, file.getFileName(), fileSize);
			
			if(idMediaFile>0){
				String path = repoPath.getURI() + repoPath.getFilePathSeparator() +
						idLessonMedia + repoPath.getFilePathSeparator()	+ 
						idMediaFile + repoPath.getFilePathSeparator();
				
				boolean copyOK = fileManager.copyStream(file.getContent(),path,file.getFileName());
				
				if(!copyOK){
					idMediaFile = -1;
				}				
			}
		}
		
		if(idMediaFile>0){
			dbm.endTransaction(true, session);
		}
		else{
			dbm.endTransaction(false, session);
		}

		return idMediaFile;
	}

	private int createMediaFile(Object session, int idLessonMedia,
			short idMediaRepository, short idMediaMimeType, String fileName, int fileSize) {
		return (int)dbm.insertQueryAndGetLastInserID_NoCommit(session,"SQL_MEDIA_CREATE_MEDIAFILE", idLessonMedia,
				idMediaRepository, idMediaMimeType, fileName, fileSize);
	}

	private int createLessonMedia(Object session, int idLesson){
		return (int)dbm.insertQueryAndGetLastInserID_NoCommit(session,"SQL_MEDIA_CREATE_LESSONMEDIA", idLesson);
	}

	@Override
	public MediaFileRepositoryPath getMediaFileRepositoryPath(short idMediaRepository) {
		return dbm.getQueryResultUnique("SQL_MEDIA_GET_MEDIAREPOSITORY", MediaFileRepositoryPath.class, idMediaRepository);
	}

	@Override
	public short createMimeTypeID(MediaType mediaType, String fileType,	String fileName) {
		String ext = fileManager.getFileExtension(fileName);
		return (short) dbm.insertQueryAndGetLastInserID("SQL_MEDIA_CREATE_MIMETYPE", mediaType.ordinal(), fileType, ext);
	}

	@Override
	public boolean removeMediaFiles(int idLesson, ArrayList<MediaFile> medias, MediaFileRepositoryPath repoPath) {

		int affectedRows = -1;
		int idLessonMedia = -1;
		
		Object session = dbm.beginTransaction();
		
		for(MediaFile file:medias){
			affectedRows = -1;
			
			idLessonMedia = file.getIdLessonMedia();
			
			String path = repoPath.getURI() + repoPath.getFilePathSeparator() +
					file.getIdLessonMedia() + repoPath.getFilePathSeparator()	+ 
					file.getId() + repoPath.getFilePathSeparator();

			boolean deleteOK = fileManager.delete(path, file.getFilename());
			
			if(deleteOK){
				affectedRows = dbm.updateQuery_NoCommit(session, "SQL_MEDIA_DELETE_MEDIAFILE", file.getId());
				
				if(affectedRows < 0){
					break;
				}
			}				
		}
		
		if(affectedRows >= 0){
			affectedRows = dbm.updateQuery_NoCommit(session, "SQL_MEDIA_DELETE_LESSONMEDIA", idLessonMedia);
		}		
				
		if(affectedRows >= 0){
			dbm.endTransaction(true, session);
		}
		else{
			dbm.endTransaction(false, session);
		}
		
		return affectedRows >= 0;
	}

}