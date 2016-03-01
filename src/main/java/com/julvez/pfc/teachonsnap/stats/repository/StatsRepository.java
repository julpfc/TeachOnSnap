package com.julvez.pfc.teachonsnap.stats.repository;

import java.util.List;
import java.util.Map;

import com.julvez.pfc.teachonsnap.lessontest.model.UserLessonTest;
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.model.StatsData;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;

/**
 * Repository to access/modify data related to users.
 * <p>
 * To be used only by the {@link StatsService}'s implementation
 */
public interface StatsRepository {

	/**
	 * Returns the corresponding Visit object from the specified id.
	 * @param idVisit Visit's id
	 * @return Visit object from the id if it's a valid one, null otherwise.
	 */
	public Visit getVisit(int idVisit);
	
	/**
	 * Creates a new visit from the IP address.
	 * @param ip a user connects from
	 * @return Newly created visit id, -1 if error
	 */
	public int createVisit(String ip);

	/**
	 * Saves logged user in a visit
	 * @param idVisit within the user logged in
	 * @param idUser logged in
	 * @return true if success
	 */
	public boolean saveUser(int idVisit, int idUser);

	/**
	 * Saves visited lesson
	 * @param idVisit within the lesson was visited
	 * @param idLesson visited
	 * @return true if success
	 */
	public boolean saveLesson(int idVisit, int idLesson);

	/**
	 * Returns number of views for this lesson
	 * @param idLesson to get views count
	 * @return Number of views
	 */
	public int getLessonViewsCount(int idLesson);

	/**
	 * Saves performed lesson's test
	 * @param visit within the lesson test was visited
	 * @param userTest performed
	 * @param betterRank if this is a better rank than the previuos
	 * @return true if success
	 */
	public boolean saveUserTest(Visit visit, UserLessonTest userTest, boolean betterRank);

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
	 * @return List of best results UserTestRank ids
	 */
	public List<Short> getUserIDsTestRank(int idLessonTest);

	/**
	 * Saves visited tag
	 * @param idVisit within the tag was visited
	 * @param idTag visited
	 * @return true if success
	 */
	public boolean saveTag(int idVisit, int idTag);
	
	/**
	 * Returns number of views for this tag
	 * @param idTag to get views count
	 * @return Number of views
	 */
	public int getTagViewsCount(int idTag);	

	/**
	 * Returns number of performed tests of the specified test
	 * @param idLessonTest to get the stats from
	 * @return number of performed tests
	 */
	public int getStatsLessonTestNumTests(int idLessonTest);

	/**
	 * Returns map of question missed distribution of the specified test
	 * @param idLessonTest to get the stats from
	 * @return map of question missed distribution
	 */
	public Map<String, String> getStatsLessonTestQuestionKOs(int idLessonTest);

	/**
	 * Returns last month visits to this lesson
	 * @param idLesson visited
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getLessonVisitsLastMonth(int idLesson);

	/**
	 * Returns last year visits to this lesson
	 * @param idLesson visited
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getLessonVisitsLastYear(int idLesson);

	/**
	 * Returns last month visits to this author's lessons 
	 * (grouped by author)
	 * @param idUser visited
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getAuthorVisitsLastMonth(int idUser);

	/**
	 * Returns last year visits to this author's lessons 
	 * (grouped by author)
	 * @param idUser visited
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getAuthorVisitsLastYear(int idUser);

	/**
	 * Returns last month visits to this author's lessons
	 * @param idUser visited
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getAuthorLessonsVisitsLastMonth(int idUser);

	/**
	 * Returns last year visits to this author's lessons
	 * @param idUser visited
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getAuthorLessonsVisitsLastYear(int idUser);

	/**
	 * Returns last month visits to this author's lessons 
	 * (grouped by media)
	 * @param idUser visited
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getAuthorLessonMediaVisitsLastMonth(int idUser);

	/**
	 * Returns last year visits to this author's lessons 
	 * (grouped by media)
	 * @param idUser visited
	 * @return List of stats data containing the visits
	 */
	public List<StatsData> getAuthorLessonMediaVisitsLastYear(int idUser);

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
