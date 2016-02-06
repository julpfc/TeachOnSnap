package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.comment.CommentService;
import com.julvez.pfc.teachonsnap.comment.CommentServiceFactory;
import com.julvez.pfc.teachonsnap.comment.model.Comment;
import com.julvez.pfc.teachonsnap.comment.model.CommentPropertyName;
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
 * LessonController extends {@link CommonController}.
 * <p>
 * Manages lesson visualization from lesson.jsp view.
 * <p>
 * Manages POST requests from lesson.jsp view to edit lesson's details
 * and GET requests to un/follow the lesson.  
 * <p> 
 * Mapped in {@link ControllerURI#LESSON}
 */
public class LessonController extends CommonController {

	private static final long serialVersionUID = 8796999417669233949L;

	/** Pagination. Limit of comments per page. */
	private final int MAX_COMMENTS_PAGE;

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
	
	/** Provides the functionality to work with lesson's comments */
	private CommentService commentService;
	

	/**
     * Default constructor
     */
    public LessonController() {
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
        	MediaFileServiceFactory.getService(),
        	CommentServiceFactory.getService());        
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
	 * @param commentService Provides the functionality to work with lesson's comments
	 */
	public LessonController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, LessonService lessonService, 
			TagService tagService, LinkService linkService, 
			LessonTestService lessonTestService, MediaFileService mediaFileService, 
			CommentService commentService) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager);
		
		if(lessonService == null || tagService == null
				|| linkService == null || lessonTestService == null
				|| mediaFileService == null || commentService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}		
		this.lessonService = lessonService;
		this.tagService = tagService;		
		this.linkService = linkService;		
		this.lessonTestService = lessonTestService;		
		this.mediaFileService = mediaFileService;		
		this.commentService = commentService;		

		MAX_COMMENTS_PAGE = (int)properties.getNumericProperty(CommentPropertyName.MAX_PAGE_COMMENTS);
	}
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {

		//get controller params from URI
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		//check valid params
		if(params!=null && params.length>0){
			
			//get lessonURI from URI
			String lessonURI= params[0];

			//get lesson from URIname
			Lesson lesson = lessonService.getLessonFromURI(lessonURI);
			
			//if lesson found
			if(lesson!=null){
				boolean follow = false;

				//if logged in user
				if(user != null){
					//get follow lesson from parameter
					int idFollowLesson = requestManager.getNumericParameter(request, Parameter.FOLLOW_LESSON);
					
					//check if the parameter is present
					if(idFollowLesson > 0){
						follow = true;
					
						//get lesson from id
						Lesson followLesson = lessonService.getLesson(idFollowLesson);
						
						//check valid param
						if(followLesson != null && followLesson.getId() == lesson.getId()){
							//unfollow lesson
							User modUser = userService.followLesson(user, followLesson);
							
							//check result and reload page
							if(modUser != null){
								setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.USER_SAVED);
								user = modUser;
							}
							else{
								setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
							}		
							response.sendRedirect(request.getRequestURI());
						}
						else{
							//bad param
							response.sendError(HttpServletResponse.SC_BAD_REQUEST);
						}
					}
					//get unfollow lesson from parameter
					int idUnFollowLesson = requestManager.getNumericParameter(request, Parameter.UNFOLLOW_LESSON);
					
					//check if the parameter is present
					if(idUnFollowLesson > 0){
						follow = true;
					
						//get lesson from id
						Lesson unfollowLesson = lessonService.getLesson(idUnFollowLesson);
						
						//check valid param
						if(unfollowLesson != null && unfollowLesson.getId() == lesson.getId()){
							//unfollow lesson
							User modUser = userService.unfollowLesson(user, unfollowLesson);
							
							//check result and reload page
							if(modUser != null){
								setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.USER_SAVED);
								user = modUser;
							}
							else{
								setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
							}		
							response.sendRedirect(request.getRequestURI());
						}
						else{
							//bad param
							response.sendError(HttpServletResponse.SC_BAD_REQUEST);
						}
					}
				}	
				//if no follow request
				if(!follow){
					int pageResult = 0;
					boolean hasNextPage = false;
					
					//save stats if necessary
					if(visit != null && !visit.isViewedLesson(lesson.getId())){
						visit = statsService.saveLesson(visit, lesson);
						if(visit != null){
							requestManager.setSessionAttribute(request, SessionAttribute.VISIT, visit);
						}
					}
					
					//get page common information for the view
					List<Tag> tags = tagService.getLessonTags(lesson.getId());
					List<Link> moreInfoLinks = linkService.getMoreInfoLinks(lesson.getId());
					List<Link> sourceLinks = linkService.getSourceLinks(lesson.getId());
					List<MediaFile> medias = mediaFileService.getLessonMedias(lesson.getIdLessonMedia());
					
					//get test information if present
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
					//get comments page number
					if(params.length > 1 && stringManager.isNumeric(params[1])){
						pageResult = Integer.parseInt(params[1]);
					}
					//get comments page
					List<Comment> comments = commentService.getComments(lesson.getId(), pageResult);
					
					//check if is there a next page
					if(comments.size()>MAX_COMMENTS_PAGE){
						hasNextPage = true;
						comments.remove(MAX_COMMENTS_PAGE);
					}
					//get next page link
					String nextPage = null;
					if(hasNextPage){
						nextPage = urlService.getAbsoluteURL(ControllerURI.LESSON.toString() + lessonURI + "/" + (pageResult+MAX_COMMENTS_PAGE));
					}
					
					//get previous page link
					String prevPage = null;
					if(pageResult>0){
						prevPage = urlService.getAbsoluteURL(ControllerURI.LESSON.toString() + lessonURI);
						if(pageResult>MAX_COMMENTS_PAGE){
							prevPage = prevPage + "/" + (pageResult-MAX_COMMENTS_PAGE);
						}
					}
					//store page information for the view
					requestManager.setAttribute(request, Attribute.LESSON, lesson);
					requestManager.setAttribute(request, Attribute.LIST_MEDIAFILE_LESSONFILES, medias);
					requestManager.setAttribute(request, Attribute.LIST_TAG, tags);
					requestManager.setAttribute(request, Attribute.LIST_LINK_MOREINFO, moreInfoLinks);
					requestManager.setAttribute(request, Attribute.LIST_LINK_SOURCES, sourceLinks);
					requestManager.setAttribute(request, Attribute.LIST_COMMENTS, comments);
					requestManager.setAttribute(request, Attribute.STRING_NEXTPAGE, nextPage);
					requestManager.setAttribute(request, Attribute.STRING_PREVPAGE, prevPage);
	
					//dispatch to view
					request.getRequestDispatcher("/WEB-INF/views/lesson.jsp").forward(request, response);
				}
			}
			else{
				//lesson not found
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
		else{
			//No lessonURI -> bad params
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}				
	}

	@Override
	protected boolean isPrivateZone() {
		return false;
	}
}
