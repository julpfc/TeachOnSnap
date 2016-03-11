package com.julvez.pfc.teachonsnap.controller.common;

import java.io.ByteArrayInputStream;
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
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.LessonTestService;
import com.julvez.pfc.teachonsnap.lessontest.LessonTestServiceFactory;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.Question;
import com.julvez.pfc.teachonsnap.manager.json.JSONManager;
import com.julvez.pfc.teachonsnap.manager.json.JSONManagerFactory;
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
 * EditLessonTestController extends {@link CommonController}.
 * <p>
 * Manages lesson test edition from editTest.jsp view.
 * <p>
 * Manages GET requests from editTest.jsp view to edit lesson test's
 * details.  
 * <p> 
 * Mapped in {@link ControllerURI#LESSON_TEST_EDIT}
 */
public class EditLessonTestController extends CommonController {

	private static final long serialVersionUID = -585021454027836172L;

	/** Provides the functionality to work with lessons. */
	private LessonService lessonService;
	
	/** Provides the functionality to work with lesson's tests */
	private LessonTestService lessonTestService;
	
	/** Provides the functionality to work with user pages (localized names & links) and the hierarchy (page stack) */
	private PageService pageService;

	/** JSON manager providing JSON manipulation utilities */
	private JSONManager jsonManager;

	/**
     * Default constructor
     */
    public EditLessonTestController() {
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
        	PageServiceFactory.getService(),
        	JSONManagerFactory.getManager());        
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
	 * @param jsonManager JSON manager providing JSON manipulation utilities
	 */
	public EditLessonTestController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, LessonService lessonService,			 
			LessonTestService lessonTestService, PageService pageService, 
			JSONManager jsonManager) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager);
		
		if(lessonService == null || lessonTestService == null
				|| pageService == null || jsonManager == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}		
		this.lessonService = lessonService;
		this.lessonTestService = lessonTestService;				
		this.pageService = pageService;		
		this.jsonManager = jsonManager;		
	}
	
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {

		//get controller params from URI
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		//check valid params
		if(params!=null && params.length>0 && stringManager.isNumeric(params[0])){
			
			//get test id from params
			int idLessonTest = Integer.parseInt(params[0]);
			
			//get test from id
			LessonTest test = lessonTestService.getLessonTest(idLessonTest);
			
			//if test found
			if(test!=null){
				//get lesson from test
				Lesson lesson = lessonService.getLesson(test.getIdLesson());
				
				//check if user is allowed for lesson edition
				if(userService.isAllowedForLesson(user, lesson)){
					//get publish parameter
					String publish = requestManager.getParameter(request,Parameter.LESSON_TEST_PUBLISH);
					
					//check if the parameter is present
					if(publish!=null){
						if(stringManager.isTrue(publish)){
							//publish
							if(test.getNumQuestions()>0){
								lessonTestService.publish(test.getId());
								setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.TEST_PUBLISHED);
							}
							else{
								setErrorSession(request, ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.INVALID_INPUT_ERROR_TEST_PUBLISH);
							}
						}				
						else{
							//unpublish
							lessonTestService.unpublish(test.getId());
							setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.TEST_UNPUBLISHED);
						}
						//return to previous page
						response.sendRedirect(requestManager.getSessionAttribute(request, SessionAttribute.LAST_PAGE));
					}
					else {
						//get delete question parameter
						int deleteQuestionID = requestManager.getNumericParameter(request, Parameter.QUESTION_DELETE);
						
						//check if the parameter is present
						if(deleteQuestionID>0){	
							//look up for the question to remove
							Question question = null;
							for(Question q:test.getQuestions()){
								if(q.getId() == deleteQuestionID){
									question = q;
									break;
								}
							}
							//if question found
							if(question!=null){
								//remove question
								if(!lessonTestService.removeQuestion(question)){
									//error									
									setErrorSession(request, ErrorType.ERR_REMOVE, ErrorMessageKey.REMOVE_ERROR);									
								}
								else {
									//success
									setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.QUESTION_REMOVED);
								}
								//reload page
								response.sendRedirect(test.getEditURL());
							}
							else{
								//question not found
								response.sendError(HttpServletResponse.SC_NOT_FOUND);
							}
						}
						else{
							//get delete test parameter
							String deleteTest = requestManager.getParameter(request, Parameter.LESSON_TEST_DELETE);
							
							//check if the parameter is present
							if(deleteTest!=null && stringManager.isTrue(deleteTest)){
								//remove test
								if(!lessonTestService.removeLessonTest(test)){
									//error & reload page
									setErrorSession(request, ErrorType.ERR_REMOVE, ErrorMessageKey.REMOVE_ERROR);									
									response.sendRedirect(test.getEditURL());							
								}
								else {
									//success & return to edit lesson page
									setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.TEST_REMOVED);
									response.sendRedirect(lesson.getEditURL());
								}
							}
							else{	
								//get move question parameters
								int newPriority = requestManager.getNumericParameter(request, Parameter.QUESTION_PRIORITY);
								int idQuestion = requestManager.getNumericParameter(request, Parameter.QUESTIONID);
								
								//check if the parameters are present
								if(newPriority>=0 && idQuestion>0){
									//look up for the question to move
									Question question = null;
									for(Question q:test.getQuestions()){
										if(q.getId() == idQuestion){
											question = q;
											break;
										}
									}
									//if question found
									if(question!=null){	
										//move question
										if(!lessonTestService.moveQuestion(question, newPriority)){
											//error									
											setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);									
										}
										else {
											//success
											setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.QUESTION_SAVED);
										}
										//reload page
										response.sendRedirect(test.getEditURL());
									}
									else{
										//question not found
										response.sendError(HttpServletResponse.SC_NOT_FOUND);
									}
								}
								else{
									//get export parameter
									String export = requestManager.getParameter(request, Parameter.EXPORT);
									
									//check if the parameters are present
									if(export != null){
										//export lesson in JSON
										String outJSON = jsonManager.object2SimpleJSON(test);	
										if(outJSON != null){
											requestManager.downloadFile(response, "application/json", "test_"+test.getId()+".json\"", 
												new ByteArrayInputStream(outJSON.getBytes("UTF-8")));
										}
									}
									else{
										//no modifications or exportation request
										//get common page information for the view
										List<Page> pageStack = pageService.getEditLessonTestPageStack(lesson, test);
										requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);										
										requestManager.setAttribute(request, Attribute.LESSON, lesson);
										requestManager.setAttribute(request, Attribute.LESSONTEST_QUESTIONS, test);
										
										//dispatch to view
										request.getRequestDispatcher("/WEB-INF/views/editTest.jsp").forward(request, response);
									}
								}
							}
						}							 
					}				
				}
				else{
					//User not allowed
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
				}						
			}
			else{
				//Test not found
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
		else{
			//Bad params
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	    
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}
}
