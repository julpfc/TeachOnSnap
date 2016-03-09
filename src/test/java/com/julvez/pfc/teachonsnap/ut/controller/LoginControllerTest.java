package com.julvez.pfc.teachonsnap.ut.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.controller.LoginController;
import com.julvez.pfc.teachonsnap.controller.model.ErrorBean;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.model.User;

public class LoginControllerTest extends ControllerTest<LoginController> {

	@Mock
	private UserService userService;
	
	@Mock
	private StatsService statsService;
	
	@Mock
	private StringManager stringManager;
	
	private String url = "url";
	private String password = "secret";
	private String email = "name@teachonsnap.com";
	
	
	@Override
	protected LoginController getController() {
		return new LoginController(userService, langService, urlService, 
				statsService, requestManager, stringManager);
	}
	
	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() {
		when(request.getMethod()).thenReturn("GET");
when(urlService.getHomeURL()).thenReturn(url);
		
		try {
			test.service(request, response);
			verify(requestManager).setSessionAttribute(eq(request), eq(SessionAttribute.ERROR), any(ErrorBean.class));
			verify(response).sendRedirect(url);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		when(requestManager.getParameter(request, Parameter.LOGIN_EMAIL_REGISTER)).thenReturn(email);
		
		try {
			test.service(request, response);
			verify(requestManager, times(2)).setSessionAttribute(eq(request), eq(SessionAttribute.ERROR), any(ErrorBean.class));
			verify(response, times(2)).sendRedirect(url);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		when(stringManager.isEmpty(anyString())).thenReturn(false);
		
		try {
			test.service(request, response);
			verify(requestManager, times(3)).setSessionAttribute(eq(request), eq(SessionAttribute.ERROR), any(ErrorBean.class));
			verify(response, times(3)).sendRedirect(url);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		when(userService.verifyEmailDomain(email)).thenReturn(true);
		try {
			test.service(request, response);
			verify(requestManager, times(4)).setSessionAttribute(eq(request), eq(SessionAttribute.ERROR), any(ErrorBean.class));
			verify(response, times(4)).sendRedirect(url);
			verify(userService).sendRegister(anyString(), anyString(), anyString(), any(Language.class));
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		User user = mock(User.class);
		when(userService.getUserFromEmail(email)).thenReturn(user);
		
		try {
			test.service(request, response);
			verify(requestManager, times(5)).setSessionAttribute(eq(request), eq(SessionAttribute.ERROR), any(ErrorBean.class));
			verify(response, times(5)).sendRedirect(url);			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		when(requestManager.getParameter(request, Parameter.LOGIN_EMAIL_REMIND)).thenReturn(email);
		try {
			test.service(request, response);
			verify(requestManager, times(6)).setSessionAttribute(eq(request), eq(SessionAttribute.ERROR), any(ErrorBean.class));
			verify(response, times(6)).sendRedirect(url);
			verify(userService).sendPasswordRemind(user);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		when(requestManager.getParameter(request, Parameter.LOGIN_EMAIL)).thenReturn(email);
		when(requestManager.getParameter(request, Parameter.LOGIN_PASSWORD)).thenReturn(password);
		when(userService.validatePassword(user, password)).thenReturn(true);
		
		Visit visit = mock(Visit.class);
		when(visit.getUser()).thenReturn(user);
		try {
			test.service(request, response);
			verify(requestManager, times(7)).setSessionAttribute(eq(request), eq(SessionAttribute.ERROR), any(ErrorBean.class));
			verify(response, times(7)).sendRedirect(url);
			verify(statsService).createVisit(anyString());
			verify(statsService).saveUser(any(Visit.class), eq(user));
		} catch (Throwable t) {
			t.printStackTrace();
		}
			
		when(requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class)).thenReturn(visit);
		when(requestManager.getBooleanParameter(request, Parameter.LOGOUT)).thenReturn(true);
		try {
			test.service(request, response);
			verify(requestManager, times(8)).setSessionAttribute(eq(request), eq(SessionAttribute.ERROR), any(ErrorBean.class));
			verify(response, times(8)).sendRedirect(url);
			verify(visit).setUser(null);			
		} catch (Throwable t) {
			t.printStackTrace();
		}

	}

	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() {
		testDoGetHttpServletRequestHttpServletResponse();
	}
}
