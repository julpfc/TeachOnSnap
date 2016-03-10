package com.julvez.pfc.teachonsnap.ut.controller.common.admin;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.common.admin.BroadcastController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupService;
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;
import com.julvez.pfc.teachonsnap.ut.controller.common.AdminControllerTest;

public class BroadcastControllerTest extends AdminControllerTest {

	@Mock
	private UserGroupService groupService;
	
	@Mock
	private NotifyService notifyService;
	
	private short idGroup = 1;
	
	@Override
	protected CommonController getController() {
		return new BroadcastController(userService, langService, urlService, 
				statsService, requestManager, logger, 
				properties, stringManager, pageService, 
				groupService, notifyService);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testProcessAdminController() {
		testProcessController();		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.USERGROUP), anyObject());		
		try { verify(dispatcher).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }
		
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(new String[0]);
		testProcessController();
		try { verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(new String[]{""+idGroup});
		when(stringManager.isNumeric(anyString())).thenReturn(true);
		testProcessController();
		try { verify(response).sendError(HttpServletResponse.SC_NOT_FOUND); }
		catch (Throwable t) {t.printStackTrace();}
	
		UserGroup group = mock(UserGroup.class);
		when(groupService.getGroup(idGroup)).thenReturn(group);
		testProcessController();		
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.USERGROUP), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_PAGE_STACK), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_BACKPAGE), anyObject());		
		try { verify(dispatcher, times(2)).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }
		
		when(request.getMethod()).thenReturn("POST");
		testProcessController();		
		try { verify(response, times(2)).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(stringManager.unescapeHTML(anyString())).thenReturn(EMPTY_STRING);
		testProcessController();
		verify(notifyService).broadcast((List<User>)any(List.class), anyString(), anyString());		
		verify(requestManager, times(3)).setAttribute(eq(request), eq(Attribute.USERGROUP), anyObject());		
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LIST_PAGE_STACK), anyObject());		
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.STRING_BACKPAGE), anyObject());		
		try { verify(dispatcher, times(3)).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }
		
		
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(null);
		testProcessController();		
		verify(notifyService, times(2)).broadcast((List<User>)any(List.class), anyString(), anyString());		
		verify(requestManager, times(4)).setAttribute(eq(request), eq(Attribute.USERGROUP), anyObject());		
		try { verify(dispatcher, times(4)).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }		
	}

}
