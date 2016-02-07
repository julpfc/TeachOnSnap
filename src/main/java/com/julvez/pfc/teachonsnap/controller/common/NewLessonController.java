package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.ErrorBean;
import com.julvez.pfc.teachonsnap.controller.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.controller.model.ErrorType;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.link.LinkService;
import com.julvez.pfc.teachonsnap.link.LinkServiceFactory;
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
import com.julvez.pfc.teachonsnap.media.model.MediaPropertyName;
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.StatsServiceFactory;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.TagServiceFactory;
import com.julvez.pfc.teachonsnap.upload.UploadService;
import com.julvez.pfc.teachonsnap.upload.UploadServiceFactory;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * NewLessonController extends {@link CommonController}.
 * <p>
 * Manages lesson edition from editLesson.jsp view.
 * <p>
 * Manages POST requests from editLesson.jsp view to create a
 * lesson.  
 * <p> 
 * Mapped in {@link ControllerURI#LESSON_NEW}
 */
public class NewLessonController extends CommonController {

	private static final long serialVersionUID = -1872355062206688138L;

	/** Provides the functionality to work with lessons. */
	private LessonService lessonService;
	
	/** Provides the functionality to work with tags */
	private TagService tagService;
	
	/** Provides the functionality to work with lesson's links. */
	private LinkService linkService;
	
	/** Provides the functionality to work with lesson's media files */
	private MediaFileService mediaFileService;
	
	/** Provides the functionality to upload files to the application. */
	private UploadService uploadService;

	/**
     * Default constructor
     */
    public NewLessonController() {
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
        	MediaFileServiceFactory.getService(),
        	UploadServiceFactory.getService());        
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
	 * @param mediaFileService Provides the functionality to work with lesson's media files
	 * @param uploadService Provides the functionality to upload files to the application.
	 */
	public NewLessonController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, LessonService lessonService, 
			TagService tagService, LinkService linkService, 
			MediaFileService mediaFileService, UploadService uploadService) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager);
		
		if(lessonService == null || tagService == null
				|| linkService == null || mediaFileService == null
				|| uploadService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}		
		this.lessonService = lessonService;
		this.tagService = tagService;		
		this.linkService = linkService;		
		this.mediaFileService = mediaFileService;		
		this.uploadService = uploadService;		
	}
	
	@Override
	protected void processController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {
		
		//check if user is an author and can create lessons
		if(user.isAuthor()){
			//store media file restrictionns for view
			long maxFileSize = properties.getNumericProperty(MediaPropertyName.MEDIAFILE_MAX_SIZE);
			requestManager.setAttribute(request, Attribute.LONG_MAX_UPLOAD_FILE_SIZE, maxFileSize);			
			List<String> acceptedFileTypes = mediaFileService.getAcceptedFileTypes();
			requestManager.setAttribute(request, Attribute.LIST_STRING_MEDIATYPE, acceptedFileTypes);
			
			//if creation request
			if(request.getMethod().equals("POST")){
				
				//get lesson from form request
				Lesson newLesson = getNewLesson(request);
	
				//if valid lesson
				if(newLesson!=null){
					//complete lesson data
					newLesson.setIdUser(user.getId());
					newLesson.setAuthor(user);
					//create lesson
					Lesson savedLesson = lessonService.createLesson(newLesson);
					
					//if success
					if(savedLesson!=null){
						newLesson = savedLesson;
	
						//get media file from request
						FileMetadata file = getSubmittedFile(request, user);
						
						//check if parameter is present
						if(file!=null){		
							//save media file
							int idMediaFile = mediaFileService.saveMediaFile(newLesson, file);
							if(idMediaFile>0){
								//success
								//remove temporary file
								uploadService.removeTemporaryFiles(user);
								setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.LESSON_CREATED);
							}
							else{
								//Error
								setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.LESSON_CREATED_WITH_MEDIA_ERROR);
							}
						}					
						
						//get new tags from parameter
						List<String> tags = requestManager.getParameterList(request, Parameter.LESSON_NEW_TAGS);
	
						//save new tags
						if(tags!=null){
							tagService.addLessonTags(newLesson, tags);
						}
						
						//get new source links from parameter
						List<String> sources = requestManager.getParameterList(request, Parameter.LESSON_NEW_SOURCES);
						//save source links
						if(sources!=null){
							linkService.addLessonSources(newLesson, sources);
						}
						//get new more info links from parameter				
						List<String> moreInfo = requestManager.getParameterList(request, Parameter.LESSON_NEW_MOREINFOS);
						//save more info links
						if(moreInfo!=null){
							linkService.addLessonMoreInfo(newLesson, moreInfo);
						}
						//notify to author
						lessonService.notifyLessonCreated(newLesson);
						
						//redirect to new lesson edit page
						response.sendRedirect(newLesson.getEditURL());
					}
					else{
						//error creating lesson -> reload page					
						setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE_DUPLICATE, ErrorMessageKey.SAVE_DUPLICATE_ERROR_LESSON));
						requestManager.setAttribute(request, Attribute.LESSON, newLesson);
						request.getRequestDispatcher("/WEB-INF/views/editLesson.jsp").forward(request, response);
					}
				}
				else{
					//incomplete lesson -> bad param
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				}
				
			}
			else{
				//no creation yet
				//dispatch to view
				request.getRequestDispatcher("/WEB-INF/views/editLesson.jsp").forward(request, response);
			}
		}
		else{
			//user is not an author
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}
	
	
	/**
	 * Returns a partial lesson object from request POST params.
	 * @param request Request
	 * @return partial lesson from view form, null if error
	 */
	private Lesson getNewLesson(HttpServletRequest request) {
		Lesson lesson = null;
		
		//get title from parameter
		String title = stringManager.unescapeHTML(requestManager.getParameter(request, Parameter.LESSON_NEW_TITLE));
		
		//check if parameter is present
		if(!stringManager.isEmpty(title)){			
			//get language from parameeter
			short idLanguage = (short)requestManager.getNumericParameter(request, Parameter.LESSON_NEW_LANGUAGE);
		
			//check if parameter is present
			if(idLanguage>0){
				//create basic lesson
				lesson = new Lesson();
				lesson.setTitle(title);
				lesson.setIdLanguage(idLanguage);					

				//get optional text parameter
				String text = stringManager.unescapeHTML(requestManager.getParameter(request, Parameter.LESSON_NEW_TEXT));
				
				//save text if present
				if(!stringManager.isEmpty(title)){
					lesson.setText(text);						
				}
			} 
		}		
		return lesson;
	}
	
	/**
	 * Get file metadata for the specified index on user's temporary repository
	 * @param request Request
	 * @param user User
	 * @return Filemetada for the specified index if it's a valid index, null otherwise
	 */
	private FileMetadata getSubmittedFile(HttpServletRequest request, User user) {
		FileMetadata file = null;
		
		//get index from parameter
		int mediaIndex =  requestManager.getNumericParameter(request, Parameter.LESSON_NEW_MEDIA_INDEX);
		
		if(user!=null && mediaIndex >= 0){
			//get file metadata form temporary repository
			file = uploadService.getTemporaryFile(user, mediaIndex);
		}		
		return file;
	}

}
