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
import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.LessonTestService;
import com.julvez.pfc.teachonsnap.lessontest.LessonTestServiceFactory;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
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
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * StatsController extends {@link CommonController}.
 * <p>
 * Loads last month or year information stats and redirects to the
 * stats.jsp view.
 * <p>
 * Manages GET requests from stats.jsp view to download CSV files
 * from stats data.  
 * <p> 
 * Mapped in {@link ControllerURI#STATS_LESSON_MONTH}
 * and {@link ControllerURI#STATS_LESSON_YEAR}
 * and {@link ControllerURI#STATS_AUTHOR_LESSON_MONTH}
 * and {@link ControllerURI#STATS_AUTHOR_LESSON_YEAR}
 * and {@link ControllerURI#STATS_ADMIN_LESSON_MONTH}
 * and {@link ControllerURI#STATS_ADMIN_LESSON_YEAR}
 * and {@link ControllerURI#STATS_ADMIN_AUTHOR_LESSON_YEAR}
 * and {@link ControllerURI#STATS_ADMIN_AUTHOR_LESSON_MONTH}
 * and {@link ControllerURI#STATS_AUTHOR_MONTH}
 * and {@link ControllerURI#STATS_AUTHOR_YEAR}
 * and {@link ControllerURI#STATS_ADMIN_AUTHOR_MONTH}
 * and {@link ControllerURI#STATS_ADMIN_AUTHOR_YEAR}
 */
public class StatsController extends CommonController {

	private static final long serialVersionUID = 24157738842171552L;
	
	/** Provides the functionality to work with lessons. */
	private LessonService lessonService;
	
	/** Provides the functionality to work with user pages (localized names & links) and the hierarchy (page stack) */
	private PageService pageService;
	
	/** Provides the functionality to work with lesson's tests. */
	private LessonTestService testService;

	/**
     * Default constructor
     */
    public StatsController() {
    	this(UserServiceFactory.getService(),
        	LangServiceFactory.getService(),
        	URLServiceFactory.getService(),
        	StatsServiceFactory.getService(),
        	RequestManagerFactory.getManager(),
        	LogManagerFactory.getManager(),
        	PropertyManagerFactory.getManager(),
        	StringManagerFactory.getManager(),
        	LessonServiceFactory.getService(),
        	PageServiceFactory.getService(),
        	LessonTestServiceFactory.getService());        
    }
    
    /**
	 * Constructor requires all parameters not to be null
	 * @param userService Provides the functionality to work with application's users.
	 * @param langService Provides the functionality to work with different languages to the application
	 * @param urlService Provides the functionality to work with application's URLs
	 * @param statsService Provides the functionality to work with application's stats
	 * @param requestManager Provides {@link HttpServletRequest} and {@link HttpServletResponse} access/manipulation utilities
	 * @param logger Log manager providing logging capabilities
	 * @param properties Property manager providing access to properties files
	 * @param stringManager String manager providing string manipulation utilities
	 * @param lessonService Provides the functionality to work with lessons.
	 * @param pageService Provides the functionality to work with user pages (localized names & links) and the hierarchy (page stack)
	 * @param testService Provides the functionality to work with lesson's tests.
	 */
	public StatsController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, LessonService lessonService, 
			PageService pageService, LessonTestService testService) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager);
		
		if(lessonService == null 
				|| pageService == null || testService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}		
		this.lessonService = lessonService;
		this.pageService = pageService;		
		this.testService = testService;		
	}
	
	@Override
	protected void processController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
		
		//get controller params from URI
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		//check valid params
		if(params != null && params.length == 1 && stringManager.isNumeric(params[0])){

			//Get controller's mapping
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
				
				//get stat user is asking to export in CSV format
				int exportIndex = requestManager.getNumericParameter(request, Parameter.EXPORT);
				
				//depending on the controller's mapping
				switch (uri) {
					case STATS_LESSON_MONTH:
					case STATS_LESSON_YEAR:
					case STATS_AUTHOR_LESSON_MONTH:
					case STATS_AUTHOR_LESSON_YEAR:
					case STATS_ADMIN_LESSON_MONTH:
					case STATS_ADMIN_LESSON_YEAR:
					case STATS_ADMIN_AUTHOR_LESSON_YEAR:
					case STATS_ADMIN_AUTHOR_LESSON_MONTH:
						//get lesson from id for stats
						Lesson lesson = lessonService.getLesson(id);
						
						//check if user is allowed for lesson
						if(userService.isAllowedForLesson(user, lesson)){
							//get last month stts
							if(uri == ControllerURI.STATS_LESSON_MONTH || uri == ControllerURI.STATS_AUTHOR_LESSON_MONTH 
									|| uri == ControllerURI.STATS_ADMIN_LESSON_MONTH || uri == ControllerURI.STATS_ADMIN_AUTHOR_LESSON_MONTH){
								stats = statsService.getLessonVisitsLastMonth(lesson);
								statsType = StatsType.MONTH;
								
								//check if export data
								if(exportIndex == 0){
									error = true;
									exportCSV(stats, response);
								}
							}
							else{
								//get last year stts
								stats = statsService.getLessonVisitsLastYear(lesson);
								statsType = StatsType.YEAR;
								
								//check if export data
								if(exportIndex == 0){
									error = true;
									exportCSV(stats, response);
								}
							}
							
							//get test stats if present
							if(!error && lesson.isTestAvailable()){
								LessonTest test = testService.getLessonTest(lesson);
								requestManager.setAttribute(request, Attribute.LESSONTEST_QUESTIONS, test);
							}
							
							//store lesson for view
							requestManager.setAttribute(request, Attribute.LESSON, lesson);						
							
							//save backpage and page stack for author
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
								//save backpage and page stack for admin and flags admin mode
								
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
								//save backpage and page stack for admin and flags admin mode
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
								//save backpage and page stack for author
								pageStack = pageService.getStatsLessonPageStack(lesson, statsType);
								backPage = urlService.getAbsoluteURL(ControllerURI.LESSON_EDIT.toString() + lesson.getId());
							}
						}
						else{
							//user is not allowed for this lesson or lesson is null
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
								//get & export data for specified stats index
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
										//list last month stats
										stats = statsService.getAuthorVisitsLastMonth(profile);
										statsExtra = statsService.getAuthorLessonsVisitsLastMonth(profile);
										statsExtra2 = statsService.getAuthorLessonMediaVisitsLastMonth(profile);
										break;
								}		
								statsType = StatsType.MONTH;
							}
							else{
								//get & export data for specified stats index
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
										//list last year stats
										stats = statsService.getAuthorVisitsLastYear(profile);
										statsExtra = statsService.getAuthorLessonsVisitsLastYear(profile);
										statsExtra2 = statsService.getAuthorLessonMediaVisitsLastYear(profile);
										break;
								}		
								
								statsType = StatsType.YEAR;
							}
						
							//store user profile for view
							requestManager.setAttribute(request, Attribute.USER_PROFILE, profile);
							
							//save backpage and page stack for admin and flags admin mode
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
								//get page stack for author
								pageStack = pageService.getStatsAuthorPageStack(profile, statsType);
							}
							
						}
						else{
							//user is not allowed to get this stats
							error = true;
							response.sendError(HttpServletResponse.SC_FORBIDDEN);
						}
						break;
		
					default:
						//bad mapping
						error = true;
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
						break;
				}
				
				//if no error and no export -> redirect to the view
				if(!error){
					//get page common information and store for the view
					requestManager.setAttribute(request, Attribute.STRING_STATS_TYPE, statsType.toString());
					requestManager.setAttribute(request, Attribute.STRING_BACKPAGE, backPage);
					requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
					requestManager.setAttribute(request, Attribute.LIST_STATS_DATA, stats);
					requestManager.setAttribute(request, Attribute.LIST_STATS_EXTRA, statsExtra);
					requestManager.setAttribute(request, Attribute.LIST_STATS_EXTRA_2, statsExtra2);
					requestManager.setAttribute(request, Attribute.STRING_STATS_ADMIN, admin);
				
					//dispatch to view
					request.getRequestDispatcher("/WEB-INF/views/stats.jsp").forward(request, response);
				}
				
			}
			else{
				//bad mapping
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
		else{
			//bad params
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}


	@Override
	protected boolean isPrivateZone() {		
		return true;
	}
	
	/**
	 * Converts stats information to Comma-separated values (CSV) file
	 * and downloads it.
	 * @param stats to generate CSV from
	 * @param response Response
	 */
	private void exportCSV(List<StatsData> stats, HttpServletResponse response) {
		//get CSV data
		String statsCSV = statsService.getCSVFromStats(stats);
		
		//get input stream to CSV
		InputStream input = new ByteArrayInputStream(statsCSV.getBytes());
		
		//download stream to response
		requestManager.downloadFile(response, "text/csv", "data.csv", input);				
	}	
}
