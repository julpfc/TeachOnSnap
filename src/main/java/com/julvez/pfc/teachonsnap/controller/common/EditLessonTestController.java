package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.manager.json.JSONManager;
import com.julvez.pfc.teachonsnap.manager.json.JSONManagerFactory;
import com.julvez.pfc.teachonsnap.manager.request.Attribute;
import com.julvez.pfc.teachonsnap.manager.request.Header;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.error.ErrorBean;
import com.julvez.pfc.teachonsnap.model.error.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.model.error.ErrorType;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.test.LessonTest;
import com.julvez.pfc.teachonsnap.model.lesson.test.Question;
import com.julvez.pfc.teachonsnap.model.page.Page;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.service.lesson.test.LessonTestService;
import com.julvez.pfc.teachonsnap.service.lesson.test.LessonTestServiceFactory;
import com.julvez.pfc.teachonsnap.service.page.PageService;
import com.julvez.pfc.teachonsnap.service.page.PageServiceFactory;

public class EditLessonTestController extends CommonController {

	private static final long serialVersionUID = 7608540908435958036L;
	
	private LessonService lessonService = LessonServiceFactory.getService();
	private LessonTestService lessonTestService = LessonTestServiceFactory.getService();
	private PageService pageService = PageServiceFactory.getService();
	
	private StringManager stringManager = StringManagerFactory.getManager();
	private JSONManager jsonManager = JSONManagerFactory.getManager();
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String[] params = requestManager.getControllerParams(request);
		
		if(params!=null && params.length>0 && stringManager.isNumeric(params[0])){
			
			int idLessonTest = Integer.parseInt(params[0]);
			
			LessonTest test = lessonTestService.getLessonTest(idLessonTest);
			
			if(test!=null){
				Lesson lesson = lessonService.getLesson(test.getIdLesson());
				User user = requestManager.getSessionUser(request);
					
				if(roleService.isAllowedForLesson(user, test.getIdLesson())){
					String publish = requestManager.getParamPublishLessonTest(request);
					
					if(publish!=null){
						if(stringManager.isTrue(publish)){
							if(test.getNumQuestions()>0){
								lessonTestService.publish(test.getId());
								requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.TEST_PUBLISHED));
							}
							else{
								requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.INVALID_INPUT_ERROR_TEST_PUBLISH));
							}
						}				
						else{
							lessonTestService.unpublish(test.getId());
							requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.TEST_UNPUBLISHED));
						}
						
						response.sendRedirect(requestManager.getLastPage(request));
					}
					else {
						
						int deleteQuestionID = requestManager.getParamDeleteQuestionID(request);
						
						if(deleteQuestionID>0){							
							Question question = null;
							for(Question q:test.getQuestions()){
								if(q.getId() == deleteQuestionID){
									question = q;
									break;
								}
							}
							if(question!=null){
								if(!lessonTestService.removeQuestion(question)){
									//No se pudo borrar la pregunta									
									requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_REMOVE, ErrorMessageKey.REMOVE_ERROR));									
								}
								else {
									requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.QUESTION_REMOVED));
								}
								response.sendRedirect(test.getEditURL());
							}
							else{
								//No se encontró la pregunta
								response.sendError(HttpServletResponse.SC_NOT_FOUND);
							}
						}
						else{
							String deleteTest = requestManager.getParamDeleteTest(request);
							
							if(deleteTest!=null && stringManager.isTrue(deleteTest)){
								if(!lessonTestService.removeLessonTest(test)){
									//No se pudo borrar el test
									requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_REMOVE, ErrorMessageKey.REMOVE_ERROR));									
									response.sendRedirect(test.getEditURL());							
								}
								else {
									requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.TEST_REMOVED));
									response.sendRedirect(lesson.getEditURL());
								}
							}
							else{								
								int newPriority = requestManager.getParamQuestionPriority(request);
								int idQuestion = requestManager.getParamQuestionID(request);
								
								if(newPriority>=0 && idQuestion>0){
									Question question = null;
									for(Question q:test.getQuestions()){
										if(q.getId() == idQuestion){
											question = q;
											break;
										}
									}
									if(question!=null){										
										if(!lessonTestService.moveQuestion(question, newPriority)){
											//No se pudo mover la pregunta									
											requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR));									
										}
										else {
											requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.QUESTION_SAVED));
										}
										response.sendRedirect(test.getEditURL());
									}
									else{
										//No se encontró la pregunta
										response.sendError(HttpServletResponse.SC_NOT_FOUND);
									}
								}
								else{
									String export = requestManager.getParamExport(request);
									
									if(export != null){
										response.setContentType("application/json");
										response.setCharacterEncoding("UTF-8");
										response.setHeader(Header.CONTENT_DISPOSITION.toString(), "attachment; filename=\"test_"+test.getId()+".json\"");						 	 
										String outJSON = jsonManager.object2SimpleJSON(test);
										
										response.getOutputStream().write(outJSON.getBytes("UTF-8"));									
									}
									else{
										List<Page> pageStack = pageService.getEditLessonTestPageStack(lesson, test);
										request.setAttribute(Attribute.LIST_PAGE_STACK.toString(), pageStack);
										
										request.setAttribute(Attribute.LESSON.toString(), lesson);
										request.setAttribute(Attribute.LESSONTEST_QUESTIONS.toString(), test);
												
										request.getRequestDispatcher("/WEB-INF/views/editTest.jsp").forward(request, response);
									}
								}
							}
						}							 
					}				
				}
				else{
					//No puedes editar la lección
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
				}						
			}
			else{
				//No se encontró el test
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
