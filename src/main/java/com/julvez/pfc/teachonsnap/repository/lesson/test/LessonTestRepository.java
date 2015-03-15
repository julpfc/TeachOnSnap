package com.julvez.pfc.teachonsnap.repository.lesson.test;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.test.Answer;
import com.julvez.pfc.teachonsnap.model.lesson.test.LessonTest;
import com.julvez.pfc.teachonsnap.model.lesson.test.Question;

public interface LessonTestRepository {

	public LessonTest getLessonTest(int idLessonTest);

	public List<Integer> getLessonTestQuestionIDs(int idLessonTest);

	public Question getQuestion(int idQuestion);

	public List<Integer> getQuestionAnswerIDs(int idQuestion);

	public Answer getAnswer(int idAnswer);

	public int getLessonTestID(int idLesson);

	public void publish(int idLessonTest, int idLesson);

	public void unpublish(int idLessonTest, int idLesson);

}
