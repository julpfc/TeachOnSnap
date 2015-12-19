package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.test.LessonTestService;
import com.julvez.pfc.teachonsnap.lesson.test.LessonTestServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.test.model.LessonTest;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.StatsServiceFactory;
import com.julvez.pfc.teachonsnap.stats.model.StatsLessonTest;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.model.User;

public class StatsLessonTestController extends CommonController {

	private static final long serialVersionUID = 3593648651182715069L;
	
	private LessonService lessonService = LessonServiceFactory.getService();
	private LessonTestService lessonTestService = LessonTestServiceFactory.getService();
	private PageService pageService = PageServiceFactory.getService();
	private StatsService statsService = StatsServiceFactory.getService();
	
	private StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {

		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params!=null && params.length == 1 && stringManager.isNumeric(params[0])){
			
			int idLessonTest = Integer.parseInt(params[0]);
			
			LessonTest test = lessonTestService.getLessonTest(idLessonTest);
			
			if(test!=null){
				Lesson lesson = lessonService.getLesson(test.getIdLesson());
					
				if(userService.isAllowedForLesson(user, lesson)){

					UserTestRank testRank = statsService.getUserTestRank(test.getId(), visit.getUser().getId());			
					List<UserTestRank> testRanks = statsService.getTestRanks(test.getId());
					
					requestManager.setAttribute(request, Attribute.USERTESTRANK, testRank);
					requestManager.setAttribute(request, Attribute.LIST_USERTESTRANKS, testRanks);
					requestManager.setAttribute(request, Attribute.LESSON, lesson);
					requestManager.setAttribute(request, Attribute.LESSONTEST_QUESTIONS, test);
					
					
					StatsLessonTest statsTest = statsService.getStatsLessonTest(test);
					requestManager.setAttribute(request, Attribute.STATSTEST, statsTest);
					
					ControllerURI uri = ControllerURI.getURIFromPath(request.getServletPath());
					
					String backPage = null;
					List<Page> pageStack = null;
					
					if(uri != null){
						switch (uri) {
						case STATS_AUTHOR_LESSON_TEST:
							backPage = ControllerURI.STATS_AUTHOR_LESSON_MONTH.toString() + lesson.getId();
							pageStack = pageService.getStatsAuthorLessonTestPageStack(lesson, test);
							break;
						case STATS_LESSON_TEST:
							backPage = ControllerURI.STATS_LESSON_MONTH.toString() + lesson.getId();
							pageStack = pageService.getStatsLessonTestPageStack(lesson, test);
							break;
						
						case STATS_TEST:
							backPage = lesson.getEditURL();
							pageStack = pageService.getStatsTestPageStack(lesson, test);
							break;
						
						default:
							break;						
						}
					}
					requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
					requestManager.setAttribute(request, Attribute.STRING_BACKPAGE, backPage);
							
				    request.getRequestDispatcher("/WEB-INF/views/test.jsp").forward(request, response);
				}
				else{
					//No tienes permisos
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
				}						
			}
			else{
				//No se encontró el test
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
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
