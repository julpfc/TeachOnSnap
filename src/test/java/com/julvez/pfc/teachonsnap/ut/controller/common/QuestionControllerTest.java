package com.julvez.pfc.teachonsnap.ut.controller.common;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.common.QuestionController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.LessonTestService;
import com.julvez.pfc.teachonsnap.lessontest.model.Answer;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.Question;
import com.julvez.pfc.teachonsnap.manager.json.JSONManager;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.ut.controller.CommonControllerTest;

public class QuestionControllerTest extends CommonControllerTest {
	
	@Mock
	private LessonService lessonService;
	
	@Mock
	private LessonTestService lessonTestService;
	
	@Mock
	private PageService pageService;

	@Mock
	private JSONManager jsonManager;

	
	private int idLessonTest = 1;
	private int idQuestion = 1;
	
	
	@Override
	protected CommonController getController() {
		return new QuestionController(userService, langService, 
				urlService, statsService, requestManager, logger, 
				properties, stringManager, lessonService, 
				lessonTestService, pageService, jsonManager);
	}
	
	@Override
	public void testProcessController() {
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		String[] params = {""+idLessonTest};
		when(stringManager.isNumeric(anyString())).thenReturn(true);
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(params);
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_NOT_FOUND); }
		catch (Throwable t) {t.printStackTrace();}
		
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTestService.getLessonTest(idLessonTest)).thenReturn(lessonTest);
		testCommonController();		
		try { verify(response).sendError(HttpServletResponse.SC_FORBIDDEN); }
		catch (Throwable t) {t.printStackTrace();}
		
		Lesson lesson = mock(Lesson.class);
		when(lessonService.getLesson(anyInt())).thenReturn(lesson);
		when(userService.isAllowedForLesson(user, lesson)).thenReturn(true);
		testCommonController();	
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_PAGE_STACK), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LESSONTEST_QUESTIONS), anyObject());
		try { verify(dispatcher).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }	

		when(request.getMethod()).thenReturn("POST");
		testCommonController();	
		try { verify(response, times(2)).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(stringManager.unescapeHTML(anyString())).thenReturn("data");
		testCommonController();	
		try { verify(response, times(3)).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		Question question = mock(Question.class);
		when(jsonManager.JSON2SimpleObject(anyString(), eq(Question.class))).thenReturn(question);
		testCommonController();	
		try { verify(response, times(4)).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(question.isFullFilled()).thenReturn(true);
		when(question.getAnswers()).thenReturn((List<Answer>)new ArrayList<Answer>());
		testCommonController();
		verify(lessonTestService).createQuestion(question);
		try { verify(response).sendRedirect(anyString()); } 
		catch (IOException e) { e.printStackTrace(); }
		
		String[] params2 = {""+idLessonTest, ""+idQuestion};
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(params2);
		testCommonController();
		try { verify(response, times(2)).sendError(HttpServletResponse.SC_NOT_FOUND); }
		catch (Throwable t) {t.printStackTrace();}
		
		
		when(lessonTestService.getQuestion(idQuestion)).thenReturn(question);
		when(request.getMethod()).thenReturn("GET");
		testCommonController();
		verify(requestManager).setAttribute(eq(request), eq(Attribute.QUESTION), anyObject());		
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LIST_PAGE_STACK), anyObject());		
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LESSONTEST_QUESTIONS), anyObject());
		try { verify(dispatcher, times(2)).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }	
		
		when(requestManager.getParameter(request, Parameter.EXPORT)).thenReturn(EMPTY_STRING);
		when(jsonManager.object2SimpleJSON(question)).thenReturn(EMPTY_STRING);
		testCommonController();
		verify(jsonManager).object2SimpleJSON(question);
		verify(requestManager).downloadFile(eq(response), anyString(), anyString(), any(InputStream.class));
				
		when(request.getMethod()).thenReturn("POST");
		testCommonController();	
		try { verify(response, times(5)).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}

		when(jsonManager.JSON2Object(anyString(), eq(Question.class))).thenReturn(question);
		when(question.isEditedVersion(question)).thenReturn(true);	
		testCommonController();		
		try { verify(response, times(2)).sendRedirect(anyString()); } 
		catch (IOException e) { e.printStackTrace(); }	
	}
}
