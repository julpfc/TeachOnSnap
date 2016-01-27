package com.julvez.pfc.teachonsnap.lessontest.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.lessontest.model.Answer;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.Question;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;

/**
 * Repository implementation to access/modify data from a Database through a cache.
 * <p>
 * A repository database implementation ({@link LessonTestRepositoryDB}) is used to provide the database layer under the cache.
 * <p>
 * {@link CacheManager} is used to provide a cache system
 */
public class LessonTestRepositoryDBCache implements LessonTestRepository {

	/** Database repository providing data access and modification capabilities */
	private LessonTestRepositoryDB repoDB;
	
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
	public LessonTestRepositoryDBCache(LessonTestRepositoryDB repoDB, CacheManager cache, StringManager stringManager) {
		if(repoDB == null || stringManager == null || cache == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.repoDB = repoDB;
		this.cache = cache;
		this.stringManager = stringManager;
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
				stringManager.getKey(idLessonTest),stringManager.getKey(idLessonTest)}, 
				new String[]{"getQuestion", "getLessonTest","getLessonTestQuestionIDs"},
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

	@Override
	public int createQuestion(Question question) {
		return (int)cache.updateImplCached(repoDB, new String[]{stringManager.getKey(question.getIdLessonTest()),
				stringManager.getKey(question.getIdLessonTest())}, new String[]{"getLessonTestQuestionIDs","getLessonTest"},
				question);
	}

	@Override
	public void removeQuestion(LessonTest test, Question question) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(question.getIdLessonTest()),
				stringManager.getKey(question.getIdLessonTest()), stringManager.getKey(question.getId())}, 
				new String[]{"getLessonTestQuestionIDs","getLessonTest","getQuestion"},	test, question);		
	}

	@Override
	public void removeLessonTest(LessonTest test) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(test.getId()), 
				stringManager.getKey(test.getIdLesson()),stringManager.getKey(test.getIdLesson())},
				new String[]{"getLessonTest", "getLesson","getLessonTestID"}, test);
	}

	@Override
	public int createLessonTest(int idLesson, boolean multipleChoice, int numAnswers) {
		return (int)cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)},
				new String[]{"getLesson"}, idLesson, multipleChoice, numAnswers);
	}	

}
