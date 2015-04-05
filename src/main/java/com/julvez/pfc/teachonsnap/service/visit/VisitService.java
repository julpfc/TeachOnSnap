package com.julvez.pfc.teachonsnap.service.visit;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.user.test.UserLessonTest;
import com.julvez.pfc.teachonsnap.model.visit.UserTestRank;
import com.julvez.pfc.teachonsnap.model.visit.Visit;

public interface VisitService {

	public Visit createVisit(String ip);

	public Visit saveUser(Visit visit);

	public Visit saveLesson(Visit visit, Lesson lesson);

	public boolean saveUserTest(Visit visit, UserLessonTest userTest);
	
	public UserTestRank getUserTestRank(int idLessonTest, int idUser);

	public List<UserTestRank> getTestRanks(int idLessonTest);

}
