package com.julvez.pfc.teachonsnap.repository.lesson.db.cache;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.model.lesson.Answer;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.LessonTest;
import com.julvez.pfc.teachonsnap.model.lesson.Link;
import com.julvez.pfc.teachonsnap.model.lesson.Question;
import com.julvez.pfc.teachonsnap.model.lesson.Tag;
import com.julvez.pfc.teachonsnap.model.lesson.VideoFile;
import com.julvez.pfc.teachonsnap.repository.lesson.LessonRepository;
import com.julvez.pfc.teachonsnap.repository.lesson.db.LessonRepositoryDB;


public class LessonRepositoryDBCache implements LessonRepository {

	private LessonRepositoryDB repoDB = new LessonRepositoryDB();
	private CacheManager cache = CacheManagerFactory.getCacheManager();
	
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

	@SuppressWarnings("unchecked")
	@Override
	public List<VideoFile> getLessonVideos(int idLessonVideo) {
		return (List<VideoFile>)cache.executeImplCached(repoDB, idLessonVideo);
	}

	@Override
	public int createLesson(Lesson newLesson) {		
		return (int)cache.updateImplCached(repoDB, null, null, newLesson);
	}

	@Override
	public void saveLessonText(int idLesson, String newText) {
		cache.updateImplCached(repoDB, idLesson, "getLesson", idLesson, newText);		
	}

	

}
