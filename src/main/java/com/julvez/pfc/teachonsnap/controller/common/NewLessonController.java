package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.media.MediaFile;
import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.service.media.MediaFileService;
import com.julvez.pfc.teachonsnap.service.media.MediaFileServiceFactory;

public class NewLessonController extends CommonController {

	private static final long serialVersionUID = 7608540908435958036L;
	
	private LessonService lessonService = LessonServiceFactory.getService();
	private MediaFileService mediaFileService = MediaFileServiceFactory.getService();
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		User user = requestManager.getSessionUser(request);
		
		if(request.getMethod().equals("POST")){
			
			Lesson newLesson = requestManager.getParamNewLesson(request.getParameterMap());
			//TODO Le falta el idtest
			if(newLesson!=null){
				newLesson.setIdUser(user.getId());
				newLesson = lessonService.createLesson(newLesson);
				
				if(newLesson!=null){				
					FileMetadata file = requestManager.getSubmittedFile(request);
					
					if(file!=null){						
						MediaFile mediaFile = mediaFileService.saveMediaFile(newLesson,file);
						if(mediaFile==null){
							//TODO error
						}
					}
					else{
						//TODO no habia fichero
					}
					
					List<String> tags = requestManager.getParamNewTags(request.getParameterMap());
					newLesson = lessonService.addLessonTags(newLesson, tags);
					
					//TODO otros
				}
				else{
					//TODO error					
				}
			}
			else{
				//TODO Error
			}
			
			
			//TODO SI todo es correcto cargarse los temporales que no hemos usado
			
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
