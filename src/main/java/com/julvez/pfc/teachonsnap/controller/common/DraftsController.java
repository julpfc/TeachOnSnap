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
import com.julvez.pfc.teachonsnap.lesson.model.LessonPropertyName;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
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
 * DraftsController extends {@link CommonController}. 
 * <p>
 * Performs pagination of lesson drafts to be passed to the 
 * lessons.jsp view to be displayed.
 * <p>
 * It's a view controller, processes the request, manages the 
 * pagination and loads common information for the lessons.jsp
 * view.
 * <p>
 * Mapped in {@link ControllerURI#LESSON_DRAFTS_BY_USER}  
 */
public class DraftsController extends CommonController {

	private static final long serialVersionUID = 6547159334243210678L;

	/** Pagination. Limit of lessons per page. */
	private final int MAX_LESSONS_PER_PAGE; 
	
	/** Provides the functionality to work with lessons. */
	private LessonService lessonService;
			
	/**
     * Default constructor
     */
    public DraftsController() {
    	this(UserServiceFactory.getService(),
        	LangServiceFactory.getService(),
        	URLServiceFactory.getService(),
        	StatsServiceFactory.getService(),
        	RequestManagerFactory.getManager(),
        	LogManagerFactory.getManager(),
        	PropertyManagerFactory.getManager(),
        	StringManagerFactory.getManager(),
        	LessonServiceFactory.getService());        
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
	 */
	public DraftsController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, LessonService lessonService) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager);
		
		if(lessonService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}		
		this.lessonService = lessonService;
				
		MAX_LESSONS_PER_PAGE = (int)properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
	}
	
	@Override
	protected void processController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)	throws ServletException, IOException {
		//get controller params from URI
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		//get page number
		int pageResult = getPageResult(params);
		
		if(pageResult >= 0){
			boolean hasNextPage = false;
			//get author id from URI
			int idUser = getUserIDFromURI(params);
					
			if(idUser > 0){
				//get author from id
				User draftUser = userService.getUser(idUser);
				
				if(draftUser != null){
					//user is allowed for drafts
					if((draftUser.getId() == user.getId() && user.isAuthor()) || user.isAdmin()){
			
						//get drafts from author
						List<Lesson> lessons = lessonService.getLessonDraftsFromUser(draftUser, pageResult);	
						
						//Check if is there a next page, and remove flag item
						if(lessons.size()>MAX_LESSONS_PER_PAGE){
							hasNextPage = true;
							lessons.remove(MAX_LESSONS_PER_PAGE);
						}
						
						//set next page pagination
						String nextPage = null;
						if(hasNextPage){
							nextPage = urlService.getAbsoluteURL(ControllerURI.LESSON_DRAFTS_BY_USER.toString() + idUser + "/" + (pageResult+MAX_LESSONS_PER_PAGE));
						}
						
						//set prev page pagination
						String prevPage = null;
						if(pageResult>0){
							prevPage = urlService.getAbsoluteURL(ControllerURI.LESSON_DRAFTS_BY_USER.toString() + idUser + "/");
							
							if(pageResult>MAX_LESSONS_PER_PAGE){
								prevPage = prevPage + (pageResult-MAX_LESSONS_PER_PAGE);
							}
						}
						//store next/prev page links to request for view
						requestManager.setAttribute(request, Attribute.STRING_NEXTPAGE, nextPage);
						requestManager.setAttribute(request, Attribute.STRING_PREVPAGE, prevPage);
					
						//store rest of information to request for view
						requestManager.setAttribute(request, Attribute.LIST_LESSON, lessons);
						
						//dispatch to view
					    request.getRequestDispatcher("/WEB-INF/views/lessons.jsp").forward(request, response);			
					}
					else{
						//not allowed
						response.sendError(HttpServletResponse.SC_FORBIDDEN);
					}
				}
				else{
					//author not found
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}	
			}
			else{
				//bad params
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
	 * Returns current page in pagination from URI params
	 * @param params Controller params from URI
	 * @return Page number (starting from 0)
	 */
	private int getPageResult(String[] params) {
		int pageResult = -1;
		
		//if params has correct size
		if(params!=null && params.length>0){
			//check if page number is present
			if(params.length>1){
				if(stringManager.isNumeric(params[1])){
					//get page number
					pageResult = Integer.parseInt(params[1]);
				}				
			}
			else{
				//no page set -> first page
				pageResult = 0;
			}
		}

		return pageResult;
	}
	
	/**
	 * Returns the user ID from the URI.
	 * @param params Controller params from URI
	 * @return user ID from the URI if present, -1 otherwise
	 */
	private int getUserIDFromURI(String[] params) {
		int idUser = -1;
		String URI = null;
		
		if(params!=null && params.length>0){
			URI = params[0];
			
			if(stringManager.isNumeric(URI)){
				idUser = Integer.parseInt(URI);
			}
		}
		
		return idUser;
	}

}
