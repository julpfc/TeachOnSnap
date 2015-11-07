package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.link.LinkService;
import com.julvez.pfc.teachonsnap.link.LinkServiceFactory;
import com.julvez.pfc.teachonsnap.manager.mail.MailManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.media.MediaFileService;
import com.julvez.pfc.teachonsnap.media.MediaFileServiceFactory;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.TagServiceFactory;
import com.julvez.pfc.teachonsnap.upload.UploadService;
import com.julvez.pfc.teachonsnap.upload.UploadServiceFactory;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.visit.model.Visit;

public class NewLessonController extends CommonController {

	private static final long serialVersionUID = 7608540908435958036L;
	
	private LessonService lessonService = LessonServiceFactory.getService();
	private LinkService linkService = LinkServiceFactory.getService();
	private TagService tagService = TagServiceFactory.getService();
	private MediaFileService mediaFileService = MediaFileServiceFactory.getService();
	private UploadService uploadService = UploadServiceFactory.getService();

	private StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		User user = null;
		Visit visit = requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class);
		if(visit!=null) user = visit.getUser();
		
		if(request.getMethod().equals("POST")){
			
			Lesson newLesson = getNewLesson(request);

			if(newLesson!=null){
				newLesson.setIdUser(user.getId());
				newLesson.setAuthor(user);
				newLesson = lessonService.createLesson(newLesson);
				
				if(newLesson!=null){				
					FileMetadata file = getSubmittedFile(request);
					
					if(file!=null){						
						MediaFile mediaFile = mediaFileService.saveMediaFile(newLesson,file);
						if(mediaFile==null){
							//TODO error, habia fichero pero no hemos podido guardarlo
						}
					}					
					
					List<String> tags = requestManager.getParameterList(request, Parameter.LESSON_NEW_TAGS);

					if(tags!=null){
						newLesson = tagService.addLessonTags(newLesson, tags);
					}
					
					List<String> sources = requestManager.getParameterList(request, Parameter.LESSON_NEW_SOURCES);

					if(sources!=null){
						newLesson = linkService.addLessonSources(newLesson, sources);
					}
										
					List<String> moreInfo = requestManager.getParameterList(request, Parameter.LESSON_NEW_MOREINFOS);

					if(moreInfo!=null){
						newLesson = linkService.addLessonMoreInfo(newLesson, moreInfo);
					}
					
					
				}
				else{
					//TODO error no se pudo crear la lesson				
				}
			}
			else{
				//TODO Error no se recuperaron params para el lesson
			}
			
			
			//TODO SI todo es correcto cargarse los temporales que no hemos usado
			uploadService.removeTemporaryFiles(user);
			
			//TODO Mandar el mail bien, sistema de notificaciones en un servicio apra los seguimientos etc
			MailManagerFactory.getManager().send(user.getEmail(), "Lesson " + newLesson.getId() + " creada", newLesson.toString());
			response.sendRedirect(newLesson.getEditURL());
		}
		else{
			request.getRequestDispatcher("/WEB-INF/views/newLesson.jsp").forward(request, response);
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
	
	private FileMetadata getSubmittedFile(HttpServletRequest request) {
		FileMetadata file = null;
		User user = null;
		Visit visit = requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class);
		
		if(visit!=null) user=visit.getUser();		
		
		String attach = requestManager.getParameter(request, Parameter.LESSON_NEW_FILE_ATTACH);
		
		if(user!=null && !stringManager.isEmpty(attach)){
			MediaType mediaType = MediaType.valueOf(attach.toUpperCase());
			String index = null;
			
			if(mediaType!=null){
				switch (mediaType) {
				case VIDEO:
					index =  requestManager.getParameter(request, Parameter.LESSON_NEW_VIDEO_INDEX);
					break;
				case AUDIO:
					index =  requestManager.getParameter(request, Parameter.LESSON_NEW_AUDIO_INDEX);
					break;
				}
				
				if(stringManager.isNumeric(index)){
					int mediaIndex = Integer.parseInt(index);
										
					if(mediaIndex>=0){
						file = uploadService.getTemporaryFile(user, mediaType , mediaIndex);
					}
				}
			}
		}		
		
		return file;
	}

}
