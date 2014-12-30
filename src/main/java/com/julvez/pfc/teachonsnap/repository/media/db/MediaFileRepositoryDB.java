package com.julvez.pfc.teachonsnap.repository.media.db;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.file.FileManager;
import com.julvez.pfc.teachonsnap.manager.file.FileManagerFactory;
import com.julvez.pfc.teachonsnap.model.media.MediaFile;
import com.julvez.pfc.teachonsnap.model.media.MediaFileRepositoryPath;
import com.julvez.pfc.teachonsnap.model.media.MediaType;
import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;
import com.julvez.pfc.teachonsnap.repository.media.MediaFileRepository;

public class MediaFileRepositoryDB implements MediaFileRepository {

	private DBManager dbm = DBManagerFactory.getDBManager();
	private FileManager fileManager = FileManagerFactory.getManager();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MediaFile> getLessonMedias(int idLessonMedia) {
		return (List<MediaFile>)dbm.getQueryResultList("SQL_MEDIA_GET_MEDIAFILES", MediaFile.class, idLessonMedia);
	}

	@Override
	public short getMimeTypeID(MediaType mediaType, String fileType) {
		short id = -1;
		Object obj = dbm.getQueryResultUnique("SQL_MEDIA_GET_MIMETYPEID", null, mediaType.ordinal(),fileType);
		if(obj!=null)
			id = Short.parseShort(obj.toString());
		return id; 		
	}

	@Override
	public short getDefaultRepositoryID() {
		//TODO Sacarlo de un properties
		return 1; 
	}

	@Override
	public int saveMediaFile(int idLesson, FileMetadata file,
			MediaFileRepositoryPath repoPath, short idMediaMimeType) {
		
		int idMediaFile = -1;
		
		Object session = dbm.beginTransaction();
		
		int idLessonMedia = createLessonMedia(session, idLesson);
		
		if(idLessonMedia>0){
			idMediaFile = createMediaFile(session, idLessonMedia, repoPath.getId(), idMediaMimeType, file.getFileName());
			
			if(idMediaFile>0){
				String path = repoPath.getURI() + repoPath.getFilePathSeparator() +
						idLessonMedia + repoPath.getFilePathSeparator()	+ 
						idMediaFile + repoPath.getFilePathSeparator();
				
				boolean copyOK = fileManager.copyStream(file.getContent(),path,file.getFileName());
				
				if(!copyOK){
					idMediaFile = -1;
				}
				else{
					//TODO Borrar fichero temporal
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
			short idMediaRepository, short idMediaMimeType, String fileName) {
	
		return (int)dbm.updateQuery_NoCommit(session,"SQL_MEDIA_CREATE_MEDIAFILE", idLessonMedia,
				idMediaRepository, idMediaMimeType, fileName);
	}

	private int createLessonMedia(Object session, int idLesson){
		return (int)dbm.updateQuery_NoCommit(session,"SQL_MEDIA_CREATE_LESSONMEDIA", idLesson);
	}

	@Override
	public MediaFileRepositoryPath getMediaFileRepositoryPath(short idMediaRepository) {
		return (MediaFileRepositoryPath)dbm.getQueryResultUnique("SQL_MEDIA_GET_MEDIAREPOSITORY", MediaFileRepositoryPath.class, idMediaRepository);
	}

}
