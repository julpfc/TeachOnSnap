package com.julvez.pfc.teachonsnap.visit;

import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.test.model.UserLessonTest;
import com.julvez.pfc.teachonsnap.lesson.test.model.UserTestRank;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.visit.model.Visit;

public interface VisitService {

	public Visit createVisit(String ip);

	public Visit saveUser(Visit visit, User user);

	public Visit saveLesson(Visit visit, Lesson lesson);

	public boolean saveUserTest(Visit visit, UserLessonTest userTest);
	
	public UserTestRank getUserTestRank(int idLessonTest, int idUser);

	public List<UserTestRank> getTestRanks(int idLessonTest);

}
