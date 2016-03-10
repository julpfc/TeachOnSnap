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

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.common.StatsController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.LessonTestService;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.stats.model.StatsData;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.ut.controller.CommonControllerTest;

public class StatsControllerTest extends CommonControllerTest {

	@Mock
	private LessonService lessonService;
	
	@Mock
	private PageService pageService;
	
	@Mock
	private LessonTestService testService;
	
	@Override
	protected CommonController getController() {
		return new StatsController(userService, langService, urlService, 
				statsService, requestManager, logger, properties, 
				stringManager, lessonService, pageService, testService);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testProcessController() {
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		String[] params = {"1"};
		when(stringManager.isNumeric(anyString())).thenReturn(true);
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(params);
		testCommonController();
		try { verify(response, times(2)).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		
		when(request.getServletPath()).thenReturn("/");
		testCommonController();		
		try { verify(response, times(3)).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
				
		when(request.getServletPath()).thenReturn("/stats/lesson/year");
		testCommonController();		
		try { verify(response).sendError(HttpServletResponse.SC_FORBIDDEN); }
		catch (Throwable t) {t.printStackTrace();}
		
		Lesson lesson = mock(Lesson.class);
		when(lessonService.getLesson(anyInt())).thenReturn(lesson);
		when(userService.isAllowedForLesson(any(User.class), any(Lesson.class))).thenReturn(true);
		when(statsService.getCSVFromStats((List<StatsData>)anyObject())).thenReturn(EMPTY_STRING);
		testCommonController();	
		verify(statsService).getCSVFromStats((List<StatsData>)anyObject());
		verify(requestManager).downloadFile(eq(response), anyString(), anyString(), any(InputStream.class));
		
		
		when(requestManager.getNumericParameter(request, Parameter.EXPORT)).thenReturn(-1);		
		testCommonController();	
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_PAGE_STACK), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_STATS_TYPE), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_STATS_DATA), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_STATS_EXTRA), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_STATS_EXTRA_2), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_BACKPAGE), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_STATS_ADMIN), anyObject());
		try { verify(dispatcher).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }	
	}
}
