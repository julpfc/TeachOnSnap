package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.manager.mail.MailManagerFactory;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.media.MediaFile;
import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.service.link.LinkService;
import com.julvez.pfc.teachonsnap.service.link.LinkServiceFactory;
import com.julvez.pfc.teachonsnap.service.media.MediaFileService;
import com.julvez.pfc.teachonsnap.service.media.MediaFileServiceFactory;
import com.julvez.pfc.teachonsnap.service.tag.TagService;
import com.julvez.pfc.teachonsnap.service.tag.TagServiceFactory;
import com.julvez.pfc.teachonsnap.service.upload.UploadService;
import com.julvez.pfc.teachonsnap.service.upload.UploadServiceFactory;

public class NewLessonController extends CommonController {

	private static final long serialVersionUID = 7608540908435958036L;
	
	private LessonService lessonService = LessonServiceFactory.getService();
	private LinkService linkService = LinkServiceFactory.getService();
	private TagService tagService = TagServiceFactory.getService();
	private MediaFileService mediaFileService = MediaFileServiceFactory.getService();
	private UploadService uploadService = UploadServiceFactory.getService();
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		User user = requestManager.getSessionUser(request);
		
		if(request.getMethod().equals("POST")){
			
			Lesson newLesson = requestManager.getParamNewLesson(request.getParameterMap());

			if(newLesson!=null){
				newLesson.setIdUser(user.getId());
				newLesson = lessonService.createLesson(newLesson);
				
				if(newLesson!=null){				
					FileMetadata file = requestManager.getSubmittedFile(request);
					
					if(file!=null){						
						MediaFile mediaFile = mediaFileService.saveMediaFile(newLesson,file);
						if(mediaFile==null){
							//TODO error, habia fichero pero no hemos podido guardarlo
						}
					}					
					
					List<String> tags = requestManager.getParamNewTags(request.getParameterMap());

					if(tags!=null){
						newLesson = tagService.addLessonTags(newLesson, tags);
					}
					
					List<String> sources = requestManager.getParamNewSources(request.getParameterMap());

					if(sources!=null){
						newLesson = linkService.addLessonSources(newLesson, sources);
					}
										
					List<String> moreInfo = requestManager.getParamNewMoreInfos(request.getParameterMap());

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

}
