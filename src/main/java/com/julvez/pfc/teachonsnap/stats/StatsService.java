package com.julvez.pfc.teachonsnap.stats;

import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.UserLessonTest;
import com.julvez.pfc.teachonsnap.stats.model.StatsData;
import com.julvez.pfc.teachonsnap.stats.model.StatsLessonTest;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.user.model.User;

/** Provides the functionality to work with application's stats. */
public interface StatsService {

	/**
	 * Creates a new visit from the IP address.
	 * @param ip a user connects from
	 * @return Newly created visit, null if error
	 */
	public Visit createVisit(String ip);

	/**
	 * Saves logged user in a visit
	 * @param visit within the user logged in
	 * @param user logged in
	 * @return Modified visit, null othewise
	 */
	public Visit saveUser(Visit visit, User user);

	/**
	 * Saves visited lesson
	 * @param visit within the lesson was visited
	 * @param lesson visited
	 * @return Modified Visit, null otherwise
	 */
	public Visit saveLesson(Visit visit, Lesson lesson);

	/**
	 * Saves performed lesson's test
	 * @param visit within the lesson test was visited
	 * @param userTest performed
	 * @return true if success
	 */
	public boolean saveUserTest(Visit visit, UserLessonTest userTest);
	
	/**
	 * Returns best user result in a test
	 * @param idLessonTest Test
	 * @param idUser User
	 * @return best user's result in a test if exists, null otherwise
	 */
	public UserTestRank getUserTestRank(int idLessonTest, int idUser);

	/**
	 * Get best results ranking for a test
	 * @param idLessonTest test
	 * @return List of best results
	 */
	public List<UserTestRank> getTestRanks(int idLessonTest);

	/**
	 * Saves visited tag
	 * @param visit within the tag was visited
	 * @param tag visited
	 * @return Modified Visit, null otherwise
	 */
	public Visit saveTag(Visit visit, Tag tag);
	
	/**
	 * Returns number of views for this tag
	 * @param tag to get views count
	 * @return Number of views
	 */
	public int getTagViewsCount(Tag tag);
	
	/**
	 * Returns number of views for this lesson
	 * @param lesson to get views count
	 * @return Number of views
	 */
	public int getLessonViewsCount(Lesson lesson);

	/**
	 * Returns the stats of the specified test
	 * @param test to get the stats from
	 * @return the stats of the test
	 */
	public StatsLessonTest getStatsLessonTest(LessonTest test);

	/**
	 * Returns last month visits to this lesson
	 * @param lesson visited
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getLessonVisitsLastMonth(Lesson lesson);

	/**
	 * Returns last year visits to this lesson
	 * @param lesson visited
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getLessonVisitsLastYear(Lesson lesson);

	/**
	 * Returns last month visits to this author's lessons 
	 * (grouped by author)
	 * @param profile visited
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getAuthorVisitsLastMonth(User profile);

	/**
	 * Returns last year visits to this author's lessons 
	 * (grouped by author)
	 * @param profile visited
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getAuthorVisitsLastYear(User profile);

	/**
	 * Returns last month visits to this author's lessons
	 * @param profile visited
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getAuthorLessonsVisitsLastMonth(User profile);

	/**
	 * Returns last year visits to this author's lessons
	 * @param profile visited
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getAuthorLessonsVisitsLastYear(User profile);

	/**
	 * Converts stats data into a CSV (comma Separated Values) string
	 * @param stats to be converted
	 * @return CSV string
	 */
	public String getCSVFromStats(List<StatsData> stats);

	/**
	 * Returns last month visits to this author's lessons 
	 * (grouped by media)
	 * @param profile visited
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getAuthorLessonMediaVisitsLastMonth(User profile);

	/**
	 * Returns last year visits to this author's lessons 
	 * (grouped by media)
	 * @param profile visited
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getAuthorLessonMediaVisitsLastYear(User profile);

	/**
	 * Returns last month visits
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getVisitsLastMonth();

	/**
	 * Returns last year visits
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getVisitsLastYear();

	/**
	 * Returns last month visits by lesson
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getLessonsVisitsLastMonth();

	/**
	 * Returns last year visits by lesson
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getLessonsVisitsLastYear();

	/**
	 * Returns last month visits by author
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getAuthorsVisitsLastMonth();

	/**
	 * Returns last year visits by author
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getAuthorsVisitsLastYear();
}
