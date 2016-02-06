package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.controller.model.ErrorType;
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
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * NewLessonTestController extends {@link CommonController}.
 * <p>
 * Manages lesson test creation from newTest.jsp view.
 * <p>
 * Manages POST requests from newTest.jsp view to create lesson tests
 * and GET requests to display the view.   
 * <p> 
 * Mapped in {@link ControllerURI#LESSON_TEST_NEW}
 */
public class NewLessonTestController extends CommonController {

	private static final long serialVersionUID = 8373021834624827353L;

	/** Provides the functionality to work with lessons. */
	private LessonService lessonService;
	
	/** Provides the functionality to work with lesson's tests */
	private LessonTestService lessonTestService;
	
	/** Provides the functionality to work with user pages (localized names & links) and the hierarchy (page stack) */
	private PageService pageService;

	/**
     * Default constructor
     */
    public NewLessonTestController() {
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
	public NewLessonTestController(UserService userService,
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
		if(params!=null && params.length>0 && stringManager.isNumeric(params[0])){
			
			//get lesson id from params
			int idLesson = Integer.parseInt(params[0]);
			
			//get lesson from id
			Lesson lesson = lessonService.getLesson(idLesson);
			
			//if lesson found
			if(lesson!=null){				
				//check if user is allowed for lesson edition
				if(userService.isAllowedForLesson(user, lesson)){
					if(request.getMethod().equals("POST")){
						//get test details from parameters
						String multipleChoice = requestManager.getParameter(request, Parameter.LESSON_TEST_MULTIPLECHOICE);
						int numAnswers = requestManager.getNumericParameter(request, Parameter.LESSON_TEST_NUMANSWERS);
						
						//check if the parameter is present
						if(multipleChoice!=null){
							//check if the parameter is valid
							if(numAnswers>1 && numAnswers<10){
								//get current test for this lesson
								LessonTest test = lessonTestService.getLessonTest(lesson);
								//if there is no test yet
								if(test == null){
									//create test
									test = lessonTestService.createLessonTest(lesson,stringManager.isTrue(multipleChoice),numAnswers);
									
									if(test!=null){
										//success & redirect to new test edit page
										setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.TEST_CREATED);
										response.sendRedirect(test.getEditURL());
									}
									else{
										//error -> reload page
										setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
										response.sendRedirect(lesson.getNewTestURL());
									}
									
								}
								else{
									//error, there is already a test for this lesson
									setErrorSession(request, ErrorType.ERR_SAVE_DUPLICATE, ErrorMessageKey.SAVE_DUPLICATE_ERROR_TEST);
									//redirect to lesson edit page
									response.sendRedirect(lesson.getEditURL());
								}
							}
							else{
								//bad input -> reload page
								setErrorSession(request, ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.INVALID_INPUT_ERROR_TEST);
								response.sendRedirect(lesson.getNewTestURL());
							}	
						}
						else{
							//missing aprams -> reload page
							response.sendRedirect(lesson.getEditURL());
						}
					}
					else{
						//no creation yet, load common page information for view
						List<Page> pageStack = pageService.getNewLessonTestPageStack(lesson);					
						requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);						
						requestManager.setAttribute(request, Attribute.LESSON, lesson);
						
						//dispatch to view
						request.getRequestDispatcher("/WEB-INF/views/newTest.jsp").forward(request, response);
					}				
				}
				else{
					//user not allowed
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
				}						
			}
			else{
				//lesson not found
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
