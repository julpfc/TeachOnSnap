package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.controller.model.ErrorType;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.LessonTestService;
import com.julvez.pfc.teachonsnap.lessontest.LessonTestServiceFactory;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.Question;
import com.julvez.pfc.teachonsnap.manager.json.JSONManager;
import com.julvez.pfc.teachonsnap.manager.json.JSONManagerFactory;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.user.model.User;

public class QuestionController extends CommonController {

	private static final long serialVersionUID = 4946929161852755647L;

	private LessonTestService lessonTestService = LessonTestServiceFactory.getService();
	private LessonService lessonService = LessonServiceFactory.getService();
	private PageService pageService = PageServiceFactory.getService();

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
					
					if(params.length>1 && stringManager.isNumeric(params[1])){
			
						int idQuestion = Integer.parseInt(params[1]);
					
						Question question = lessonTestService.getQuestion(idQuestion);
					
						if(question!=null && question.getIdLessonTest() == test.getId()){				
					
							//Guardamos cambios
							if(request.getMethod().equals("POST")){
								String json = stringManager.unescapeHTML(requestManager.getParameter(request,Parameter.JSON));
									
								if(json!=null){
									Question jQuestion = jsonManager.JSON2Object(json, Question.class);
									
									if(jQuestion!=null){										
										if(question.equals(jQuestion)){
											//No ha cambiado nada
											setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES);
											response.sendRedirect(test.getEditURL());
										}
										else{
											//Ha cambiado
											if(question.isEditedVersion(jQuestion)){
												if(jQuestion.isFullFilled()){
													lessonTestService.saveQuestion(jQuestion);
													setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.QUESTION_SAVED);
													response.sendRedirect(test.getEditURL());
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
								
								String export = requestManager.getParameter(request, Parameter.EXPORT);
								
								if(export != null){
									requestManager.setFileMetadataHeaders(response, "application/json", "question_"+question.getId()+".json");															 	 
									String outJSON = jsonManager.object2SimpleJSON(question);
									
									response.getOutputStream().write(outJSON.getBytes("UTF-8"));									
								}
								else{
															
									List<Page> pageStack = pageService.getEditQuestionPageStack(lesson, test, question);
									requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
	
									
									requestManager.setAttribute(request, Attribute.QUESTION, question);
									requestManager.setAttribute(request, Attribute.LESSONTEST_QUESTIONS, test);
											
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
							String json = stringManager.unescapeHTML(requestManager.getParameter(request, Parameter.JSON));
							
							if(json!=null){
								Question jQuestion = jsonManager.JSON2SimpleObject(json, Question.class);
								
								if(jQuestion!=null){
									
									if(jQuestion.isFullFilled() && jQuestion.getAnswers().size() == test.getNumAnswers()){
										jQuestion.setIdLessonTest(test.getId());
										jQuestion.setPriority((byte)test.getNumQuestions());
										test = lessonTestService.createQuestion(jQuestion);
										
										if(test != null){
											setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.QUESTION_CREATED);
											response.sendRedirect(test.getEditURL());
										}
										else{
											test = lessonTestService.getLessonTest(idLessonTest);
											//No se pudo crear la pregunta
											setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
											response.sendRedirect(test.getNewQuestionURL());
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
							requestManager.setAttribute(request, Attribute.LIST_PAGE_STACK, pageStack);
							
							requestManager.setAttribute(request, Attribute.LESSONTEST_QUESTIONS, test);							
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
