package com.julvez.pfc.teachonsnap.repository.lesson.db.cache;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.lesson.Answer;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.LessonTest;
import com.julvez.pfc.teachonsnap.model.lesson.Link;
import com.julvez.pfc.teachonsnap.model.lesson.Question;
import com.julvez.pfc.teachonsnap.model.lesson.Tag;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getLessonIDsFromTag(String tag,int firstResult) {
		return (List<Integer>)cache.executeImplCached(repoDB, tag,firstResult);
	}

	@Override
	public int getLessonIDFromURI(String lessonURI) {
		return (int)cache.executeImplCached(repoDB, lessonURI);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getLessonTagIDs(int idLesson) {
		return (List<Integer>)cache.executeImplCached(repoDB, idLesson);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getLinkedLessonIDs(int idLesson) {
		return (List<Integer>)cache.executeImplCached(repoDB, idLesson);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getMoreInfoLinkIDs(int idLesson) {
		return (List<Integer>)cache.executeImplCached(repoDB, idLesson);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getSourceLinkIDs(int idLesson) {
		return (List<Integer>)cache.executeImplCached(repoDB, idLesson);
	}

	@Override
	public Tag getTag(int idTag) {
		return (Tag)cache.executeImplCached(repoDB, idTag);
	}

	@Override
	public Link getLink(int idLink) {
		return (Link)cache.executeImplCached(repoDB, idLink);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getLastLessonIDs(int firstResult) {
		return (List<Integer>)cache.executeImplCached(repoDB, firstResult);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getCloudTags() {
		return (List<Object[]>)cache.executeImplCached(repoDB, new Object[0]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getLessonIDsFromAuthor(String author,int firstResult) {
		return (List<Integer>)cache.executeImplCached(repoDB, author,firstResult);
	}

	@Override
	public LessonTest getLessonTest(int idLessonTest) {
		return (LessonTest)cache.executeImplCached(repoDB, idLessonTest);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getLessonTestQuestionIDs(int idLessonTest) {
		return (List<Integer>)cache.executeImplCached(repoDB, idLessonTest);
	}

	@Override
	public Question getQuestion(int idQuestion) {
		return (Question)cache.executeImplCached(repoDB, idQuestion);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getQuestionAnswerIDs(int idQuestion) {
		return (List<Integer>)cache.executeImplCached(repoDB, idQuestion);
	}

	@Override
	public Answer getAnswer(int idAnswer) {
		return (Answer)cache.executeImplCached(repoDB, idAnswer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAuthorCloudTags() {
		return (List<Object[]>)cache.executeImplCached(repoDB, new Object[0]);
	}

	@Override
	public int createLesson(Lesson newLesson) {		
		int id =(int)cache.updateImplCached(repoDB, null, null, newLesson);
		
		if(id>0){
			cache.clearCache("getLastLessonIDs");
			cache.clearCache("getAuthorCloudTags");

			//TODO Idealmente sólo nos cargaríamos la caché de ese autor (URIname que no tenemos)
			//getLessonIDsFromAuthor(String author,int firstResult)			
			cache.clearCache("getLessonIDsFromAuthor");
		}
		
		return id;
	}

	@Override
	public void saveLessonText(int idLesson, String newText) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getLesson"}, idLesson, newText);		
	}

	@Override
	public void addLessonTags(int idLesson, ArrayList<Integer> tagIDs) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getLessonTagIDs"}, idLesson, tagIDs);
		
		cache.clearCache("getLessonIDsFromTag");
		cache.clearCache("getCloudTags");			
	}

	@Override
	public int getTagID(String tag) {
		return (int)cache.executeImplCached(repoDB, tag);		
	}

	@Override
	public int createTag(String tag) {
		return (int)cache.updateImplCached(repoDB, null, null, tag);
	}

	@Override
	public int getLinkID(String link) {
		return (int)cache.executeImplCached(repoDB, link);		
	}

	@Override
	public int createLink(String url, String desc) {
		return (int)cache.updateImplCached(repoDB, null, null, url, desc);
	}

	@Override
	public void addLessonSources(int idLesson, ArrayList<Integer> sourceLinkIDs) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getSourceLinkIDs"}, idLesson, sourceLinkIDs);		
	}

	@Override
	public void addLessonMoreInfos(int idLesson, ArrayList<Integer> moreInfoLinkIDs) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getMoreInfoLinkIDs"}, idLesson, moreInfoLinkIDs);		
	}



	

}
