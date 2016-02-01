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
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.TagServiceFactory;
import com.julvez.pfc.teachonsnap.tag.model.CloudTag;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * CommonController abstract implementation class PagerController. 
 * <p>
 * Performs pagination common tasks for a controller which will gather 
 * information and be passed to the lessons.jsp view to be displayed.
 * <p>
 * It's a view controller, processes the request, manages the pagination common tasks,
 * asks implementations for pagination content and loads common information for 
 * the lessons.jsp view.  
 */
public abstract class PagerController extends CommonController {

	private static final long serialVersionUID = 8657057759677354918L;
	
	/** Pagination. Limit of lessons per page. */
	protected final int MAX_LESSONS_PER_PAGE; 
	
	/** Provides the functionality to work with lessons. */
	protected LessonService lessonService;
	
	/** Provides the functionality to work with lessons. */
	protected TagService tagService;
	
	/**
     * Default constructor
     */
    public PagerController() {
    	this(UserServiceFactory.getService(),
        	LangServiceFactory.getService(),
        	URLServiceFactory.getService(),
        	StatsServiceFactory.getService(),
        	RequestManagerFactory.getManager(),
        	LogManagerFactory.getManager(),
        	PropertyManagerFactory.getManager(),
        	StringManagerFactory.getManager(),
        	LessonServiceFactory.getService(),
        	TagServiceFactory.getService());        
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
	 * @param tagService Provides the functionality to work with lessons.
	 */
	public PagerController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, LessonService lessonService, 
			TagService tagService) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager);
		
		if(lessonService == null || tagService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}		
		this.lessonService = lessonService;
		this.tagService = tagService;
		
		MAX_LESSONS_PER_PAGE = (int)properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
	}

	@Override
	protected boolean isPrivateZone() {		
		return false;
	}
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {

		//get controller params from URI
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		//get ControllerURI
		ControllerURI controllerURI = ControllerURI.getURIFromPath(request.getServletPath());
		
		//get page number
		int pageResult = getPageResult(params);
		
		if(pageResult >= 0 && controllerURI != null){
			boolean hasNextPage = false;
			//get search criteria
			String searchURI = getSearchURI(params);
					
			//get lessons from criteria
			List<Lesson> lessons = getLessons(searchURI, pageResult);	
			
			//Check if is there a next page, and remove flag item
			if(lessons.size()>MAX_LESSONS_PER_PAGE){
				hasNextPage = true;
				lessons.remove(MAX_LESSONS_PER_PAGE);
			}
			
			//set next page pagination
			String nextPage = null;
			if(hasNextPage){
				if(searchURI == null)
					nextPage = urlService.getAbsoluteURL(controllerURI.toString() + (pageResult+MAX_LESSONS_PER_PAGE));
				else
					nextPage = urlService.getAbsoluteURL(controllerURI.toString() + searchURI + "/" + (pageResult+MAX_LESSONS_PER_PAGE));
			}
			
			//set prev page pagination
			String prevPage = null;
			if(pageResult>0){
				if(searchURI == null)
					prevPage = urlService.getAbsoluteURL(controllerURI.toString());
				else
					prevPage = urlService.getAbsoluteURL(controllerURI.toString() + searchURI + "/");
				
				if(pageResult>MAX_LESSONS_PER_PAGE){
					prevPage = prevPage + (pageResult-MAX_LESSONS_PER_PAGE);
				}
			}
			//store next/prev page links to request for view
			requestManager.setAttribute(request, Attribute.STRING_NEXTPAGE, nextPage);
			requestManager.setAttribute(request, Attribute.STRING_PREVPAGE, prevPage);
			
			//get page common information and store for the view
			List<CloudTag> tagUseCloudTags = tagService.getTagUseCloudTags();
			List<CloudTag> authorCloudTags = tagService.getAuthorCloudTags();
			List<CloudTag> tagSearchCloudTags = tagService.getTagSearchCloudTags();
			List<CloudTag> lessonCloudTags = tagService.getLessonViewCloudTags();
			requestManager.setAttribute(request, Attribute.LIST_CLOUDTAG_TAG_SEARCH, tagSearchCloudTags);
			requestManager.setAttribute(request, Attribute.LIST_CLOUDTAG_AUTHOR, authorCloudTags);
			requestManager.setAttribute(request, Attribute.LIST_CLOUDTAG_TAG_USE, tagUseCloudTags);
			requestManager.setAttribute(request, Attribute.LIST_CLOUDTAG_LESSON, lessonCloudTags);
			
			//store rest of information to request for view
			requestManager.setAttribute(request, Attribute.LIST_LESSON, lessons);			
			requestManager.setAttribute(request, Attribute.STRING_SEARCHTYPE, request.getServletPath().substring(1));			
			String searchKeyword = getSearchKeyword(searchURI,lessons);			
			if(!stringManager.isEmpty(searchKeyword)){
				requestManager.setAttribute(request, Attribute.STRING_SEARCHKEYWORD, searchKeyword);
			}
		    
			//save stats if necessary
			saveStats(visit, searchURI, lessons);
			
			//dispatch to view
		    request.getRequestDispatcher("/WEB-INF/views/lessons.jsp").forward(request, response);
		}
		else{
			//bad params
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	/**
	 * Returns the type of search from the URI (tag, author, last lessons).
	 * Default implementation, should be overriden if necessary.
	 * @param params Controller params from URI
	 * @return search type if present, null otherwise
	 */
	protected String getSearchURI(String[] params) {
		String searchURI = null;
		
		if(params!=null && params.length>0 && !stringManager.isNumeric(params[0])){
			searchURI = params[0];
		}		
		return searchURI;
	}

	/**
	 * Returns current page in pagination from URI params
	 * @param params Controller params from URI
	 * @return Page number (starting from 0)
	 */
	protected int getPageResult(String[] params) {
		int pageResult = -1;
		
		//if params has correct size
		if(params!=null && params.length>0){
			//check if this implementation has the page number at 
			// first or second position
			if(params.length>1){
				if(stringManager.isNumeric(params[1])){
					//get page number
					pageResult = Integer.parseInt(params[1]);
				}				
			}
			else if(stringManager.isNumeric(params[0])){
				//get page number
				pageResult = Integer.parseInt(params[0]);
			}
			else{
				//no valid page -> first page
				pageResult = 0;
			}
		}
		else{
			//no page -> first page
			pageResult = 0;
		}
		return pageResult;
	}
	
	/**
	 * Implementations will return the list of Lessons matching the search URI and
	 * page number.
	 * @param searchURI Criteria to get the list of lessons
	 * @param pageResult Page number
	 * @return List of lessons matching the search criteria. 
	 */
	protected abstract List<Lesson> getLessons(String searchURI, int pageResult);
	
	/**
	 * Implementations will return search keyword from search URI
	 * @param searchURI to be converted into a valid search keyword
	 * @param lessons List of lessons matching this search URI
	 * @return search keyword
	 */
	protected abstract String getSearchKeyword(String searchURI, List<Lesson> lessons);	
	
	/**
	 * Implementations will save the corresponding stats depending of the search
	 * URI criteria
	 * @param visit to save stats to
	 * @param searchURI Search URI criteria
	 * @param lessons list of lessons matching the criteria
	 */
	protected abstract void saveStats(Visit visit, String searchURI, List<Lesson> lessons);	

}
