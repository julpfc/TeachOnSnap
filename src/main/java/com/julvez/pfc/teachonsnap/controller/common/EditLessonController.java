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
import com.julvez.pfc.teachonsnap.media.MediaFileService;
import com.julvez.pfc.teachonsnap.media.MediaFileServiceFactory;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.media.model.MediaPropertyName;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.TagServiceFactory;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.upload.UploadService;
import com.julvez.pfc.teachonsnap.upload.UploadServiceFactory;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.user.model.User;

public class EditLessonController extends CommonController {

	private static final long serialVersionUID = 7608540908435958036L;
	
	private LessonService lessonService = LessonServiceFactory.getService();
	private TagService tagService = TagServiceFactory.getService();
	private LinkService linkService = LinkServiceFactory.getService();
	private LessonTestService lessonTestService = LessonTestServiceFactory.getService();
	private MediaFileService mediaFileService = MediaFileServiceFactory.getService();
	private PageService pageService = PageServiceFactory.getService();
	private UploadService uploadService = UploadServiceFactory.getService();


	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {
		
		
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params!=null && params.length>0 && stringManager.isNumeric(params[0])){
			
			int idLesson = Integer.parseInt(params[0]);
			
			Lesson lesson = lessonService.getLesson(idLesson);
			
			if(lesson!= null){				
				
				if(userService.isAllowedForLesson(user, lesson)){

					String publish = requestManager.getParameter(request,Parameter.LESSON_PUBLISH);
					
					if(publish!=null){
						if(stringManager.isTrue(publish)){
							lessonService.publish(lesson);
							setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.LESSON_PUBLISHED);							
						}				
						else{
							lessonService.unpublish(lesson);
							setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.LESSON_UNPUBLISHED);
						}
						response.sendRedirect(requestManager.getSessionAttribute(request, SessionAttribute.LAST_PAGE));
					}
					else{
					
						List<Tag> tags = tagService.getLessonTags(lesson.getId());
						List<Link> moreInfoLinks = linkService.getMoreInfoLinks(lesson.getId());
						List<Link> sourceLinks = linkService.getSourceLinks(lesson.getId());
						
						if(request.getMethod().equals("POST")){
							Lesson modLesson = null;
							boolean success = true;
							boolean changes = false;
							
							String title = stringManager.unescapeHTML(requestManager.getParameter(request, Parameter.LESSON_NEW_TITLE));
							
							if(!stringManager.isEmpty(title) && !title.equals(lesson.getTitle())){
								
								modLesson = lessonService.saveLessonTitle(lesson, title);
								
								if(modLesson == null){
									// No se pudo guardar -> Duplicado
									setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE_DUPLICATE, ErrorMessageKey.SAVE_DUPLICATE_ERROR_LESSON));
									success = false;
								}
								else{
									lesson = modLesson;
									changes=true;
								}
							}
							
							if(success){
	
								short idLanguage = (short)requestManager.getNumericParameter(request, Parameter.LESSON_NEW_LANGUAGE);
								Language lang = langService.getLanguage(idLanguage);
								
								if(lang != null){
									modLesson = lessonService.saveLessonLanguage(lesson, lang);
										
									if(modLesson != null){
										lesson = modLesson;
										changes=true;
									}
								}
								
								String text = stringManager.unescapeHTML(requestManager.getParameter(request, Parameter.LESSON_NEW_TEXT));
								
								modLesson = lessonService.saveLessonText(lesson, text);
								
								if(modLesson != null){
									lesson = modLesson;
									changes = true;
								}
	
								List<String> newTags = requestManager.getParameterList(request, Parameter.LESSON_NEW_TAGS);
	
								if(tagService.saveLessonTags(lesson, tags, newTags)){
									changes = true;
									tags = tagService.getLessonTags(lesson.getId());									
								}
														
								List<String> newSourcesLinks = requestManager.getParameterList(request, Parameter.LESSON_NEW_SOURCES);
	
								if(linkService.saveLessonSources(lesson, sourceLinks, newSourcesLinks)){
									changes = true;
									sourceLinks = linkService.getSourceLinks(lesson.getId());
								}
																		
								List<String> newMoreInfoLinks = requestManager.getParameterList(request, Parameter.LESSON_NEW_MOREINFOS);
	
								if(linkService.saveLessonMoreInfo(lesson, moreInfoLinks, newMoreInfoLinks)){
									changes = true;
									moreInfoLinks = linkService.getMoreInfoLinks(lesson.getId());
								}
					
								boolean remove = requestManager.getBooleanParameter(request, Parameter.LESSON_MEDIA_REMOVE);
								
								if(remove){
									//eliminar el viejo
									modLesson = mediaFileService.removeMediaFiles(lesson);
									
									if(modLesson != null){
										lesson = modLesson;
										changes = true;
									}
									else{
										setAttributeErrorBean(request,  new ErrorBean(ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR));
										success = false;
									}
								}	
								//Y coger el nuevo (sólo si se ha eliminado el viejo)
								if((remove && success && lesson.getIdLessonMedia() == -1) || (!remove && lesson.getIdLessonMedia() == -1)){
									
									FileMetadata file = getSubmittedFile(request, user);
								
									if(file!=null){		
										int idMediaFile = mediaFileService.saveMediaFile(lesson, file);
										if(idMediaFile>0){
											//SI todo es correcto cargarse los temporales que no hemos usado
											uploadService.removeTemporaryFiles(user);
											lesson = lessonService.getLesson(lesson.getId());
											changes = true;
										}
										else{
											//Error, habia fichero pero no hemos podido guardarlo
											success = false;
											setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR));
										}
									}
									
								}							
	
							
								if(success){
									if(changes){
										lessonService.notifyLessonModified(lesson);
										if(!lesson.isDraft()){
											//Actualizamos fecha de publicación si se modifica (republicamos)											
											lessonService.republish(lesson);
											lesson = lessonService.getLesson(lesson.getId());
										}
										setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.LESSON_SAVED));
									}
									else{
										setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES));
									}
								}
							}
							
						}
						
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
						
						request.getRequestDispatcher("/WEB-INF/views/editLesson.jsp").forward(request, response);
					}
				}
				else{
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
				}
			}
			else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}			
		}
		else{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
		
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}

	private FileMetadata getSubmittedFile(HttpServletRequest request, User user) {
		FileMetadata file = null;
		
		int mediaIndex =  requestManager.getNumericParameter(request, Parameter.LESSON_NEW_MEDIA_INDEX);
		
		if(user!=null && mediaIndex >= 0){
			file = uploadService.getTemporaryFile(user, mediaIndex);
		}		
		
		return file;
	}

}
