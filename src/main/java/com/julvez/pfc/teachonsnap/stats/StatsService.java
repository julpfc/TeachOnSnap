package com.julvez.pfc.teachonsnap.stats;

import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.test.model.LessonTest;
import com.julvez.pfc.teachonsnap.lesson.test.model.UserLessonTest;
import com.julvez.pfc.teachonsnap.stats.model.StatsData;
import com.julvez.pfc.teachonsnap.stats.model.StatsLessonTest;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.user.model.User;

public interface StatsService {

	public Visit createVisit(String ip);

	public Visit saveUser(Visit visit, User user);

	public Visit saveLesson(Visit visit, Lesson lesson);

	public boolean saveUserTest(Visit visit, UserLessonTest userTest);
	
	public UserTestRank getUserTestRank(int idLessonTest, int idUser);

	public List<UserTestRank> getTestRanks(int idLessonTest);

	public Visit saveTag(Visit visit, Tag tag);
	
	public int getTagViewsCount(Tag tag);
	
	public int getLessonViewsCount(Lesson lesson);

	public StatsLessonTest getStatsLessonTest(LessonTest test);

	public List<StatsData> getLessonVisitsLastMonth(Lesson lesson);

	public List<StatsData> getLessonVisitsLastYear(Lesson lesson);

	public List<StatsData> getAuthorVisitsLastMonth(User profile);

	public List<StatsData> getAuthorVisitsLastYear(User profile);

	public List<StatsData> getAuthorLessonsVisitsLastMonth(User profile);

	public List<StatsData> getAuthorLessonsVisitsLastYear(User profile);

	public String getCSVFromStats(List<StatsData> stats);

	public List<StatsData> getAuthorLessonMediaVisitsLastMonth(User profile);

	public List<StatsData> getAuthorLessonMediaVisitsLastYear(User profile);

	public List<StatsData> getVisitsLastMonth();

	public List<StatsData> getVisitsLastYear();

	public List<StatsData> getLessonsVisitsLastMonth();

	public List<StatsData> getLessonsVisitsLastYear();

	public List<StatsData> getAuthorsVisitsLastMonth();

	public List<StatsData> getAuthorsVisitsLastYear();

}
