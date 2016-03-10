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
import com.julvez.pfc.teachonsnap.controller.common.admin.UserProfileController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupService;
import com.julvez.pfc.teachonsnap.ut.controller.common.AdminControllerTest;

public class UserProfileControllerTest extends AdminControllerTest {

	@Mock
	private UserGroupService groupService;
	
	private int idUser = 1;
	
	@Override
	protected CommonController getController() {
		return new UserProfileController(userService, langService, urlService, 
				statsService, requestManager, logger, properties, 
				stringManager, pageService, groupService);
	}

	@Test
	public void testProcessAdminController() {
		testProcessController();
		try { verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		String[] params = {""+idUser};
		when(stringManager.isNumeric(anyString())).thenReturn(true);
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(params);
		
		testProcessController();
		try { verify(response).sendError(HttpServletResponse.SC_NOT_FOUND); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(userService.getUser(idUser)).thenReturn(user);
		
		testProcessController();
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_PAGE_STACK), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.USER_PROFILE), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_GROUP), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_PREVPAGE), anyObject());
		try { verify(dispatcher).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }	
		
		when(requestManager.getBooleanParameter(request, Parameter.REGISTER_SEND_EMAIL)).thenReturn(true);
		testProcessController();
		verify(userService).sendPasswordRemind(user);
		try { verify(response).sendRedirect(anyString());}
		catch (Throwable t) {t.printStackTrace();}
		
		when(requestManager.getBooleanParameter(request, Parameter.REGISTER_SEND_EMAIL)).thenReturn(false);
		when(request.getMethod()).thenReturn("POST");
		
		when(stringManager.isEmpty(anyString())).thenReturn(true);
		testProcessController();
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LIST_PAGE_STACK), anyObject());
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.USER_PROFILE), anyObject());
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LIST_GROUP), anyObject());		
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.STRING_PREVPAGE), anyObject());
		try { verify(dispatcher, times(2)).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }	
	}
}
