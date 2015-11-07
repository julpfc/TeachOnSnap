package com.julvez.pfc.teachonsnap.service.visit.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.user.test.UserLessonTest;
import com.julvez.pfc.teachonsnap.model.visit.UserTestRank;
import com.julvez.pfc.teachonsnap.model.visit.Visit;

public interface VisitRepository {

	public int createVisit(String ip);

	public boolean saveUser(int idVisit, int idUser);

	public boolean saveLesson(int idVisit, int idLesson);

	public int getLessonViewsCount(int idLesson);

	public boolean saveUserTest(Visit visit, UserLessonTest userTest, boolean betterRank);

	public UserTestRank getUserTestRank(int idLessonTest, int idUser);

	public List<Short> getUserIDsTestRank(int idLessonTest);

}
