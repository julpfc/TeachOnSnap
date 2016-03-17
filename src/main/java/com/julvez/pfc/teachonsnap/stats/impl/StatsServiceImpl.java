package com.julvez.pfc.teachonsnap.stats.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.UserLessonTest;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.model.StatsData;
import com.julvez.pfc.teachonsnap.stats.model.StatsLessonTest;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.stats.repository.StatsRepository;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Implementation of the StatsService interface, uses an internal {@link StatsRepository} 
 * to access/modify the stats data.
 */
public class StatsServiceImpl implements StatsService {

	/** Blank IP to be used when the IP is missing */
	private static final String IP_NULL = "0.0.0.0";
	
	/** Repository than provides data access/modification */
	private StatsRepository statsRepository;
	
	/** Provides the functionality to work with application's users */
	private UserService userService;
	
	/** String manager providing string manipulation utilities */
	private StringManager stringManager;
	
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param statsRepository Repository than provides data access/modification
	 * @param userService Provides the functionality to work with application's users
	 * @param stringManager String manager providing string manipulation utilities
	 */
	public StatsServiceImpl(StatsRepository statsRepository,
			UserService userService, StringManager stringManager) {
		if(statsRepository == null || userService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		
		this.statsRepository = statsRepository;
		this.userService = userService;
		this.stringManager = stringManager;
	}

	@Override
	public Visit createVisit(String ip) {
		Visit visit = null;
		
		//Use blank IP if it's missing
		if(stringManager.isEmpty(ip)){
			ip = IP_NULL;
		}
		
		//Create a visit in repository
		int idVisit = statsRepository.createVisit(ip);
		
		//Create a new Visit with the id
		if(idVisit>0){
			visit = new Visit(idVisit);
		}
		
		return visit;
	}

	@Override
	public Visit saveUser(Visit visit, User user) {
		if(visit!=null && visit.getUser()==null && user!=null){
			//Save logged-in user in visit
			if(statsRepository.saveUser(visit.getId(), user.getId())){
				visit.setUser(user);
			}else{
				visit = null;
			}			
		}
		return visit;
	}

	@Override
	public Visit saveLesson(Visit visit, Lesson lesson) {
		if(visit!=null && lesson!=null){
			//Save viewed lesson in visit (only if it's not already added)
			if(!visit.isViewedLesson(lesson.getId())){
				if(statsRepository.saveLesson(visit.getId(), lesson.getId())){										
					visit.addViewedLesson(lesson.getId());
				}
				else return null;				
			}			
		}
		return visit;
	}

	@Override
	public boolean saveUserTest(Visit visit, UserLessonTest userTest) {
		boolean saved = false;
		if(visit!=null && userTest!=null){
			//Get best result
			UserTestRank testRank = getUserTestRank(userTest.getIdLessonTest(), visit.getUser().getId());
			
			//Check if it's a better result
			boolean betterRank = !(testRank != null && testRank.getPoints()>=userTest.getPoints());
			
			//Save result in the repository
			saved = statsRepository.saveUserTest(visit, userTest, betterRank);
			
		}
		return saved;
	}

	@Override
	public UserTestRank getUserTestRank(int idLessonTest, int idUser) {
		UserTestRank testRank = null;
		
		if(idLessonTest>0 && idUser>0){
			//Get best result in test for user
			testRank = statsRepository.getUserTestRank(idLessonTest, idUser);
			if(testRank != null){
				//Get user from id
				User user = userService.getUser(idUser);
				testRank.setUser(user);
			}
		}
		return testRank;
	}

	@Override
	public List<UserTestRank> getTestRanks(int idLessonTest) {
		List<UserTestRank> testRanks = Collections.emptyList();
		
		//get user ids ranking fro test
		List<Short> ids = statsRepository.getUserIDsTestRank(idLessonTest);
		
		if(ids != null){
			testRanks = new ArrayList<UserTestRank>();
			//get user test ranks from ids
			for(int id:ids){
				testRanks.add(getUserTestRank(idLessonTest, id));
			}
		}
		
		return testRanks;
	}

	@Override
	public Visit saveTag(Visit visit, Tag tag) {
		if(visit!=null && tag!=null){
			//Save viewed tag (only if it's not already added
			if(!visit.isViewedTag(tag.getId())){
				if(statsRepository.saveTag(visit.getId(), tag.getId())){										
					visit.addViewedTag(tag.getId());
				}
				else return null;				
			}			
		}
		return visit;	}

	@Override
	public int getTagViewsCount(Tag tag) {
		int count = -1;
		
		if(tag != null){
			//get tag view count from repo
			count = statsRepository.getTagViewsCount(tag.getId());
		}
		
		return count;
	}

	@Override
	public int getLessonViewsCount(Lesson lesson) {
		int count = -1;
		
		if(lesson != null){
			//get lesson view count from repo
			count = statsRepository.getLessonViewsCount(lesson.getId());
		}
		
		return count;
	}

	@Override
	public StatsLessonTest getStatsLessonTest(LessonTest test) {
		StatsLessonTest statsTest = null;
		
		if(test != null){
			//get total number of attempts
			int numTests = statsRepository.getStatsLessonTestNumTests(test.getId());
			
			if(numTests > 0){
				//get question missed distribution
				Map<String, String> questionKOs = statsRepository.getStatsLessonTestQuestionKOs(test.getId());
			
				//Create an object to store stats data for this test
				statsTest = new StatsLessonTest(test.getId(), numTests, questionKOs);
			}
		}
		
		return statsTest;
	}

	@Override
	public List<StatsData> getLessonVisitsLastMonth(Lesson lesson) {
		List<StatsData> stats = null;
		
		if(lesson != null){
			//get stats from repo
			stats = statsRepository.getLessonVisitsLastMonth(lesson.getId());
		}
		
		return stats;
	}

	@Override
	public List<StatsData> getLessonVisitsLastYear(Lesson lesson) {
		List<StatsData> stats = null;
		
		if(lesson != null){
			//get stats from repo
			stats = statsRepository.getLessonVisitsLastYear(lesson.getId());
		}
		
		return stats;
	}

	@Override
	public List<StatsData> getAuthorVisitsLastMonth(User profile) {
		List<StatsData> stats = null;
		
		if(profile != null){
			//get stats from repo
			stats = statsRepository.getAuthorVisitsLastMonth(profile.getId());
		}
		
		return stats;

	}

	@Override
	public List<StatsData> getAuthorVisitsLastYear(User profile) {
		List<StatsData> stats = null;
		
		if(profile != null){
			//get stats from repo
			stats = statsRepository.getAuthorVisitsLastYear(profile.getId());
		}
		
		return stats;
	}

	@Override
	public List<StatsData> getAuthorLessonsVisitsLastMonth(User profile) {
		List<StatsData> stats = null;
		
		if(profile != null){
			//get stats from repo
			stats = statsRepository.getAuthorLessonsVisitsLastMonth(profile.getId());
		}
		
		return stats;
	}

	@Override
	public List<StatsData> getAuthorLessonsVisitsLastYear(User profile) {
		List<StatsData> stats = null;
		
		if(profile != null){
			//get stats from repo
			stats = statsRepository.getAuthorLessonsVisitsLastYear(profile.getId());
		}
		
		return stats;
	}

	@Override
	public String getCSVFromStats(List<StatsData> stats) {
		String csv = null;
		
		if(stats != null){
			int i = 0;
			csv = "";
			//Generate Comma separated values from data
			for(StatsData stat:stats){
				csv = csv + stat.getKey() + ";" + stat.getValue();
				if(i<stats.size()){
					csv = csv + "\n";
				}
				i++;
			}			
		}
		
		return csv;
	}

	@Override
	public List<StatsData> getAuthorLessonMediaVisitsLastMonth(User profile) {
		List<StatsData> stats = null;
		
		if(profile != null){
			//get stats from repo
			stats = statsRepository.getAuthorLessonMediaVisitsLastMonth(profile.getId());
		}
		
		return stats;
	}

	@Override
	public List<StatsData> getAuthorLessonMediaVisitsLastYear(User profile) {
		List<StatsData> stats = null;
		
		if(profile != null){
			//get stats from repo
			stats = statsRepository.getAuthorLessonMediaVisitsLastYear(profile.getId());
		}
		
		return stats;
	}

	@Override
	public List<StatsData> getVisitsLastMonth() {		
		return statsRepository.getVisitsLastMonth();
	}

	@Override
	public List<StatsData> getVisitsLastYear() {		
		return statsRepository.getVisitsLastYear();
	}

	@Override
	public List<StatsData> getLessonsVisitsLastMonth() {
		return statsRepository.getLessonsVisitsLastMonth();
	}

	@Override
	public List<StatsData> getLessonsVisitsLastYear() {
		return statsRepository.getLessonsVisitsLastYear();
	}

	@Override
	public List<StatsData> getAuthorsVisitsLastMonth() {
		return statsRepository.getAuthorsVisitsLastMonth();
	}

	@Override
	public List<StatsData> getAuthorsVisitsLastYear() {
		return statsRepository.getAuthorsVisitsLastYear();
	}

	@Override
	public Visit getVisit(int idVisit) {
		Visit visit = null;
		if(idVisit > 0){
			visit = statsRepository.getVisit(idVisit);
		}
		return visit;
	}
}
