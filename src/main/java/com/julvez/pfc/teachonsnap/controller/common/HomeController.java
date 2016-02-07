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
 * HomeController extends {@link CommonController}. 
 * <p>
 * Recovers information to be shown at home page
 * <p>
 * It's a view controller, processes the request and loads 
 * common information for the home.jsp view.
 * <p>
 * Mapped in {@link ControllerURI#HOME}  
 */
public class HomeController extends CommonController {

	private static final long serialVersionUID = 1L;

	/** Pagination. Limit of lessons per page. */
	private final int MAX_LESSONS_PER_PAGE; 

	/** Provides the functionality to work with lessons. */
	private LessonService lessonService;
	
	/** Provides the functionality to work with tags */
	private TagService tagService;
		

	/**
     * Default constructor
     */
    public HomeController() {
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
	 * @param tagService Provides the functionality to work with tags
	 */
	public HomeController(UserService userService,
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
	protected void processController(HttpServletRequest request,
			HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {
		
		//get last published lessons
		List<Lesson> lastLessons = lessonService.getLastLessons(0);
		
		//check if is there a next page and get link
		if(lastLessons.size()>MAX_LESSONS_PER_PAGE){			
			lastLessons.remove(MAX_LESSONS_PER_PAGE);			
			String nextPage = urlService.getAbsoluteURL(ControllerURI.LESSONS_BY_LAST.toString() + MAX_LESSONS_PER_PAGE);			
			requestManager.setAttribute(request, Attribute.STRING_NEXTPAGE, nextPage);
		}

		//load clouds of tags
		List<CloudTag> tagUseCloudTags = tagService.getTagUseCloudTags();
		List<CloudTag> lessonCloudTags = tagService.getLessonViewCloudTags();
		List<CloudTag> authorCloudTags = tagService.getAuthorCloudTags();
		requestManager.setAttribute(request, Attribute.LIST_CLOUDTAG_AUTHOR, authorCloudTags);
		requestManager.setAttribute(request, Attribute.LIST_CLOUDTAG_TAG_USE, tagUseCloudTags);
		requestManager.setAttribute(request, Attribute.LIST_CLOUDTAG_LESSON, lessonCloudTags);

		//load last published lessons
		requestManager.setAttribute(request, Attribute.LIST_LESSON, lastLessons);
		
		//dispatch to view
		request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

	@Override
	protected boolean isPrivateZone() {		
		return false;
	}
}
