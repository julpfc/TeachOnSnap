package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.error.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.error.model.ErrorType;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.LessonTestService;
import com.julvez.pfc.teachonsnap.lessontest.LessonTestServiceFactory;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.user.model.User;

public class NewLessonTestController extends CommonController {

	private static final long serialVersionUID = 4029476444876195321L;

	private LessonService lessonService = LessonServiceFactory.getService();
	private LessonTestService lessonTestService = LessonTestServiceFactory.getService();
	private PageService pageService = PageServiceFactory.getService();

	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {

		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params!=null && params.length>0 && stringManager.isNumeric(params[0])){
			
			int idLesson = Integer.parseInt(params[0]);
			
			Lesson lesson = lessonService.getLesson(idLesson);
			
			if(lesson!=null){				
					
				if(userService.isAllowedForLesson(user, lesson)){
					if(request.getMethod().equals("POST")){
						String multipleChoice = requestManager.getParameter(request, Parameter.LESSON_TEST_MULTIPLECHOICE);
						int numAnswers = requestManager.getNumericParameter(request, Parameter.LESSON_TEST_NUMANSWERS);
						
						if(multipleChoice!=null){
							if(numAnswers>1 && numAnswers<10){
								LessonTest test = lessonTestService.getLessonTest(lesson);
								
								if(test == null){

									test = lessonTestService.createLessonTest(lesson,stringManager.isTrue(multipleChoice),numAnswers);
									
									if(test!=null){
										setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.TEST_CREATED);
										response.sendRedirect(test.getEditURL());
									}
									else{
										setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
										response.sendRedirect(lesson.getNewTestURL());
									}
									
								}
								else{
									setErrorSession(request, ErrorType.ERR_SAVE_DUPLICATE, ErrorMessageKey.SAVE_DUPLICATE_ERROR_TEST);
									response.sendRedirect(lesson.getEditURL());
								}
							}
							else{
								setErrorSession(request, ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.INVALID_INPUT_ERROR_TEST);
								response.sendRedirect(lesson.getNewTestURL());
							}	
						}
						else{
							response.sendRedirect(lesson.getEditURL());
						}
					}
					else{
						List<Page> pageStack = pageService.getNewLessonTestPageStack(lesson);
					
						requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
						
						requestManager.setAttribute(request, Attribute.LESSON, lesson);
								
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
