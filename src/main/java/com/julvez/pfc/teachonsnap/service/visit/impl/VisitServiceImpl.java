package com.julvez.pfc.teachonsnap.service.visit.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.model.user.test.UserLessonTest;
import com.julvez.pfc.teachonsnap.model.visit.UserTestRank;
import com.julvez.pfc.teachonsnap.model.visit.Visit;
import com.julvez.pfc.teachonsnap.repository.visit.VisitRepository;
import com.julvez.pfc.teachonsnap.repository.visit.VisitRepositoryFactory;
import com.julvez.pfc.teachonsnap.service.user.UserService;
import com.julvez.pfc.teachonsnap.service.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.service.visit.VisitService;

public class VisitServiceImpl implements VisitService {

	private static final String IP_NULL = "0.0.0.0";
	
	private VisitRepository visitRepository = VisitRepositoryFactory.getRepository();
	
	private UserService userService = UserServiceFactory.getService();

	@Override
	public Visit createVisit(String ip) {
		Visit visit = null;
		
		if(ip==null){
			ip = IP_NULL;
		}
		
		int idVisit = visitRepository.createVisit(ip);
		
		if(idVisit>0){
			visit = new Visit(idVisit);
		}
		
		return visit;
	}

	@Override
	public Visit saveUser(Visit visit, User user) {
		if(visit!=null && visit.getUser()==null && user!=null){
			if(visitRepository.saveUser(visit.getId(), user.getId())){
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
				if(visitRepository.saveLesson(visit.getId(), lesson.getId())){										
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
			
			saved = visitRepository.saveUserTest(visit, userTest, betterRank);
			
		}
		return saved;
	}

	@Override
	public UserTestRank getUserTestRank(int idLessonTest, int idUser) {
		UserTestRank testRank = null;
		
		if(idLessonTest>0 && idUser>0){
			testRank = visitRepository.getUserTestRank(idLessonTest, idUser);
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
		
		List<Short> ids = visitRepository.getUserIDsTestRank(idLessonTest);
		
		for(int id:ids){
			testRanks.add(getUserTestRank(idLessonTest, id));
		}
		
		return testRanks;
	}

}
