package com.julvez.pfc.teachonsnap.ut.controller;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.controller.ChangePWController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.model.User;

public class ChangePWControllerTest extends ControllerTest<ChangePWController> {

	@Mock
	private UserService userService;
	
	@Mock
	private StringManager stringManager;	
	
	private String token = "token";
	private String password = "password";
	
	@Override
	protected ChangePWController getController() {		
		return new ChangePWController(userService, langService, urlService, 
				requestManager, logger, stringManager);
	}
	
	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() {
		when(request.getMethod()).thenReturn("GET");
		
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(null);
		try {
			test.service(request, response);
			verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(new String[]{token});
		when(userService.getUserFromPasswordTemporaryToken(token)).thenReturn(null);
		try {
			test.service(request, response);
			verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		User user = mock(User.class);
		when(userService.getUserFromPasswordTemporaryToken(token)).thenReturn(user);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		try {
			test.service(request, response);
			verify(requestManager, times(4)).setAttribute(eq(request), any(Attribute.class), anyObject());
			verify(request).getRequestDispatcher(anyString());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() {
		when(request.getMethod()).thenReturn("POST");
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(null);
		try {
			test.service(request, response);
			verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(new String[]{token});
		when(userService.getUserFromPasswordTemporaryToken(token)).thenReturn(null);
		try {
			test.service(request, response);
			verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		User user = mock(User.class);
		when(userService.getUserFromPasswordTemporaryToken(token)).thenReturn(user);
		when(stringManager.isEmpty(anyString())).thenReturn(true);
		when(stringManager.isEmpty(password)).thenReturn(false);
		
		when(requestManager.getParameter(request, Parameter.NEW_PASSWORD)).thenReturn(BLANK_STRING);
		try {
			test.service(request, response);
			verify(requestManager, times(4)).setAttribute(eq(request), any(Attribute.class), anyObject());
			verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		when(requestManager.getParameter(request, Parameter.NEW_PASSWORD)).thenReturn(password);
		try {
			test.service(request, response);
			verify(requestManager, times(8)).setAttribute(eq(request), any(Attribute.class), anyObject());
			verify(userService).savePassword(user, password);
			verify(userService).deletePasswordTemporaryToken(user);
			verify(urlService).getHomeURL();
			verify(response).sendRedirect(anyString());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
