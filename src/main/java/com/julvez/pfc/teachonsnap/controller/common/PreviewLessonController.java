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
import com.julvez.pfc.teachonsnap.link.LinkService;
import com.julvez.pfc.teachonsnap.link.LinkServiceFactory;
import com.julvez.pfc.teachonsnap.link.model.Link;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.media.MediaFileService;
import com.julvez.pfc.teachonsnap.media.MediaFileServiceFactory;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.StatsServiceFactory;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.TagServiceFactory;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * PreviewLessonController extends {@link CommonController}.
 * <p>
 * Manages lesson draft visualization from lesson.jsp view.
 * <p>
 * Mapped in {@link ControllerURI#LESSON_PREVIEW}
 */
public class PreviewLessonController extends CommonController {

	private static final long serialVersionUID = -1341484060163304084L;

	/** Provides the functionality to work with lessons. */
	private LessonService lessonService;
	
	/** Provides the functionality to work with tags */
	private TagService tagService;
	
	/** Provides the functionality to work with lesson's links. */
	private LinkService linkService;
	
	/** Provides the functionality to work with lesson's tests */
	private LessonTestService lessonTestService;
	
	/** Provides the functionality to work with lesson's media files */
	private MediaFileService mediaFileService;
	

	/**
     * Default constructor
     */
    public PreviewLessonController() {
    	this(UserServiceFactory.getService(),
        	LangServiceFactory.getService(),
        	URLServiceFactory.getService(),
        	StatsServiceFactory.getService(),
        	RequestManagerFactory.getManager(),
        	LogManagerFactory.getManager(),
        	PropertyManagerFactory.getManager(),
        	StringManagerFactory.getManager(),
        	LessonServiceFactory.getService(),
        	TagServiceFactory.getService(),
        	LinkServiceFactory.getService(),
        	LessonTestServiceFactory.getService(),
        	MediaFileServiceFactory.getService());        
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
	 * @param linkService Provides the functionality to work with lesson's links.
	 * @param lessonTestService Provides the functionality to work with lesson's tests
	 * @param mediaFileService Provides the functionality to work with lesson's media files
	 */
	public PreviewLessonController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, LessonService lessonService, 
			TagService tagService, LinkService linkService, 
			LessonTestService lessonTestService, MediaFileService mediaFileService) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager);
		
		if(lessonService == null || tagService == null
				|| linkService == null || lessonTestService == null
				|| mediaFileService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}		
		this.lessonService = lessonService;
		this.tagService = tagService;		
		this.linkService = linkService;		
		this.lessonTestService = lessonTestService;		
		this.mediaFileService = mediaFileService;		
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
				//load common page information for view
				List<Tag> tags = tagService.getLessonTags(lesson.getId());
				List<Link> moreInfoLinks = linkService.getMoreInfoLinks(lesson.getId());
				List<Link> sourceLinks = linkService.getSourceLinks(lesson.getId());
				List<MediaFile> medias = mediaFileService.getLessonMedias(lesson.getIdLessonMedia());
				
				if(lesson.isTestAvailable()){
					LessonTest test = lessonTestService.getLessonTest(lesson);
					requestManager.setAttribute(request, Attribute.LESSONTEST_QUESTIONS, test);
					
					if(visit!= null && visit.getUser()!=null){
						UserTestRank testRank = statsService.getUserTestRank(test.getId(), visit.getUser().getId());			
						requestManager.setAttribute(request, Attribute.USERTESTRANK, testRank);
					}
					List<UserTestRank> testRanks = statsService.getTestRanks(test.getId());
					requestManager.setAttribute(request, Attribute.LIST_USERTESTRANKS, testRanks);
				}
				
				requestManager.setAttribute(request, Attribute.LESSON, lesson);
				requestManager.setAttribute(request, Attribute.LIST_MEDIAFILE_LESSONFILES, medias);
				requestManager.setAttribute(request, Attribute.LIST_TAG, tags);
				requestManager.setAttribute(request, Attribute.LIST_LINK_MOREINFO, moreInfoLinks);
				requestManager.setAttribute(request, Attribute.LIST_LINK_SOURCES, sourceLinks);

				//dispatch to view
				request.getRequestDispatcher("/WEB-INF/views/lesson.jsp").forward(request, response);
			}
			else{
				//not valid URI -> 404
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
		else{
			//No lessonURI -> error 400
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}				
	}

	@Override
	protected boolean isPrivateZone() {
		return false;
	}

}
