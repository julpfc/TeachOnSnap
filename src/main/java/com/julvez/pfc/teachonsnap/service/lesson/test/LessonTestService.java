package com.julvez.pfc.teachonsnap.service.lesson.test;

import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.test.Answer;
import com.julvez.pfc.teachonsnap.model.lesson.test.LessonTest;
import com.julvez.pfc.teachonsnap.model.lesson.test.Question;
import com.julvez.pfc.teachonsnap.model.user.User;

public interface LessonTestService {

	public LessonTest getLessonTest(Lesson lesson);

	public LessonTest getLessonTest(int idLessonTest);

	public void publish(int idLessonTest, User user);

	public void unpublish(int idLessonTest, User user);

	public Question getQuestion(int idQuestion);

	public void saveQuestion(Question question);

	public void saveAnswer(Answer answer);

	public Question createQuestion(Question question);	
	
}
