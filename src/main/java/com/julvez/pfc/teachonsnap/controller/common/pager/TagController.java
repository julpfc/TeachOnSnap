package com.julvez.pfc.teachonsnap.controller.common.pager;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.common.PagerController;
import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
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
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;

/**
 * TagController extends {@link PagerController}.
 * <p>
 * Paginates through the lessons which contains an specific tag. 
 * <p>
 * Mapped in {@link ControllerURI#TAG}
 */
public class TagController extends PagerController {

	private static final long serialVersionUID = -8636543880785473414L;

	/**
     * Default constructor
     */
    public TagController() {
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
	public TagController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, LessonService lessonService, 
			TagService tagService) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager,
				lessonService, tagService);
	}	
	
	@Override
	protected List<Lesson> getLessons(String searchURI, int pageResult) {
		List<Lesson> lessons = Collections.emptyList();
		
		//get tag from controller's URI
		Tag tag = tagService.getTag(stringManager.decodeURL(searchURI));				
		
		if(tag != null){
			//get lessons from tag (pagination)
			lessons = tagService.getLessonsFromTag(tag, pageResult);
		}
		
		return lessons;
	}

	@Override
	protected String getSearchKeyword(String searchURI, List<Lesson> lessons) {
		//Use the specified tag in controller's URI as Search keyword
		return stringManager.decodeURL(searchURI);
	}

	@Override
	protected void saveStats(Visit visit, String searchURI, List<Lesson> lessons) {
		if(visit != null && lessons != null){
			//get tag from controller's URI
			Tag tag = tagService.getTag(stringManager.decodeURL(searchURI));
		
			if(tag != null){
				//save searched tag stats
				statsService.saveTag(visit, tag);
			}
		}
	}
}
