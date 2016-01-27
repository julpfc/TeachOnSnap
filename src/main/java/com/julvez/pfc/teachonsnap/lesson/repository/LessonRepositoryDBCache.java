package com.julvez.pfc.teachonsnap.lesson.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;

/**
 * Repository implementation to access/modify data from a Database through a cache.
 * <p>
 * A repository database implementation ({@link LessonRepositoryDB}) is used to provide the database layer under the cache.
 * <p>
 * {@link CacheManager} is used to provide a cache system
 */
public class LessonRepositoryDBCache implements LessonRepository {

	/** Database repository providing data access and modification capabilities */
	private LessonRepositoryDB repoDB;
	
	/** Cache manager providing access/modification capabilities to the cache system */
	private CacheManager cache;
	
	/** String manager providing string manipulation utilities */
	private StringManager stringManager;
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param repoDB Database repository providing data access and modification capabilities
	 * @param cache Cache manager providing access/modification capabilities to the cache system
	 * @param stringManager String manager providing string manipulation utilities
	 */
	public LessonRepositoryDBCache(LessonRepositoryDB repoDB, CacheManager cache, StringManager stringManager) {
		if(repoDB == null || stringManager == null || cache == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.repoDB = repoDB;
		this.cache = cache;
		this.stringManager = stringManager;
	}
	
	@Override
	public Lesson getLesson(int idLesson) {
		return (Lesson)cache.executeImplCached(repoDB, idLesson);
	}

	@Override
	public int getLessonIDFromURI(String lessonURI) {
		return (int)cache.executeImplCached(repoDB, lessonURI);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getLastLessonIDs(int firstResult) {
		return (List<Integer>)cache.executeImplCached(repoDB, firstResult);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getLessonIDsFromAuthor(String author,int firstResult) {
		return (List<Integer>)cache.executeImplCached(repoDB, author,firstResult);
	}

	@Override
	public int createLesson(Lesson newLesson) {		
		int id =(int)cache.updateImplCached(repoDB, 
				new String[]{stringManager.getKey(newLesson.getAuthor().getURIName())}, 
				new String[]{"getLessonIDsFromAuthor"}, newLesson);
		
		if(id>0){
			//clear lesson related caches
			cache.clearCache("getLastLessonIDs");
			cache.clearCache("getAuthorCloudTags");
			cache.clearCache("getDraftLessonIDsFromUser");
		}
		
		return id;
	}

	@Override
	public void saveLessonText(int idLesson, String newText) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getLesson"}, idLesson, newText);		
	}

	@Override
	public void saveLessonLanguage(int idLesson, short idLanguage) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getLesson"}, idLesson, idLanguage);		
	}

	@Override
	public boolean saveLessonTitle(Lesson lesson, String title, String URIName) {
		return (boolean)cache.updateImplCached(repoDB, new String[]{stringManager.getKey(lesson.getId()), stringManager.getKey(lesson.getURIname())}, 
				new String[]{"getLesson","getLessonIDFromURI"}, lesson, title, URIName);
	}

	@Override
	public void removeLessonText(int idLesson) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getLesson"}, idLesson);		
	}

	@Override
	public void publish(int idLesson) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getLesson"}, idLesson);
		
		//clear lesson related caches
		cache.clearCache("getLastLessonIDs");
		cache.clearCache("getAuthorCloudTags");
		cache.clearCache("getLessonIDsFromAuthor");
		cache.clearCache("getLessonIDsFromTag");
		cache.clearCache("getCloudTags");
		cache.clearCache("getDraftLessonIDsFromUser");		
		cache.clearCache("getLessonIDFromURI");		
	}

	@Override
	public void unpublish(int idLesson) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getLesson"}, idLesson);
		
		//clear lesson related caches
		cache.clearCache("getLastLessonIDs");
		cache.clearCache("getAuthorCloudTags");
		cache.clearCache("getLessonIDsFromAuthor");
		cache.clearCache("getLessonIDsFromTag");
		cache.clearCache("getCloudTags");
		cache.clearCache("getDraftLessonIDsFromUser");		
		cache.clearCache("getLessonIDFromURI");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> getDraftLessonIDsFromUser(int idUser, int firstResult) {
		return (List<Integer>)cache.executeImplCached(repoDB, idUser, firstResult);
	}

}
