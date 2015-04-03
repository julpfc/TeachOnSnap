package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.manager.request.Attribute;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.error.ErrorBean;
import com.julvez.pfc.teachonsnap.model.error.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.model.error.ErrorType;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.test.LessonTest;
import com.julvez.pfc.teachonsnap.model.page.Page;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.model.visit.Visit;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.service.lesson.test.LessonTestService;
import com.julvez.pfc.teachonsnap.service.lesson.test.LessonTestServiceFactory;
import com.julvez.pfc.teachonsnap.service.page.PageService;
import com.julvez.pfc.teachonsnap.service.page.PageServiceFactory;

public class NewLessonTestController extends CommonController {

	private static final long serialVersionUID = 4029476444876195321L;

	private LessonService lessonService = LessonServiceFactory.getService();
	private LessonTestService lessonTestService = LessonTestServiceFactory.getService();
	private PageService pageService = PageServiceFactory.getService();

	private StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String[] params = requestManager.getControllerParams(request);
		
		if(params!=null && params.length>0 && stringManager.isNumeric(params[0])){
			
			int idLesson = Integer.parseInt(params[0]);
			
			Lesson lesson = lessonService.getLesson(idLesson);
			
			if(lesson!=null){				
				User user = null;
				Visit visit = requestManager.getSessionVisit(request);
				if(visit!=null) user = visit.getUser();
					
				if(roleService.isAllowedForLesson(user, lesson.getId())){
					if(request.getMethod().equals("POST")){
						String multipleChoice = requestManager.getParamMultiplechoiceTest(request);
						int numAnswers = requestManager.getParamNumAnswersTest(request);
						
						if(multipleChoice!=null){
							if(numAnswers>1 && numAnswers<10){
								LessonTest test = lessonTestService.getLessonTest(lesson);
								
								if(test == null){

									test = lessonTestService.createLessonTest(lesson,stringManager.isTrue(multipleChoice),numAnswers);
									
									if(test!=null){
										requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.TEST_CREATED));
										response.sendRedirect(test.getEditURL());
									}
									else{
										requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR));
										response.sendRedirect(lesson.getNewTestURL());
									}
									
								}
								else{
									requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_SAVE_DUPLICATE, ErrorMessageKey.SAVE_DUPLICATE_ERROR_TEST));
									response.sendRedirect(lesson.getEditURL());
								}
							}
							else{
								requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.INVALID_INPUT_ERROR_TEST));
								response.sendRedirect(lesson.getNewTestURL());
							}	
						}
						else{
							response.sendRedirect(lesson.getEditURL());
						}
					}
					else{
						List<Page> pageStack = pageService.getNewLessonTestPageStack(lesson);
					
						request.setAttribute(Attribute.LIST_PAGE_STACK.toString(), pageStack);
						
						request.setAttribute(Attribute.LESSON.toString(), lesson);
								
						request.getRequestDispatcher("/WEB-INF/views/newTest.jsp").forward(request, response);
					}				
				}
				else{
					//No puedes crear un test para esta lección
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
				}						
			}
			else{
				//No se encontró la lección
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
		else{
			//Parámetros inválidos
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}			
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}

}
