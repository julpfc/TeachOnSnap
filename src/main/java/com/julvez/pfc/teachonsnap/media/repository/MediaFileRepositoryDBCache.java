package com.julvez.pfc.teachonsnap.media.repository;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.media.model.MediaFileRepositoryPath;
import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;

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
		
		int id =(int)cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson), stringManager.getKey(repoPath.getId())}, 
				new String[]{"getLesson","getRepositorySize"}, idLesson, file, repoPath, idMediaMimeType);
		cache.clearCache("getAuthorQuotaUsed");
		return id;
	}

	@Override
	public MediaFileRepositoryPath getMediaFileRepositoryPath(short idMediaRepository) {
		return (MediaFileRepositoryPath)cache.executeImplCached(repoDB, idMediaRepository);
	}

	@Override
	public short createMimeTypeID(MediaType mediaType, String fileType,	String fileName) {
		return (short)cache.updateImplCached(repoDB, null, null, mediaType, fileType, fileName);
	}

	@Override
	public boolean removeMediaFiles(int idLesson, ArrayList<MediaFile> medias, MediaFileRepositoryPath repoPath) {
		cache.clearCache("getAuthorQuotaUsed");
		return (boolean)cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson), stringManager.getKey(repoPath.getId())}, 
				new String[]{"getLesson","getRepositorySize"}, idLesson, medias, repoPath);
	}

	@Override
	public long getAuthorQuotaUsed(int idUser) {
		return (long)cache.executeImplCached(repoDB, idUser);
	}

	@Override
	public long getRepositorySize(short idMediaRepository) {
		return (long)cache.executeImplCached(repoDB, idMediaRepository);
	}
	
	
}
