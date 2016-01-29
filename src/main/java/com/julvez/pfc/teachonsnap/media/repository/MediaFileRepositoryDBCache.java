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

/**
 * Repository implementation to access/modify data from a Database through a cache.
 * <p>
 * A repository database implementation ({@link MediaFileRepositoryDB}) is used to provide the database layer under the cache.
 * <p>
 * {@link CacheManager} is used to provide a cache system
 */
public class MediaFileRepositoryDBCache implements MediaFileRepository {

	/** Database repository providing data access and modification capabilities */
	private MediaFileRepositoryDB repoDB;
	
	/** Cache manager providing access/modification capabilities to the cache system */
	private CacheManager cache = CacheManagerFactory.getManager();
	
	/** String manager providing string manipulation utilities */
	private StringManager stringManager = StringManagerFactory.getManager();

	
	/**
	 * Constructor requires all parameters not to be null
	 * @param repoDB Database repository providing data access and modification capabilities
	 * @param cache Cache manager providing access/modification capabilities to the cache system
	 * @param stringManager String manager providing string manipulation utilities
	 */
	public MediaFileRepositoryDBCache(MediaFileRepositoryDB repoDB,
			CacheManager cache, StringManager stringManager) {
		
		if(repoDB == null || stringManager == null || cache == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		
		this.repoDB = repoDB;
		this.cache = cache;
		this.stringManager = stringManager;
	}

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
