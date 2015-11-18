package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.error.model.ErrorBean;
import com.julvez.pfc.teachonsnap.error.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.error.model.ErrorType;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.link.LinkService;
import com.julvez.pfc.teachonsnap.link.LinkServiceFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.media.MediaFileService;
import com.julvez.pfc.teachonsnap.media.MediaFileServiceFactory;
import com.julvez.pfc.teachonsnap.media.model.MediaPropertyName;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.TagServiceFactory;
import com.julvez.pfc.teachonsnap.upload.UploadService;
import com.julvez.pfc.teachonsnap.upload.UploadServiceFactory;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.user.model.User;

public class NewLessonController extends CommonController {

	private static final long serialVersionUID = 7608540908435958036L;
	
	private LessonService lessonService = LessonServiceFactory.getService();
	private LinkService linkService = LinkServiceFactory.getService();
	private TagService tagService = TagServiceFactory.getService();
	private MediaFileService mediaFileService = MediaFileServiceFactory.getService();
	private UploadService uploadService = UploadServiceFactory.getService();

	private StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	protected void processController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {
		
		if(user.isAuthor()){
			int maxFileSize = properties.getNumericProperty(MediaPropertyName.MEDIAFILE_MAX_SIZE);
			requestManager.setAttribute(request, Attribute.INT_MAX_UPLOAD_FILE_SIZE, maxFileSize);
			
			List<String> acceptedFileTypes = mediaFileService.getAcceptedFileTypes();
			requestManager.setAttribute(request, Attribute.LIST_STRING_MEDIATYPE, acceptedFileTypes);
			
			if(request.getMethod().equals("POST")){
				
				Lesson newLesson = getNewLesson(request);
	
				if(newLesson!=null){
					newLesson.setIdUser(user.getId());
					newLesson.setAuthor(user);
					Lesson savedLesson = lessonService.createLesson(newLesson);
					
					if(savedLesson!=null){
						newLesson = savedLesson;
	
						FileMetadata file = getSubmittedFile(request, user);
						
						if(file!=null){		
							int idMediaFile = mediaFileService.saveMediaFile(newLesson, file);
							if(idMediaFile>0){
								//SI todo es correcto cargarse los temporales que no hemos usado
								uploadService.removeTemporaryFiles(user);
								setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.LESSON_CREATED);
							}
							else{
								//Error, habia fichero pero no hemos podido guardarlo
								setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.LESSON_CREATED_WITH_MEDIA_ERROR);
							}
						}					
						
						List<String> tags = requestManager.getParameterList(request, Parameter.LESSON_NEW_TAGS);
	
						if(tags!=null){
							tagService.addLessonTags(newLesson, tags);
						}
						
						List<String> sources = requestManager.getParameterList(request, Parameter.LESSON_NEW_SOURCES);
	
						if(sources!=null){
							linkService.addLessonSources(newLesson, sources);
						}
											
						List<String> moreInfo = requestManager.getParameterList(request, Parameter.LESSON_NEW_MOREINFOS);
	
						if(moreInfo!=null){
							linkService.addLessonMoreInfo(newLesson, moreInfo);
						}
						
						lessonService.notifyLessonCreated(newLesson);
						
						response.sendRedirect(newLesson.getEditURL());
					}
					else{
						//No se pudo crear la lesson					
						setAttributeErrorBean(request, new ErrorBean(ErrorType.ERR_SAVE_DUPLICATE, ErrorMessageKey.SAVE_DUPLICATE_ERROR_LESSON));
						requestManager.setAttribute(request, Attribute.LESSON, newLesson);
						request.getRequestDispatcher("/WEB-INF/views/editLesson.jsp").forward(request, response);
					}
				}
				else{
					//Error no se recuperaron params para el lesson
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				}
				
			}
			else{
				request.getRequestDispatcher("/WEB-INF/views/editLesson.jsp").forward(request, response);
			}
		}
		else{
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}
	
	
	private Lesson getNewLesson(HttpServletRequest request) {
		Lesson lesson = null;
		
		String title = requestManager.getParameter(request, Parameter.LESSON_NEW_TITLE);
		
		if(!stringManager.isEmpty(title)){
			//TODO HTML en los input text 
			//title = stringManager.escapeHTML(title);
			short idLanguage = (short)requestManager.getNumericParameter(request, Parameter.LESSON_NEW_LANGUAGE);
			
			if(idLanguage>0){
				lesson = new Lesson();
				lesson.setTitle(title);
				lesson.setIdLanguage(idLanguage);					

				String text = requestManager.getParameter(request, Parameter.LESSON_NEW_TEXT);
				
				if(!stringManager.isEmpty(title)){
					lesson.setText(text);						
				}
			} 
		}
		
		return lesson;
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
