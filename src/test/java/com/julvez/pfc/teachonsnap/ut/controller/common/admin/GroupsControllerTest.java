package com.julvez.pfc.teachonsnap.ut.controller.common.admin;

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
import com.julvez.pfc.teachonsnap.controller.common.admin.GroupsController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupService;
import com.julvez.pfc.teachonsnap.ut.controller.common.AdminControllerTest;

public class GroupsControllerTest extends AdminControllerTest {

	@Mock
	private UserGroupService groupService;
	
	@Override
	protected CommonController getController() {
		return new GroupsController(userService, langService, urlService, 
				statsService, requestManager, logger, properties, 
				stringManager, pageService, groupService);
	}

	@Test
	public void testProcessAdminController() {		
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(new String[0]);
		testProcessController();
		try { verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(null);
		testProcessController();
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_NEXTPAGE), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_PREVPAGE), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_GROUP), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_PAGE_STACK), anyObject());		
		try { verify(dispatcher).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }
		
		when(request.getMethod()).thenReturn("POST");
		
		when(requestManager.getParameter(request, Parameter.USER_GROUP_NAME)).thenReturn(EMPTY_STRING);
		when(stringManager.isEmpty(anyString())).thenReturn(true);
		testProcessController();
		try { verify(response, times(2)).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(stringManager.isEmpty(anyString())).thenReturn(false);
		testProcessController();
		verify(groupService).createGroup(EMPTY_STRING);
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.STRING_NEXTPAGE), anyObject());		
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.STRING_PREVPAGE), anyObject());		
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LIST_GROUP), anyObject());
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LIST_PAGE_STACK), anyObject());		
		try { verify(dispatcher, times(2)).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }		
	}
}
