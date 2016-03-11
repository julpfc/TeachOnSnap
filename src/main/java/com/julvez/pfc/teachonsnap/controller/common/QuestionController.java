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
 * QuestionController extends {@link CommonController}.
 * <p> 
 * Manages question creation & edition from question.jsp view.
 * <p>
 * Manages POST requests from question.jsp view to edit question's details
 * and GET requests to export JSON files from question data.  
 * <p> 
 * Mapped in {@link ControllerURI#LESSON_TEST_QUESTION}
 */
public class QuestionController extends CommonController {

	private static final long serialVersionUID = 4946929161852755647L;

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
    public QuestionController() {
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
	public QuestionController(UserService userService,
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
					
					//check if we have to show or edit a previous question
					if(params.length>1 && stringManager.isNumeric(params[1])){
						//get question id to show/edit
						int idQuestion = Integer.parseInt(params[1]);
					
						//get question from id
						Question question = lessonTestService.getQuestion(idQuestion);
					
						//check valid question
						if(question!=null && question.getIdLessonTest() == test.getId()){				
					
							//Modifications request
							if(request.getMethod().equals("POST")){
								//get JSON data
								String json = stringManager.unescapeHTML(requestManager.getParameter(request,Parameter.JSON));
								
								//if data present
								if(json!=null){
									//get question from json data
									Question jQuestion = jsonManager.JSON2Object(json, Question.class);
									
									if(jQuestion!=null){
										//check if changed
										if(question.equals(jQuestion)){
											//No changes -> redirect to edit test page
											setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES);
											response.sendRedirect(test.getEditURL());
										}
										else{
											//check if it's a velid question
											if(question.isEditedVersion(jQuestion)){
												//check if all mandatory data is present 
												if(jQuestion.isFullFilled()){
													//save question & redirect to edit test page
													lessonTestService.saveQuestion(jQuestion);
													setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.QUESTION_SAVED);
													response.sendRedirect(test.getEditURL());
												}
												else{
													//Missing params for JSON validation -> bas params
													response.sendError(HttpServletResponse.SC_BAD_REQUEST);
												}
											}
											else{
												//Question fields mismatch -> bad params
												response.sendError(HttpServletResponse.SC_BAD_REQUEST);
											}										
										}
									}
									else{
										//Invalid JSON -> bad params
										response.sendError(HttpServletResponse.SC_BAD_REQUEST);
									}
								}
								else{
									//Missing json -> bad params
									response.sendError(HttpServletResponse.SC_BAD_REQUEST);
								}
							}
							else{
								//No modification request -> load question information for view
								
								//get export parameter
								String export = requestManager.getParameter(request, Parameter.EXPORT);
								
								//if export request
								if(export != null){
									//export JSON data																								 	 
									String outJSON = jsonManager.object2SimpleJSON(question);
									if(outJSON != null){
										requestManager.downloadFile(response, "application/json", "test_"+test.getId()+".json\"", 
											new ByteArrayInputStream(outJSON.getBytes("UTF-8")));
									}
									
								}
								else{
									//load question information for view						
									List<Page> pageStack = pageService.getEditQuestionPageStack(lesson, test, question);
									requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
										
									requestManager.setAttribute(request, Attribute.QUESTION, question);
									requestManager.setAttribute(request, Attribute.LESSONTEST_QUESTIONS, test);
									//dispatch to view		
									request.getRequestDispatcher("/WEB-INF/views/question.jsp").forward(request, response);
								}
							}
						}				
						else{
							//Question not found
							response.sendError(HttpServletResponse.SC_NOT_FOUND);
						}
					}
					else if(params.length==1){
						//No idQuestion at URL, new question request
						//if creation request
						if(request.getMethod().equals("POST")){
							//get JSON data
							String json = stringManager.unescapeHTML(requestManager.getParameter(request, Parameter.JSON));
							
							//if data present
							if(json!=null){
								//get question from json data
								Question jQuestion = jsonManager.JSON2SimpleObject(json, Question.class);
								
								if(jQuestion!=null){
									//check if all mandatory data is present
									if(jQuestion.isFullFilled() && jQuestion.getAnswers().size() == test.getNumAnswers()){
										//complete information and create question
										jQuestion.setIdLessonTest(test.getId());
										jQuestion.setPriority((byte)test.getNumQuestions());
										test = lessonTestService.createQuestion(jQuestion);
										
										if(test != null){
											//success -> redirect to edit test page
											setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.QUESTION_CREATED);
											response.sendRedirect(test.getEditURL());
										}
										else{
											//error -> show message on current page
											test = lessonTestService.getLessonTest(idLessonTest);
											setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
											response.sendRedirect(test.getNewQuestionURL());
										}
									}
									else{
										//Missing params for JSON validation -> bad params
										response.sendError(HttpServletResponse.SC_BAD_REQUEST);
									}								
								}
								else{
									//Invalid JSON -> bad params
									response.sendError(HttpServletResponse.SC_BAD_REQUEST);
								}
							}
							else{
								//Missing json -> bad params
								response.sendError(HttpServletResponse.SC_BAD_REQUEST);
							}
						}
						else{
							//no creation request, just load information for view
							List<Page> pageStack = pageService.getNewQuestionPageStack(lesson, test);
							requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);							
							requestManager.setAttribute(request, Attribute.LESSONTEST_QUESTIONS, test);
							
							//dispatch to view
							request.getRequestDispatcher("/WEB-INF/views/question.jsp").forward(request, response);
						}
						
					}
					else{
						//invalid idQuestion -> bad params
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					}
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
			//No idLessonTest -> bad params 
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}
}
