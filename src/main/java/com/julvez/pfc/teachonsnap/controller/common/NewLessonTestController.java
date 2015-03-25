package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;

public class NewLessonTestController extends CommonController {

	private static final long serialVersionUID = 7608540908435958036L;
	
//	private LessonService lessonService = LessonServiceFactory.getService();
//	private PageService pageService = PageServiceFactory.getService();
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
//		User user = requestManager.getSessionUser(request);
		
		if(request.getMethod().equals("POST")){
			
	/*		Lesson newLesson = requestManager.getParamNewLesson(request.getParameterMap());

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
						newLesson = lessonService.addLessonTags(newLesson, tags);
					}
					
					List<String> sources = requestManager.getParamNewSources(request.getParameterMap());

					if(sources!=null){
						newLesson = lessonService.addLessonSources(newLesson, sources);
					}
										
					List<String> moreInfo = requestManager.getParamNewMoreInfos(request.getParameterMap());

					if(moreInfo!=null){
						newLesson = lessonService.addLessonMoreInfo(newLesson, moreInfo);
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
		*/
			response.sendRedirect("/test/edit/");
		}
		else{
			
		//	List<Page> pageStack = pageService.getNewLessonTestPageStack(lesson);
		//	request.setAttribute(Attribute.LIST_PAGE_STACK.toString(), pageStack);
			
			request.getRequestDispatcher("/WEB-INF/views/newTest.jsp").forward(request, response);
		}
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}

}
