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
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.lang.model.Language;
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
import com.julvez.pfc.teachonsnap.media.model.MediaPropertyName;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.StatsServiceFactory;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.TagServiceFactory;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
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
 * EditLessonController extends {@link CommonController}.
 * <p>
 * Manages lesson edition from editLesson.jsp view.
 * <p>
 * Manages POST requests from editLesson.jsp view to edit lesson's details
 * and GET requests to un/publish a lesson.  
 * <p> 
 * Mapped in {@link ControllerURI#LESSON_EDIT}
 */
public class EditLessonController extends CommonController {

	private static final long serialVersionUID = 5389130179972948086L;
	
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
	
	/** Provides the functionality to work with user pages (localized names & links) and the hierarchy (page stack) */
	private PageService pageService;
	
	/** Provides the functionality to upload files to the application. */
	private UploadService uploadService;

	/**
     * Default constructor
     */
    public EditLessonController() {
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
        	PageServiceFactory.getService(),
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
	 * @param lessonTestService Provides the functionality to work with lesson's tests
	 * @param mediaFileService Provides the functionality to work with lesson's media files
	 * @param pageService Provides the functionality to work with user pages (localized names & links) and the hierarchy (page stack)
	 * @param uploadService Provides the functionality to upload files to the application.
	 */
	public EditLessonController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, LessonService lessonService, 
			TagService tagService, LinkService linkService, 
			LessonTestService lessonTestService, MediaFileService mediaFileService, 
			PageService pageService, UploadService uploadService) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager);
		
		if(lessonService == null || tagService == null
				|| linkService == null || lessonTestService == null
				|| mediaFileService == null || pageService == null
				|| uploadService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}		
		this.lessonService = lessonService;
		this.tagService = tagService;		
		this.linkService = linkService;		
		this.lessonTestService = lessonTestService;		
		this.mediaFileService = mediaFileService;		
		this.pageService = pageService;		
		this.uploadService = uploadService;		
	}

	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {
		
		//get controller params from URI
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		//check valid params
		if(params!=null && params.length>0 && stringManager.isNumeric(params[0])){
			
			//get id lesson from URI
			int idLesson = Integer.parseInt(params[0]);
			
			//get lesson from id
			Lesson lesson = lessonService.getLesson(idLesson);
			
			//if lesson found ...
			if(lesson!= null){				
				//check if user is allowed to edit this lesson
				if(userService.isAllowedForLesson(user, lesson)){

					//get publish parameter
					String publish = requestManager.getParameter(request,Parameter.LESSON_PUBLISH);
					
					//check if the parameter is present
					if(publish!=null){						
						if(stringManager.isTrue(publish)){
							//publish
							lessonService.publish(lesson);
							setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.LESSON_PUBLISHED);							
						}				
						else{
							//unpublish
							lessonService.unpublish(lesson);
							setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.LESSON_UNPUBLISHED);
						}
						//return to previous page
						response.sendRedirect(requestManager.getSessionAttribute(request, SessionAttribute.LAST_PAGE));
					}
					else{
						//load current tags and links
						List<Tag> tags = tagService.getLessonTags(lesson.getId());
						List<Link> moreInfoLinks = linkService.getMoreInfoLinks(lesson.getId());
						List<Link> sourceLinks = linkService.getSourceLinks(lesson.getId());
						
						//if user wants to edit any detail
						if(request.getMethod().equals("POST")){
							Lesson modLesson = null;
							boolean success = true;
							boolean changes = false;
							
							//get new title from parameter
							String title = stringManager.unescapeHTML(requestManager.getParameter(request, Parameter.LESSON_NEW_TITLE));
							
							//check if data changed
							if(!stringManager.isEmpty(title) && !title.equals(lesson.getTitle())){
								
								//save new title
								modLesson = lessonService.saveLessonTitle(lesson, title);
								
								if(modLesson == null){
									//Error saving new title -> Duplicated
									setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE_DUPLICATE, ErrorMessageKey.SAVE_DUPLICATE_ERROR_LESSON));
									success = false;
								}
								else{
									//success
									lesson = modLesson;
									changes=true;
								}
							}
							
							if(success){
	
								//get new language from parameter
								short idLanguage = (short)requestManager.getNumericParameter(request, Parameter.LESSON_NEW_LANGUAGE);
								Language lang = langService.getLanguage(idLanguage);
								
								if(lang != null){
									//save new language
									modLesson = lessonService.saveLessonLanguage(lesson, lang);
									
									//check if data changed
									if(modLesson != null){
										lesson = modLesson;
										changes=true;
									}
								}
								
								//get new text from parameter
								String text = stringManager.unescapeHTML(requestManager.getParameter(request, Parameter.LESSON_NEW_TEXT));
								
								//save new text
								modLesson = lessonService.saveLessonText(lesson, text);
								
								//check if data changed
								if(modLesson != null){
									lesson = modLesson;
									changes = true;
								}
	
								//get new tags from parameter
								List<String> newTags = requestManager.getParameterList(request, Parameter.LESSON_NEW_TAGS);
	
								//save new tags
								if(tagService.saveLessonTags(lesson, tags, newTags)){
									//success
									changes = true;
									tags = tagService.getLessonTags(lesson.getId());									
								}
								
								//get new source links from parameter
								List<String> newSourcesLinks = requestManager.getParameterList(request, Parameter.LESSON_NEW_SOURCES);
	
								//save source links
								if(linkService.saveLessonSources(lesson, sourceLinks, newSourcesLinks)){
									//success
									changes = true;
									sourceLinks = linkService.getSourceLinks(lesson.getId());
								}
								
								//get new more info links from parameter										
								List<String> newMoreInfoLinks = requestManager.getParameterList(request, Parameter.LESSON_NEW_MOREINFOS);
	
								//save more info links
								if(linkService.saveLessonMoreInfo(lesson, moreInfoLinks, newMoreInfoLinks)){
									//success
									changes = true;
									moreInfoLinks = linkService.getMoreInfoLinks(lesson.getId());
								}
					
								//get remove media files parameter
								boolean remove = requestManager.getBooleanParameter(request, Parameter.LESSON_MEDIA_REMOVE);
								
								if(remove){
									//remove previous media files
									modLesson = mediaFileService.removeMediaFiles(lesson);
									
									//check if data changed
									if(modLesson != null){
										//success
										lesson = modLesson;
										changes = true;
									}
									else{
										//error removing medias
										setAttributeErrorBean(request,  new ErrorBean(ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR));
										success = false;
									}
								}	
								//save new media files (if previous were successfuly removed.
								if((remove && success && lesson.getIdLessonMedia() == -1) || (!remove && lesson.getIdLessonMedia() == -1)){
									//get submitted file metadata
									FileMetadata file = getSubmittedFile(request, user);
								
									if(file!=null){		
										//save media file
										int idMediaFile = mediaFileService.saveMediaFile(lesson, file);
										if(idMediaFile>0){
											//success
											//remove temporary reference for this file
											uploadService.removeTemporaryFiles(user);
											//update lesson
											lesson = lessonService.getLesson(lesson.getId());
											changes = true;
										}
										else{
											//Error saving file
											success = false;
											setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR));
										}
									}
									
								}							
	
								//prepare messages for view
								if(success){
									//if modified
									if(changes){
										//notify to lesson followers and author
										lessonService.notifyLessonModified(lesson);
										if(!lesson.isDraft()){
											//Update last published date											
											lessonService.republish(lesson);
											lesson = lessonService.getLesson(lesson.getId());
										}
										setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.LESSON_SAVED));
									}
									else{
										//no changes
										setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES));
									}
								}
							}							
						}
						
						//load rest of lesson information and page information
						List<MediaFile> medias = mediaFileService.getLessonMedias(lesson.getIdLessonMedia());
	
						List<Page> pageStack = pageService.getEditLessonPageStack(lesson);
						requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
										
						requestManager.setAttribute(request, Attribute.LESSON, lesson);
						requestManager.setAttribute(request, Attribute.LIST_MEDIAFILE_LESSONFILES, medias);
						requestManager.setAttribute(request, Attribute.LIST_TAG, tags);
						requestManager.setAttribute(request, Attribute.LIST_LINK_MOREINFO, moreInfoLinks);
						requestManager.setAttribute(request, Attribute.LIST_LINK_SOURCES, sourceLinks);
						
						LessonTest test = lessonTestService.getLessonTest(lesson);
						requestManager.setAttribute(request, Attribute.LESSONTEST_QUESTIONS, test);
						
						if(test != null){
							List<UserTestRank> testRanks = statsService.getTestRanks(test.getId());
							requestManager.setAttribute(request, Attribute.LIST_USERTESTRANKS, testRanks);
						}
						
						long maxFileSize = properties.getNumericProperty(MediaPropertyName.MEDIAFILE_MAX_SIZE);
						requestManager.setAttribute(request, Attribute.LONG_MAX_UPLOAD_FILE_SIZE, maxFileSize);
						
						List<String> acceptedFileTypes = mediaFileService.getAcceptedFileTypes();
						requestManager.setAttribute(request, Attribute.LIST_STRING_MEDIATYPE, acceptedFileTypes);
						
						//dispatch to view
						request.getRequestDispatcher("/WEB-INF/views/editLesson.jsp").forward(request, response);
					}
				}
				else{
					//user is not allowed for this lesson
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
				}
			}
			else {
				//lesson not found
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
