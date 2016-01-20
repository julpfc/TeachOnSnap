package com.julvez.pfc.teachonsnap.lessontest;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.model.Answer;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.Question;

public interface LessonTestService {

	public LessonTest getLessonTest(Lesson lesson);

	public LessonTest getLessonTest(int idLessonTest);

	public void publish(int idLessonTest);

	public void unpublish(int idLessonTest);

	public Question getQuestion(int idQuestion);

	public void saveQuestion(Question question);

	public void saveAnswer(Answer answer);

	public LessonTest createQuestion(Question question);

	public boolean removeQuestion(Question question);

	public boolean removeLessonTest(LessonTest test);

	public boolean moveQuestion(Question question, int newPriority);

	public LessonTest createLessonTest(Lesson lesson, boolean multipleChoice, int numAnswers);	
	
}
