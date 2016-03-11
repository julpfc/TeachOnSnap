package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.comment.CommentService;
import com.julvez.pfc.teachonsnap.comment.CommentServiceFactory;
import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.controller.model.ErrorType;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
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
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * CommentController extends {@link CommonController}.
 * <p>
 * Manages comments creation/modification from lesson.jsp view. It also
 * allows an administrator to block/unblock the comment if necessary.
 * <p>
 * Manages POST requests from lesson.jsp view to create, edit or block a 
 * comment and GET requests to unblock a comment.  
 * <p> 
 * Mapped in {@link ControllerURI#LESSON_COMMENT}
 */
public class CommentController extends CommonController {

	private static final long serialVersionUID = 4894163535035375452L;

	/** Provides the functionality to work with lessons. */
	private LessonService lessonService;
	
	/** Provides the functionality to work with lessons. */
	private CommentService commentService;
	
	/**
     * Default constructor
     */
    public CommentController() {
    	this(UserServiceFactory.getService(),
        	LangServiceFactory.getService(),
        	URLServiceFactory.getService(),
        	StatsServiceFactory.getService(),
        	RequestManagerFactory.getManager(),
        	LogManagerFactory.getManager(),
        	PropertyManagerFactory.getManager(),
        	StringManagerFactory.getManager(),
        	LessonServiceFactory.getService(),
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
	 * @param commentService Provides the functionality to work with lesson's comments
	 */
	public CommentController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, LessonService lessonService, 
			CommentService commentService) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager);
		
		if(lessonService == null || commentService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}		
		this.lessonService = lessonService;
		this.commentService = commentService;		
	}

	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {
		
		//get controller params from URI
		String[] params = requestManager.splitParamsFromControllerURI(request); 
		
		String lessonURI = null;
		
		//get lessonURI from URI
		if(params != null && params.length>0){
			lessonURI = params[0];
		}
		
		//get lesson from URIname
		Lesson lesson = lessonService.getLessonFromURI(lessonURI);
		
		if(lesson != null){
			boolean error = false;
			//get comment id from parameter
			int commentID = requestManager.getNumericParameter(request, Parameter.LESSON_COMMENTID);
			//get isBanned parameter
			String isBanned = requestManager.getParameter(request,Parameter.LESSON_COMMENT_BAN);
			
			if(request.getMethod().equals("POST")){	
				//get comment body from parameter
				String commentBody = stringManager.unescapeHTML(requestManager.getParameter(request,Parameter.LESSON_COMMENT));
				//get edition flag from parameter
				boolean isEditing = requestManager.getBooleanParameter(request,Parameter.LESSON_COMMENT_EDIT);
				
				//if a message it's present
				if(commentBody != null){
					//if it's editing the comment body
					if(isEditing){
						//save comment body
						commentService.saveCommentBody(commentID, user.getId(), commentBody);
						//set message for view
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.COMMENT_SAVED);
					}
					else if(isBanned == null){
						//it's creating a new comment
						commentService.createComment(lesson.getId(), user.getId(), commentBody, commentID);
						//set message for view
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.COMMENT_CREATED);
					}
					else if(stringManager.isTrue(isBanned)){
						//if it's banning a comment
						commentService.blockComment(commentID, user, commentBody);
						//set message for view
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.COMMENT_BLOCKED);
					}
					else{
						error = true;
						//isEditing=false, isBanned=false -> bad params
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					}
				}
				else{
					error = true;
					//bad params
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				}
			}
			else{
				//GET method
				//check if isBanned parameter was present
				if(isBanned!= null){
					//check if user wants to unblock this comment
					if(!stringManager.isTrue(isBanned)){
						//unblock comment (if it's an adminsitrator)
						commentService.unblockComment(commentID, user);
						//set message for view
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.COMMENT_UNBLOCKED);
					}
					else{
						error = true;
						//It's not allowed banning via GET method
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					}
				}
				else{
					error = true;
					//bad params
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				}
			}
			if(!error){
				//redirect to lesson view
				response.sendRedirect(lesson.getURL());
			}
		}
		else{
			//lesson not found
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}
}
