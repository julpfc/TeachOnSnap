package com.julvez.pfc.teachonsnap.lesson.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;


public class LessonRepositoryDBCache implements LessonRepository {

	private LessonRepositoryDB repoDB = new LessonRepositoryDB();
	private CacheManager cache = CacheManagerFactory.getCacheManager();
	private StringManager stringManager = StringManagerFactory.getManager();
	
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
	public List<Integer> getDraftLessonIDsFromUser(short idUser, int firstResult) {
		return (List<Integer>)cache.executeImplCached(repoDB, idUser, firstResult);
	}

}
