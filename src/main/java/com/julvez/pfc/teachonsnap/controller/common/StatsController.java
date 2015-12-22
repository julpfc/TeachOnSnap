package com.julvez.pfc.teachonsnap.controller.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
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
import com.julvez.pfc.teachonsnap.stats.model.StatsData;
import com.julvez.pfc.teachonsnap.stats.model.StatsType;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.model.User;

public class StatsController extends CommonController {

	private static final long serialVersionUID = -6715205860379822910L;
	
	private LessonService lessonService = LessonServiceFactory.getService();
	private StatsService statsService = StatsServiceFactory.getService();
	private PageService pageService = PageServiceFactory.getService();
	private LessonTestService testService = LessonTestServiceFactory.getService();

	private StringManager stringManager = StringManagerFactory.getManager();
	
	
	
	@Override
	protected void processController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
		
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params!=null && params.length == 1 && stringManager.isNumeric(params[0])){

			ControllerURI uri = ControllerURI.getURIFromPath(request.getServletPath());
			
			if(uri != null){
				int id = Integer.parseInt(params[0]);
				List<StatsData> stats = null;
				List<StatsData> statsExtra = null;
				List<StatsData> statsExtra2 = null;
				List<Page> pageStack = null;
				StatsType statsType = null;
				String admin = null;
				String backPage = null; 
				boolean error = false;
				
				int exportIndex = requestManager.getNumericParameter(request, Parameter.EXPORT);
				
				switch (uri) {
					case STATS_LESSON_MONTH:
					case STATS_LESSON_YEAR:
					case STATS_AUTHOR_LESSON_MONTH:
					case STATS_AUTHOR_LESSON_YEAR:
					case STATS_ADMIN_LESSON_MONTH:
					case STATS_ADMIN_LESSON_YEAR:
					case STATS_ADMIN_AUTHOR_LESSON_YEAR:
					case STATS_ADMIN_AUTHOR_LESSON_MONTH:
						Lesson lesson = lessonService.getLesson(id);
						
						if(userService.isAllowedForLesson(user, lesson)){
							if(uri == ControllerURI.STATS_LESSON_MONTH || uri == ControllerURI.STATS_AUTHOR_LESSON_MONTH 
									|| uri == ControllerURI.STATS_ADMIN_LESSON_MONTH || uri == ControllerURI.STATS_ADMIN_AUTHOR_LESSON_MONTH){
								stats = statsService.getLessonVisitsLastMonth(lesson);
								statsType = StatsType.MONTH;
								if(exportIndex == 0){
									error = true;
									exportCSV(stats, response);
								}
							}
							else{
								stats = statsService.getLessonVisitsLastYear(lesson);
								statsType = StatsType.YEAR;
								if(exportIndex == 0){
									error = true;
									exportCSV(stats, response);
								}
							}
							
							if(!error && lesson.isTestAvailable()){
								LessonTest test = testService.getLessonTest(lesson);
								requestManager.setAttribute(request, Attribute.LESSONTEST_QUESTIONS, test);
							}
							requestManager.setAttribute(request, Attribute.LESSON, lesson);						
							
							if(!error && (uri == ControllerURI.STATS_AUTHOR_LESSON_YEAR || uri == ControllerURI.STATS_AUTHOR_LESSON_MONTH)){
								pageStack = pageService.getStatsAuthorLessonPageStack(lesson, statsType);
								
								if(StatsType.MONTH == statsType){
									backPage = urlService.getAbsoluteURL(ControllerURI.STATS_AUTHOR_MONTH.toString() + lesson.getAuthor().getId());
								}
								else{
									backPage = urlService.getAbsoluteURL(ControllerURI.STATS_AUTHOR_YEAR.toString() + lesson.getAuthor().getId());
								}
								requestManager.setAttribute(request, Attribute.USER_PROFILE, lesson.getAuthor());
							}
							else if(!error && (uri == ControllerURI.STATS_ADMIN_AUTHOR_LESSON_YEAR || uri == ControllerURI.STATS_ADMIN_AUTHOR_LESSON_MONTH)){
								pageStack = pageService.getStatsAdminAuthorLessonPageStack(lesson, statsType);
								admin = "admin";
								
								if(StatsType.MONTH == statsType){
									backPage = urlService.getAbsoluteURL(ControllerURI.STATS_ADMIN_AUTHOR_MONTH.toString() + lesson.getAuthor().getId());
								}
								else{
									backPage = urlService.getAbsoluteURL(ControllerURI.STATS_ADMIN_AUTHOR_YEAR.toString() + lesson.getAuthor().getId());
								}
								requestManager.setAttribute(request, Attribute.USER_PROFILE, lesson.getAuthor());
							}
							else if(!error && (uri == ControllerURI.STATS_ADMIN_LESSON_YEAR || uri == ControllerURI.STATS_ADMIN_LESSON_MONTH)){
								admin = "admin";
								pageStack = pageService.getStatsAdminLessonPageStack(lesson, statsType);
								
								if(StatsType.MONTH == statsType){
									backPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_STATS_MONTH.toString());
								}
								else{
									backPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_STATS_YEAR.toString());
								}								
							}
							else if(!error){
								pageStack = pageService.getStatsLessonPageStack(lesson, statsType);
								backPage = urlService.getAbsoluteURL(ControllerURI.LESSON_EDIT.toString() + lesson.getId());
							}
						}
						else{
							error = true;
							response.sendError(HttpServletResponse.SC_FORBIDDEN);
						}
						break;				
					case STATS_AUTHOR_MONTH:
					case STATS_AUTHOR_YEAR:
					case STATS_ADMIN_AUTHOR_MONTH:
					case STATS_ADMIN_AUTHOR_YEAR:
						User profile = null;
						profile = userService.getUser(id);
						if(profile != null && (user.getId() == profile.getId() || user.isAdmin())){
							if(uri == ControllerURI.STATS_AUTHOR_MONTH || uri == ControllerURI.STATS_ADMIN_AUTHOR_MONTH){
								switch(exportIndex){
									case 0:
										stats = statsService.getAuthorVisitsLastMonth(profile);
										error = true;
										exportCSV(stats, response);
										break;
									case 1:
										statsExtra = statsService.getAuthorLessonsVisitsLastMonth(profile);
										error = true;
										exportCSV(statsExtra, response);
										break;
									case 2:							
										statsExtra2 = statsService.getAuthorLessonMediaVisitsLastMonth(profile);
										error = true;
										exportCSV(statsExtra2, response);
										break;
									default:
										stats = statsService.getAuthorVisitsLastMonth(profile);
										statsExtra = statsService.getAuthorLessonsVisitsLastMonth(profile);
										statsExtra2 = statsService.getAuthorLessonMediaVisitsLastMonth(profile);
										break;
								}		
								statsType = StatsType.MONTH;
							}
							else{
								switch(exportIndex){
									case 0:
										stats = statsService.getAuthorVisitsLastYear(profile);
										error = true;
										exportCSV(stats, response);
										break;
									case 1:
										statsExtra = statsService.getAuthorLessonsVisitsLastYear(profile);
										error = true;
										exportCSV(statsExtra, response);
										break;
									case 2:							
										statsExtra2 = statsService.getAuthorLessonMediaVisitsLastYear(profile);
										error = true;
										exportCSV(statsExtra2, response);
										break;
									default:
										stats = statsService.getAuthorVisitsLastYear(profile);
										statsExtra = statsService.getAuthorLessonsVisitsLastYear(profile);
										statsExtra2 = statsService.getAuthorLessonMediaVisitsLastYear(profile);
										break;
								}		
								
								statsType = StatsType.YEAR;
							}
						
							requestManager.setAttribute(request, Attribute.USER_PROFILE, profile);
							
							if(!error && (uri == ControllerURI.STATS_ADMIN_AUTHOR_MONTH || uri == ControllerURI.STATS_ADMIN_AUTHOR_YEAR)){
								pageStack = pageService.getStatsAdminAuthorPageStack(profile, statsType);
								admin = "admin";
								
								if(statsType == StatsType.MONTH){
									backPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_STATS_MONTH.toString());
								}
								else{
									backPage = urlService.getAbsoluteURL(ControllerURI.ADMIN_STATS_YEAR.toString());
								}											
							}
							else if(!error){
								pageStack = pageService.getStatsAuthorPageStack(profile, statsType);
							}
							
						}
						else{
							error = true;
							response.sendError(HttpServletResponse.SC_FORBIDDEN);
						}
						break;
		
					default:
						error = true;
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
						break;
				}
				
				if(!error){
					requestManager.setAttribute(request, Attribute.STRING_STATS_TYPE, statsType.toString());
					requestManager.setAttribute(request, Attribute.STRING_BACKPAGE, backPage);
					requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
					requestManager.setAttribute(request, Attribute.LIST_STATS_DATA, stats);
					requestManager.setAttribute(request, Attribute.LIST_STATS_EXTRA, statsExtra);
					requestManager.setAttribute(request, Attribute.LIST_STATS_EXTRA_2, statsExtra2);
					requestManager.setAttribute(request, Attribute.STRING_STATS_ADMIN, admin);
				
					request.getRequestDispatcher("/WEB-INF/views/stats.jsp").forward(request, response);
				}
				
			}
			else{
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
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
	
	private void exportCSV(List<StatsData> stats, HttpServletResponse response) {
		String statsCSV = statsService.getCSVFromStats(stats);
		
		InputStream input = new ByteArrayInputStream(statsCSV.getBytes());
		
		requestManager.downloadFile(response, "text/csv", "data.csv", input);				
	}
}
