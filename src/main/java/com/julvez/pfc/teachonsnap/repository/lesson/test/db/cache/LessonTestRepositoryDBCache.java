package com.julvez.pfc.teachonsnap.repository.lesson.test.db.cache;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.model.lesson.test.Answer;
import com.julvez.pfc.teachonsnap.model.lesson.test.LessonTest;
import com.julvez.pfc.teachonsnap.model.lesson.test.Question;
import com.julvez.pfc.teachonsnap.repository.lesson.test.LessonTestRepository;
import com.julvez.pfc.teachonsnap.repository.lesson.test.db.LessonTestRepositoryDB;

public class LessonTestRepositoryDBCache implements LessonTestRepository {

	private LessonTestRepositoryDB repoDB = new LessonTestRepositoryDB();
	private CacheManager cache = CacheManagerFactory.getCacheManager();

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

}
