package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.error.model.ErrorBean;
import com.julvez.pfc.teachonsnap.error.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.error.model.ErrorType;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.test.LessonTestService;
import com.julvez.pfc.teachonsnap.lesson.test.LessonTestServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.test.model.LessonTest;
import com.julvez.pfc.teachonsnap.lesson.test.model.UserLessonTest;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.user.model.User;

public class LessonTestController extends CommonController {

	private static final long serialVersionUID = 3593648651182715069L;
	
	private LessonService lessonService = LessonServiceFactory.getService();
	private LessonTestService lessonTestService = LessonTestServiceFactory.getService();
	
	private StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {

		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params!=null && params.length>0 && stringManager.isNumeric(params[0])){
			
			int idLessonTest = Integer.parseInt(params[0]);
			
			LessonTest test = lessonTestService.getLessonTest(idLessonTest);
			
			Lesson lesson = lessonService.getLesson(test.getIdLesson());
			
			if(request.getMethod().equals("POST")){
				UserLessonTest userTest = new UserLessonTest(test, request.getParameterMap());
				
				boolean saved = statsService.saveUserTest(visit, userTest);
				
				if(saved){
					setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.USERTEST_SAVED));					
				}
				else{
					setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR));
				}
				
				requestManager.setAttribute(request, Attribute.USERLESSONTEST_ANSWERS, userTest);
			}			
			
			UserTestRank testRank = statsService.getUserTestRank(test.getId(), visit.getUser().getId());			
			List<UserTestRank> testRanks = statsService.getTestRanks(test.getId());
			
			requestManager.setAttribute(request, Attribute.USERTESTRANK, testRank);
			requestManager.setAttribute(request, Attribute.LIST_USERTESTRANKS, testRanks);
			requestManager.setAttribute(request, Attribute.LESSON, lesson);
			requestManager.setAttribute(request, Attribute.LESSONTEST_QUESTIONS, test);
					
		    request.getRequestDispatcher("/WEB-INF/views/test.jsp").forward(request, response);
		}
		else{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}

	}

	@Override
	protected boolean isPrivateZone() {		
		return true;
	}

}
