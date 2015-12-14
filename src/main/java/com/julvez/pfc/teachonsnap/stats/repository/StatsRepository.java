package com.julvez.pfc.teachonsnap.stats.repository;

import java.util.List;
import java.util.Map;

import com.julvez.pfc.teachonsnap.lesson.test.model.UserLessonTest;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;

public interface StatsRepository {

	public int createVisit(String ip);

	public boolean saveUser(int idVisit, int idUser);

	public boolean saveLesson(int idVisit, int idLesson);

	public int getLessonViewsCount(int idLesson);

	public boolean saveUserTest(Visit visit, UserLessonTest userTest, boolean betterRank);

	public UserTestRank getUserTestRank(int idLessonTest, int idUser);

	public List<Short> getUserIDsTestRank(int idLessonTest);

	public boolean saveTag(int idVisit, int idTag);
	
	public int getTagViewsCount(int idTag);	

	public int getStatsLessonTestNumTests(int idLessonTest);

	public Map<String, String> getStatsLessonTestQuestionKOs(int idLessonTest);

}
