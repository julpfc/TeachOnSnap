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

public class QuestionController extends CommonController {

	private static final long serialVersionUID = 4946929161852755647L;

	private LessonTestService lessonTestService = LessonTestServiceFactory.getService();
	private LessonService lessonService = LessonServiceFactory.getService();
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
				
				User user = requestManager.getSessionUser(request);
				
				if(roleService.isAllowedForLesson(user, test.getIdLesson())){
					
					Lesson lesson = lessonService.getLesson(test.getIdLesson());
					
					if(params.length>1 && stringManager.isNumeric(params[1])){
			
						int idQuestion = Integer.parseInt(params[1]);
					
						Question question = lessonTestService.getQuestion(idQuestion);
					
						if(question!=null && question.getIdLessonTest() == test.getId()){				
					
							//Guardamos cambios
							if(request.getMethod().equals("POST")){
								String json = requestManager.getParamJSON(request);
									
								if(json!=null){
									System.out.println("json="+json);
										
									Question jQuestion = jsonManager.JSON2Object(json, Question.class);
										
									System.out.println("jsonQ="+jQuestion);
									
									if(jQuestion!=null){
										System.out.println("equals="+question.equals(jQuestion));
										if(question.equals(jQuestion)){
											//No ha cambiado nada
											requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES));
											response.sendRedirect(test.getEditURL());
										}
										else{
											//Ha cambiado
											if(question.isEditedVersion(jQuestion)){
												if(jQuestion.isFullFilled()){
													lessonTestService.saveQuestion(jQuestion);
													response.sendRedirect(test.getEditURL());
													//TODO Abrir popup o notificación de que se ha guardado correctamente en ambos casos
												}
												else{
													//No están todos los campos completos - Fallo validación JS
													response.sendError(HttpServletResponse.SC_BAD_REQUEST);
												}
											}
											else{
												//No concuerda con la estructura de la pregunta editada
												response.sendError(HttpServletResponse.SC_BAD_REQUEST);
											}										
										}
									}
									else{
										//No es válido el JSON
										response.sendError(HttpServletResponse.SC_BAD_REQUEST);
									}
								}
								else{
									//No recibimos json del formulario
									response.sendError(HttpServletResponse.SC_BAD_REQUEST);
								}
							}
							else{
								//Es un GET -> Cargamos los datos
								
								String export = requestManager.getParamExport(request);
								
								if(export != null){
									response.setContentType("application/json");
									response.setCharacterEncoding("UTF-8");
									response.setHeader(Header.CONTENT_DISPOSITION.toString(), "attachment; filename=\"question_"+question.getId()+".json\"");						 	 
									String outJSON = jsonManager.object2SimpleJSON(question);
									
									response.getOutputStream().write(outJSON.getBytes("UTF-8"));									
								}
								else{
									
									List<Page> pageStack = pageService.getEditQuestionPageStack(lesson, test, question);
									request.setAttribute(Attribute.LIST_PAGE_STACK.toString(), pageStack);

									
									request.setAttribute(Attribute.QUESTION.toString(), question);
									request.setAttribute(Attribute.LESSONTEST_QUESTIONS.toString(), test);
											
									request.getRequestDispatcher("/WEB-INF/views/question.jsp").forward(request, response);
								}
							}
						}				
						else{
							//El idQuestion de la URL no es válido
							response.sendError(HttpServletResponse.SC_NOT_FOUND);
						}
					}
					else if(params.length==1){
						//No viene idQuestion en la URL, hay que crear una nueva
						//Guardamos New Question
						if(request.getMethod().equals("POST")){
							String json = requestManager.getParamJSON(request);
							
							if(json!=null){
								System.out.println("json="+json);
									
								Question jQuestion = jsonManager.JSON2SimpleObject(json, Question.class);
									
								System.out.println("jsonQ="+jQuestion);
															
								if(jQuestion!=null){
									
									if(jQuestion.isFullFilled() && jQuestion.getAnswers().size() == test.getNumAnswers()){
										jQuestion.setIdLessonTest(test.getId());
										jQuestion.setPriority((byte)test.getNumQuestions());
										Question question = lessonTestService.createQuestion(jQuestion);
										
										if(question != null){
											response.sendRedirect(test.getEditURL());
											//TODO Abrir popup o notificación de que se ha guardado correctamente en ambos casos
										}
										else{
											//TODO error
										}
									}
									else{
										//No están todos los campos completos - Fallo validación JS
										response.sendError(HttpServletResponse.SC_BAD_REQUEST);
									}								
								}
								else{
									//No es válido el JSON
									response.sendError(HttpServletResponse.SC_BAD_REQUEST);
								}
							}
							else{
								//No recibimos json del formulario
								response.sendError(HttpServletResponse.SC_BAD_REQUEST);
							}
						}
						else{
							List<Page> pageStack = pageService.getNewQuestionPageStack(lesson, test);
							request.setAttribute(Attribute.LIST_PAGE_STACK.toString(), pageStack);
							
							request.setAttribute(Attribute.LESSONTEST_QUESTIONS.toString(), test);							
							request.getRequestDispatcher("/WEB-INF/views/question.jsp").forward(request, response);
						}
						
					}
					else{
						//No viene idQuestion en la URL, viene un dato erróneo
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					}
				}
				else{
					//El usuario no tiene permisos para editar la pregunta
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
				}
			}
			else{
				//El idLessonTest de la URL no es válido
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
		else{
			//No viene idLessonTest en la URL 
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}

}
