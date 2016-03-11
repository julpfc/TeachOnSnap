package com.julvez.pfc.teachonsnap.ut.controller.common;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.common.DraftsController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.ut.controller.CommonControllerTest;

public class DraftsControllerTest extends CommonControllerTest {

	@Mock
	private LessonService lessonService;

	private int idUser = 1;

	@Override
	protected CommonController getController() {
		return new DraftsController(userService, langService, urlService, 
				statsService, requestManager, logger, 
				properties, stringManager, lessonService);
	}
	
	@Test
	public void testProcessController() {
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(new String[]{"-1"});
		when(stringManager.isNumeric(anyString())).thenReturn(true);
		testCommonController();
		try { verify(response, times(2)).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(new String[]{""+idUser});
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_NOT_FOUND); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(userService.getUser(idUser)).thenReturn(user);		
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_FORBIDDEN); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(user.isAdmin()).thenReturn(true);
		testCommonController();
		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_NEXTPAGE), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_PREVPAGE), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_LESSON), anyObject());
				
		try { verify(dispatcher).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }	
	}
}
