package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
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
import com.julvez.pfc.teachonsnap.stats.model.StatsLessonTest;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * StatsLessonTestController extends {@link CommonController}.
 * <p>
 * Manages lesson test stats from test.jsp view.
 * <p>
 * Manages GET requests from test.jsp view to show lesson test's
 * stats.  
 * <p> 
 * Mapped in {@link ControllerURI#STATS_AUTHOR_LESSON_TEST}
 * and {@link ControllerURI#STATS_AUTHOR_LESSON_MONTH}
 * and {@link ControllerURI#STATS_LESSON_TEST}
 * and {@link ControllerURI#STATS_ADMIN_AUTHOR_LESSON_TEST}
 * and {@link ControllerURI#STATS_ADMIN_LESSON_TEST}
 * and {@link ControllerURI#STATS_TEST}
 */
public class StatsLessonTestController extends CommonController {

	private static final long serialVersionUID = 5620915035207187126L;

	/** Provides the functionality to work with lessons. */
	private LessonService lessonService;
	
	/** Provides the functionality to work with lesson's tests */
	private LessonTestService lessonTestService;
	
	/** Provides the functionality to work with user pages (localized names & links) and the hierarchy (page stack) */
	private PageService pageService;

	/**
     * Default constructor
     */
    public StatsLessonTestController() {
    	this(UserServiceFactory.getService(),
        	LangServiceFactory.getService(),
        	URLServiceFactory.getService(),
        	StatsServiceFactory.getService(),
        	RequestManagerFactory.getManager(),
        	LogManagerFactory.getManager(),
        	PropertyManagerFactory.getManager(),
        	StringManagerFactory.getManager(),
        	LessonServiceFactory.getService(),
        	LessonTestServiceFactory.getService(),
        	PageServiceFactory.getService());        
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
	 * @param lessonTestService Provides the functionality to work with lesson's tests
	 * @param pageService Provides the functionality to work with user pages (localized names & links) and the hierarchy (page stack)
	 */
	public StatsLessonTestController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, LessonService lessonService,			 
			LessonTestService lessonTestService, PageService pageService) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager);
		
		if(lessonService == null || lessonTestService == null
				|| pageService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}		
		this.lessonService = lessonService;
		this.lessonTestService = lessonTestService;				
		this.pageService = pageService;		
	}
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {

		//get controller params from URI
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		//check valid params
		if(params!=null && params.length == 1 && stringManager.isNumeric(params[0])){
			//get test id from params
			int idLessonTest = Integer.parseInt(params[0]);
			
			//get test from id
			LessonTest test = lessonTestService.getLessonTest(idLessonTest);
			
			//if test found
			if(test!=null){
				//get lesson from test
				Lesson lesson = lessonService.getLesson(test.getIdLesson());
				
				//check if user is allowed for lesson stats
				if(userService.isAllowedForLesson(user, lesson)){
					//get test stats and store for view
					UserTestRank testRank = statsService.getUserTestRank(test.getId(), visit.getUser().getId());
					List<UserTestRank> testRanks = statsService.getTestRanks(test.getId());
					requestManager.setAttribute(request, Attribute.USERTESTRANK, testRank);
					requestManager.setAttribute(request, Attribute.LIST_USERTESTRANKS, testRanks);
					requestManager.setAttribute(request, Attribute.LESSON, lesson);
					requestManager.setAttribute(request, Attribute.LESSONTEST_QUESTIONS, test);
					StatsLessonTest statsTest = statsService.getStatsLessonTest(test);
					requestManager.setAttribute(request, Attribute.STATSTEST, statsTest);
					
					//Get controller's mapping
					ControllerURI uri = ControllerURI.getURIFromPath(request.getServletPath());
					
					String backPage = null;
					List<Page> pageStack = null;
					
					//calculate back page and page stack for current controller's mapping
					if(uri != null){
						switch (uri) {
						case STATS_AUTHOR_LESSON_TEST:
							backPage = urlService.getAbsoluteURL(ControllerURI.STATS_AUTHOR_LESSON_MONTH.toString() + lesson.getId());
							pageStack = pageService.getStatsAuthorLessonTestPageStack(lesson, test);
							break;
						case STATS_LESSON_TEST:
							backPage = urlService.getAbsoluteURL(ControllerURI.STATS_LESSON_MONTH.toString() + lesson.getId());
							pageStack = pageService.getStatsLessonTestPageStack(lesson, test);
							break;
						case STATS_ADMIN_AUTHOR_LESSON_TEST:
							backPage = urlService.getAbsoluteURL(ControllerURI.STATS_ADMIN_AUTHOR_LESSON_MONTH.toString() + lesson.getId());
							pageStack = pageService.getStatsAdminAuthorLessonTestPageStack(lesson, test);
							break;
						case STATS_ADMIN_LESSON_TEST:
							backPage = urlService.getAbsoluteURL(ControllerURI.STATS_ADMIN_LESSON_MONTH.toString() + lesson.getId());
							pageStack = pageService.getStatsAdminLessonTestPageStack(lesson, test);
							break;
						case STATS_TEST:
							backPage = lesson.getEditURL();
							pageStack = pageService.getStatsTestPageStack(lesson, test);
							break;
						
						default:
							break;						
						}
					}
					//store back page and page stack for view
					requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
					requestManager.setAttribute(request, Attribute.STRING_BACKPAGE, backPage);
							
					//dispatch to view
				    request.getRequestDispatcher("/WEB-INF/views/test.jsp").forward(request, response);
				}
				else{
					//user not allowed
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
				}						
			}
			else{
				//test not found
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
}
