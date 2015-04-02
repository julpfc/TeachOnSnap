package com.julvez.pfc.teachonsnap.repository.media.db;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.file.FileManager;
import com.julvez.pfc.teachonsnap.manager.file.FileManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.model.media.MediaFile;
import com.julvez.pfc.teachonsnap.model.media.MediaFileRepositoryPath;
import com.julvez.pfc.teachonsnap.model.media.MediaType;
import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;
import com.julvez.pfc.teachonsnap.repository.media.MediaFileRepository;

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
		Short obj = dbm.getQueryResultUnique("SQL_MEDIA_GET_MIMETYPEID", Short.class, mediaType.ordinal(),fileType);
		if(obj!=null)
			id = obj;
		return id; 		
	}

	@Override
	public short getDefaultRepositoryID() {
		short id = 1;
		String prop = properties.getProperty(MediaFileRepositoryPropertyName.MEDIAFILE_DEFAULT_REPOSITORY);
		
		try{
			id = Short.parseShort(prop);
		}
		catch(Throwable t){
			t.printStackTrace();
		}
		
		return id; 
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
		return (int)dbm.insertQueryAndGetLastInserID_NoCommit(session,"SQL_MEDIA_CREATE_MEDIAFILE", idLessonMedia,
				idMediaRepository, idMediaMimeType, fileName);
	}

	private int createLessonMedia(Object session, int idLesson){
		return (int)dbm.insertQueryAndGetLastInserID_NoCommit(session,"SQL_MEDIA_CREATE_LESSONMEDIA", idLesson);
	}

	@Override
	public MediaFileRepositoryPath getMediaFileRepositoryPath(short idMediaRepository) {
		return dbm.getQueryResultUnique("SQL_MEDIA_GET_MEDIAREPOSITORY", MediaFileRepositoryPath.class, idMediaRepository);
	}

}
