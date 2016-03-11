package com.julvez.pfc.teachonsnap.ut.controller.common;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.common.FollowAuthorController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.ErrorBean;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.ut.controller.CommonControllerTest;

public class FollowAuthorControllerTest extends CommonControllerTest {

	@Mock
	private PageService pageService;
	
	private int idUser = 1;
	private int invalidIdUser = -1;
	
	@Override
	protected CommonController getController() {
		return new FollowAuthorController(userService, langService, urlService, 
				statsService, requestManager, logger,
				properties, stringManager, pageService);
	}
	
	@Test
	public void testProcessController() {
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(new String[]{""+idUser});
		when(stringManager.isNumeric(anyString())).thenReturn(true);
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_NOT_FOUND); }
		catch (Throwable t) {t.printStackTrace();}
	
		when(userService.getUser(idUser)).thenReturn(user);
		testCommonController();	
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_NEXTPAGE), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_PREVPAGE), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_BACKPAGE), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_AUTHOR_FOLLOWED), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_PAGE_STACK), anyObject());		
		try { verify(dispatcher).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }
		
		
		when(requestManager.getNumericParameter(request, Parameter.FOLLOW_AUTHOR)).thenReturn(idUser);
		testCommonController();
		verify(userService).followAuthor(user, user);
		verify(requestManager, times(5)).setSessionAttribute(eq(request), eq(SessionAttribute.ERROR), any(ErrorBean.class));
		try {
			verify(response).sendRedirect(anyString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		when(requestManager.getNumericParameter(request, Parameter.FOLLOW_AUTHOR)).thenReturn(invalidIdUser);
		when(requestManager.getNumericParameter(request, Parameter.UNFOLLOW_AUTHOR)).thenReturn(idUser);
		
		testCommonController();
		verify(userService).unfollowAuthor(user, user);
		verify(requestManager, times(7)).setSessionAttribute(eq(request), eq(SessionAttribute.ERROR), any(ErrorBean.class));
		try {
			verify(response, times(2)).sendRedirect(anyString());
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
