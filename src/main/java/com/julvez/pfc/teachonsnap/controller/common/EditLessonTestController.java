package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.error.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.error.model.ErrorType;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.test.LessonTestService;
import com.julvez.pfc.teachonsnap.lesson.test.LessonTestServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.test.model.LessonTest;
import com.julvez.pfc.teachonsnap.lesson.test.model.Question;
import com.julvez.pfc.teachonsnap.manager.json.JSONManager;
import com.julvez.pfc.teachonsnap.manager.json.JSONManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.user.model.User;

public class EditLessonTestController extends CommonController {

	private static final long serialVersionUID = 7608540908435958036L;
	
	private LessonService lessonService = LessonServiceFactory.getService();
	private LessonTestService lessonTestService = LessonTestServiceFactory.getService();
	private PageService pageService = PageServiceFactory.getService();
	
	private StringManager stringManager = StringManagerFactory.getManager();
	private JSONManager jsonManager = JSONManagerFactory.getManager();
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {

		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params!=null && params.length>0 && stringManager.isNumeric(params[0])){
			
			int idLessonTest = Integer.parseInt(params[0]);
			
			LessonTest test = lessonTestService.getLessonTest(idLessonTest);
			
			if(test!=null){
				Lesson lesson = lessonService.getLesson(test.getIdLesson());
					
				if(userService.isAllowedForLesson(user, lesson)){
					String publish = requestManager.getParameter(request,Parameter.LESSON_TEST_PUBLISH);
					
					if(publish!=null){
						if(stringManager.isTrue(publish)){
							if(test.getNumQuestions()>0){
								lessonTestService.publish(test.getId());
								setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.TEST_PUBLISHED);
							}
							else{
								setErrorSession(request, ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.INVALID_INPUT_ERROR_TEST_PUBLISH);
							}
						}				
						else{
							lessonTestService.unpublish(test.getId());
							setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.TEST_UNPUBLISHED);
						}
						
						response.sendRedirect(requestManager.getSessionAttribute(request, SessionAttribute.LAST_PAGE));
					}
					else {
						
						int deleteQuestionID = requestManager.getNumericParameter(request, Parameter.QUESTIONID_DELETE);
						
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
									setErrorSession(request, ErrorType.ERR_REMOVE, ErrorMessageKey.REMOVE_ERROR);									
								}
								else {
									setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.QUESTION_REMOVED);
								}
								response.sendRedirect(test.getEditURL());
							}
							else{
								//No se encontró la pregunta
								response.sendError(HttpServletResponse.SC_NOT_FOUND);
							}
						}
						else{
							String deleteTest = requestManager.getParameter(request, Parameter.LESSON_TEST_DELETE);
							
							if(deleteTest!=null && stringManager.isTrue(deleteTest)){
								if(!lessonTestService.removeLessonTest(test)){
									//No se pudo borrar el test
									setErrorSession(request, ErrorType.ERR_REMOVE, ErrorMessageKey.REMOVE_ERROR);									
									response.sendRedirect(test.getEditURL());							
								}
								else {
									setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.TEST_REMOVED);
									response.sendRedirect(lesson.getEditURL());
								}
							}
							else{								
								int newPriority = requestManager.getNumericParameter(request, Parameter.QUESTION_PRIORITY);
								int idQuestion = requestManager.getNumericParameter(request, Parameter.QUESTIONID);
								
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
											setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);									
										}
										else {
											setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.QUESTION_SAVED);
										}
										response.sendRedirect(test.getEditURL());
									}
									else{
										//No se encontró la pregunta
										response.sendError(HttpServletResponse.SC_NOT_FOUND);
									}
								}
								else{
									String export = requestManager.getParameter(request, Parameter.EXPORT);
									
									if(export != null){
										requestManager.setFileMetadataHeaders(response, "application/json", "test_"+test.getId()+".json\"");
																 	 
										String outJSON = jsonManager.object2SimpleJSON(test);
										
										response.getOutputStream().write(outJSON.getBytes("UTF-8"));									
									}
									else{
										List<Page> pageStack = pageService.getEditLessonTestPageStack(lesson, test);
										requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
										
										requestManager.setAttribute(request, Attribute.LESSON, lesson);
										requestManager.setAttribute(request, Attribute.LESSONTEST_QUESTIONS, test);
												
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
