package com.julvez.pfc.teachonsnap.repository.lesson.test.db.cache;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.lesson.test.Answer;
import com.julvez.pfc.teachonsnap.model.lesson.test.LessonTest;
import com.julvez.pfc.teachonsnap.model.lesson.test.Question;
import com.julvez.pfc.teachonsnap.repository.lesson.test.LessonTestRepository;
import com.julvez.pfc.teachonsnap.repository.lesson.test.db.LessonTestRepositoryDB;

public class LessonTestRepositoryDBCache implements LessonTestRepository {

	private LessonTestRepositoryDB repoDB = new LessonTestRepositoryDB();
	private CacheManager cache = CacheManagerFactory.getCacheManager();
	private StringManager stringManager = StringManagerFactory.getManager();

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

	@Override
	public int getLessonTestID(int idLesson) {
		return (int)cache.executeImplCached(repoDB, idLesson);
	}

	@Override
	public void publish(int idLessonTest, int idLesson) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLessonTest), 
				stringManager.getKey(idLesson)}, new String[]{"getLessonTest", "getLesson"}, 
				idLessonTest, idLesson);
	}

	@Override
	public void unpublish(int idLessonTest, int idLesson) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLessonTest), 
				stringManager.getKey(idLesson)}, new String[]{"getLessonTest", "getLesson"},
				idLessonTest, idLesson);		
	}

	@Override
	public void saveQuestion(int idQuestion, String text, byte priority, int idLessonTest) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idQuestion), 
				stringManager.getKey(idLessonTest)}, new String[]{"getQuestion", "getLessonTest"},
				idQuestion, text, priority, idLessonTest);
		
	}

	@Override
	public void saveAnswer(int idAnswer, String text, boolean correct,
			String reason, int idQuestion, int idLessonTest) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idQuestion), 
				stringManager.getKey(idLessonTest), stringManager.getKey(idAnswer)}, 
				new String[]{"getQuestion", "getLessonTest", "getAnswer"}, 
				idAnswer, text, correct, reason, idQuestion, idLessonTest);
		
	}

}
