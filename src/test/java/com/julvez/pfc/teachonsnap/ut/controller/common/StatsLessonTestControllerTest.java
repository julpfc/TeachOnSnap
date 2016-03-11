package com.julvez.pfc.teachonsnap.ut.controller.common;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.common.StatsLessonTestController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.LessonTestService;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.ut.controller.CommonControllerTest;

public class StatsLessonTestControllerTest extends CommonControllerTest {

	@Mock
	private LessonService lessonService;
	
	@Mock
	private LessonTestService lessonTestService;
	
	@Mock
	private PageService pageService;
	
	private int idLessonTest = 1;
	
	@Override
	protected CommonController getController() {		
		return new StatsLessonTestController(userService, langService, 
				urlService, statsService, requestManager, logger, 
				properties, stringManager, lessonService, 
				lessonTestService, pageService);
	}
	
	@Test
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
		verify(requestManager).setAttribute(eq(request), eq(Attribute.USERTESTRANK), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_USERTESTRANKS), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LESSON), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LESSONTEST_QUESTIONS), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STATSTEST), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_BACKPAGE), anyObject());
		try { verify(dispatcher).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }	
	}
}
