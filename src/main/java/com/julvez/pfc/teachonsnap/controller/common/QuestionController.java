package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.manager.json.JSONManager;
import com.julvez.pfc.teachonsnap.manager.json.JSONManagerFactory;
import com.julvez.pfc.teachonsnap.manager.request.Attribute;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.lesson.test.LessonTest;
import com.julvez.pfc.teachonsnap.model.lesson.test.Question;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.service.lesson.test.LessonTestService;
import com.julvez.pfc.teachonsnap.service.lesson.test.LessonTestServiceFactory;

public class QuestionController extends CommonController {

	private static final long serialVersionUID = 4946929161852755647L;

	private LessonTestService lessonTestService = LessonTestServiceFactory.getService();

	private StringManager stringManager = StringManagerFactory.getManager();
	private JSONManager jsonManager = JSONManagerFactory.getManager();

		
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String[] params = requestManager.getControllerParams(request);
		
		if(params!=null && params.length>0 && stringManager.isNumeric(params[0])){
			
			int idQuestion = Integer.parseInt(params[0]);
			
			Question question = lessonTestService.getQuestion(idQuestion);
			
			if(question!=null){				
				
				LessonTest test = lessonTestService.getLessonTest(question.getIdLessonTest());
				
				if(test!=null){
					
					User user = requestManager.getSessionUser(request);
											
					if(roleService.isAllowedForLesson(user, test.getIdLesson())){
						
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
										response.sendRedirect(test.getEditURL());
										//TODO Abrir popup o notificación de que se ha guardado correctamente en ambos casos
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
								response.setHeader("Content-disposition", "attachment; filename=\"question_"+question.getId()+".json\"");						 	 
								String outJSON = jsonManager.object2SimpleJSON(question);
								
								response.getOutputStream().write(outJSON.getBytes("UTF-8"));									
							}
							else{
								request.setAttribute(Attribute.QUESTION.toString(), question);
								request.setAttribute(Attribute.LESSONTEST_QUESTIONS.toString(), test);
										
								request.getRequestDispatcher("/WEB-INF/views/question.jsp").forward(request, response);
							}
						}
					}
					else{
						//El usuario no tiene permisos para editar la pregunta
						response.sendError(HttpServletResponse.SC_FORBIDDEN);
					}						
				}
				else{
					//El test de la pregunta no es válido (No debe pasar salvo incoherencia en BBDD)
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}				
			}
			else{
				//El idQuestion de la URL no es válido
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
		else{
			//No viene idQuestion en la URL 
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}

}
