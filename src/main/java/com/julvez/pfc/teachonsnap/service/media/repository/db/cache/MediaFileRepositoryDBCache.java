package com.julvez.pfc.teachonsnap.service.media.repository.db.cache;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.media.MediaFile;
import com.julvez.pfc.teachonsnap.model.media.MediaFileRepositoryPath;
import com.julvez.pfc.teachonsnap.model.media.MediaType;
import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;
import com.julvez.pfc.teachonsnap.service.media.repository.MediaFileRepository;
import com.julvez.pfc.teachonsnap.service.media.repository.db.MediaFileRepositoryDB;

public class MediaFileRepositoryDBCache implements MediaFileRepository {

	private MediaFileRepositoryDB repoDB = new MediaFileRepositoryDB();
	private CacheManager cache = CacheManagerFactory.getCacheManager();
	private StringManager stringManager = StringManagerFactory.getManager();

	@SuppressWarnings("unchecked")
	@Override
	public List<MediaFile> getLessonMedias(int idLessonMedia) {
		return (List<MediaFile>)cache.executeImplCached(repoDB, idLessonMedia);
	}

	@Override
	public short getMimeTypeID(MediaType mediaType, String fileType) {
		return (short)cache.executeImplCached(repoDB, mediaType, fileType);
	}

	@Override
	public short getDefaultRepositoryID() {
		return repoDB.getDefaultRepositoryID();
	}

	@Override
	public int saveMediaFile(int idLesson, FileMetadata file,
			MediaFileRepositoryPath repoPath, short idMediaMimeType) {
		
		int id =(int)cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getLesson"}, idLesson, file, repoPath, idMediaMimeType);
					
		return id;
	}

	@Override
	public MediaFileRepositoryPath getMediaFileRepositoryPath(short idMediaRepository) {
		return (MediaFileRepositoryPath)cache.executeImplCached(repoDB, idMediaRepository);
	}
	
	
}
