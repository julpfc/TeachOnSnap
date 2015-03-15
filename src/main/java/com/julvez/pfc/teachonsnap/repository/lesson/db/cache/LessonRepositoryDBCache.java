package com.julvez.pfc.teachonsnap.repository.lesson.db.cache;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.repository.lesson.LessonRepository;
import com.julvez.pfc.teachonsnap.repository.lesson.db.LessonRepositoryDB;


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
	public List<Integer> getLinkedLessonIDs(int idLesson) {
		return (List<Integer>)cache.executeImplCached(repoDB, idLesson);
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
		}
		
		return id;
	}

	@Override
	public void saveLessonText(int idLesson, String newText) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getLesson"}, idLesson, newText);		
	}

}
