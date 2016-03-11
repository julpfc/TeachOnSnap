package com.julvez.pfc.teachonsnap.ut.controller.common;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.common.NewLessonTestController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.LessonTestService;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.ut.controller.CommonControllerTest;

public class NewLessonTestControllerTest extends CommonControllerTest {

	@Mock
	private LessonService lessonService;
	
	@Mock
	private LessonTestService lessonTestService;

	@Mock
	private PageService pageService;

	
	private int idLesson = 1;
	
	
	@Override
	protected CommonController getController() {
		return new NewLessonTestController(userService, langService, 
				urlService, statsService, requestManager, logger, 
				properties, stringManager, lessonService, 
				lessonTestService, pageService);
	}
	
	@Override
	public void testProcessController() {
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		String[] params = {""+idLesson};
		when(stringManager.isNumeric(anyString())).thenReturn(true);
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(params);
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_NOT_FOUND); }
		catch (Throwable t) {t.printStackTrace();}
		
		Lesson lesson = mock(Lesson.class);
		when(lessonService.getLesson(idLesson)).thenReturn(lesson);
		testCommonController();		
		try { verify(response).sendError(HttpServletResponse.SC_FORBIDDEN); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(userService.isAllowedForLesson(user, lesson)).thenReturn(true);	
		testCommonController();	
				
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_PAGE_STACK), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LESSON), anyObject());
		try { verify(dispatcher).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }	

		
		when(request.getMethod()).thenReturn("POST");
		testCommonController();	
		try { verify(response).sendRedirect(anyString()); } 
		catch (IOException e) { e.printStackTrace(); }		
		
		when(requestManager.getParameter(request, Parameter.LESSON_TEST_MULTIPLECHOICE)).thenReturn(EMPTY_STRING);
		testCommonController();
		try { verify(response, times(2)).sendRedirect(anyString()); } 
		catch (IOException e) { e.printStackTrace(); }	
		
		when(requestManager.getNumericParameter(request, Parameter.LESSON_TEST_NUMANSWERS)).thenReturn(2);
		testCommonController();
		try { verify(response, times(3)).sendRedirect(anyString()); } 
		catch (IOException e) { e.printStackTrace(); }
		
		
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTestService.getLessonTest(lesson)).thenReturn(lessonTest);
		testCommonController();
		verify(lessonTestService).createLessonTest(lesson, false, 2);
		try { verify(response, times(4)).sendRedirect(anyString()); } 
		catch (IOException e) { e.printStackTrace(); }
	}
}
