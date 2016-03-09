package com.julvez.pfc.teachonsnap.ut.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.ErrorBean;
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.model.User;

public abstract class CommonControllerTest extends ControllerTest<CommonController> {

	@Mock
	protected UserService userService;
		
	@Mock
	protected StatsService statsService;
	
	@Mock
	protected PropertyManager properties;
	
	@Mock
	protected StringManager stringManager;
		
	@Mock
	protected User user;
	
	@Mock
	protected Visit visit;
	
	
	@Test
	public void testDoGetHttpServletRequestHttpServletResponse() {
		when(request.getMethod()).thenReturn("GET");
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		
		Language lang = mock(Language.class);
		when(langService.getUserSessionLanguage(anyString(), any(Visit.class), anyString())).thenReturn(lang);
		
		try {
			test.service(request, response);
			verify(requestManager).setSessionAttribute(eq(request), eq(SessionAttribute.ERROR), any(ErrorBean.class));
			verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_LANGUAGES), anyObject());			
			verify(requestManager).setAttribute(eq(request), eq(Attribute.LANGUAGE_USERLANGUAGE), anyObject());			
			verify(requestManager).setAttribute(eq(request), eq(Attribute.USER), anyObject());			
			verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_HOST), anyObject());			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		when(visit.getUser()).thenReturn(user);
		when(user.getLanguage()).thenReturn(lang);
		when(requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class)).thenReturn(visit);
		
		try {
			test.service(request, response);
			verify(requestManager, times(2)).setSessionAttribute(eq(request), eq(SessionAttribute.ERROR), any(ErrorBean.class));
			verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LIST_LANGUAGES), anyObject());			
			verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LANGUAGE_USERLANGUAGE), anyObject());			
			verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.USER), anyObject());			
			verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.STRING_HOST), anyObject());			
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() {
		testDoGetHttpServletRequestHttpServletResponse();
	}

	public void testCommonController(){
		when(visit.getUser()).thenReturn(user);		
		when(requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class)).thenReturn(visit);
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		
		Language lang = mock(Language.class);
		when(langService.getUserSessionLanguage(anyString(), any(Visit.class), anyString())).thenReturn(lang);
		when(user.getLanguage()).thenReturn(lang);
		
		try {
			test.service(request, response);
		} catch (Throwable t) {
			t.printStackTrace();
		}		
	}
	
	@Test
	public abstract void testProcessController();
		
}
