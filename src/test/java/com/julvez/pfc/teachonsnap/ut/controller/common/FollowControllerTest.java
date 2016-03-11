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
import com.julvez.pfc.teachonsnap.controller.common.FollowController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.ut.controller.CommonControllerTest;

public class FollowControllerTest extends CommonControllerTest {

	@Mock
	private LessonService lessonService;
	
	private int idUser = 1;
	
	@Override
	protected CommonController getController() {
		return new FollowController(userService, langService, urlService, 
				statsService, requestManager, logger, 
				properties, stringManager, lessonService);
	}
	
	@Test
	public void testProcessController() {
		testCommonController();
		verify(requestManager).setAttribute(eq(request), eq(Attribute.USER_PROFILE), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_USER), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_LESSON), anyObject());
				
		try { verify(dispatcher).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }	
		
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(new String[0]);
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		String[] params = {""+idUser};
		when(stringManager.isNumeric(anyString())).thenReturn(true);
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(params);
		
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_NOT_FOUND); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(userService.getUser(idUser)).thenReturn(user);
		
		testCommonController();
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.USER_PROFILE), anyObject());
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LIST_USER), anyObject());
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LIST_LESSON), anyObject());
		
		try { verify(dispatcher, times(2)).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }			
	}
}
