package com.julvez.pfc.teachonsnap.stats.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.test.model.UserLessonTest;
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.stats.repository.StatsRepository;
import com.julvez.pfc.teachonsnap.stats.repository.StatsRepositoryFactory;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;

public class StatsServiceImpl implements StatsService {

	private static final String IP_NULL = "0.0.0.0";
	
	private StatsRepository statsRepository = StatsRepositoryFactory.getRepository();
	
	private UserService userService = UserServiceFactory.getService();

	@Override
	public Visit createVisit(String ip) {
		Visit visit = null;
		
		if(ip==null){
			ip = IP_NULL;
		}
		
		int idVisit = statsRepository.createVisit(ip);
		
		if(idVisit>0){
			visit = new Visit(idVisit);
		}
		
		return visit;
	}

	@Override
	public Visit saveUser(Visit visit, User user) {
		if(visit!=null && visit.getUser()==null && user!=null){
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
			UserTestRank testRank = getUserTestRank(userTest.getIdLessonTest(), visit.getUser().getId());
			
			boolean betterRank = !(testRank != null && testRank.getPoints()>=userTest.getPoints());
			
			saved = statsRepository.saveUserTest(visit, userTest, betterRank);
			
		}
		return saved;
	}

	@Override
	public UserTestRank getUserTestRank(int idLessonTest, int idUser) {
		UserTestRank testRank = null;
		
		if(idLessonTest>0 && idUser>0){
			testRank = statsRepository.getUserTestRank(idLessonTest, idUser);
			if(testRank != null){
				User user = userService.getUser(idUser);
				testRank.setUser(user);
			}
		}
		return testRank;
	}

	@Override
	public List<UserTestRank> getTestRanks(int idLessonTest) {
		List<UserTestRank> testRanks = new ArrayList<UserTestRank>();
		
		List<Short> ids = statsRepository.getUserIDsTestRank(idLessonTest);
		
		for(int id:ids){
			testRanks.add(getUserTestRank(idLessonTest, id));
		}
		
		return testRanks;
	}

	@Override
	public Visit saveTag(Visit visit, Tag tag) {
		if(visit!=null && tag!=null){
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
			count = statsRepository.getTagViewsCount(tag.getId());
		}
		
		return count;
	}

	@Override
	public int getLessonViewsCount(Lesson lesson) {
		int count = -1;
		
		if(lesson != null){
			count = statsRepository.getLessonViewsCount(lesson.getId());
		}
		
		return count;
	}

}
