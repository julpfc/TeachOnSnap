package com.julvez.pfc.teachonsnap.ut.controller.common.pager;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.common.pager.AuthorController;
import com.julvez.pfc.teachonsnap.controller.model.ErrorBean;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.ut.controller.common.PagerControllerTest;

public class AuthorControllerTest extends PagerControllerTest {

	private int idUser = 1;
	private int invalidIdUser = -1;
	
	@Override
	protected CommonController getController() {
		return new AuthorController(userService, langService, urlService, 
				statsService, requestManager, logger, properties, 
				stringManager, lessonService, tagService);
	}

	@Test
	public void testProcessController() {
		super.testProcessController();
		
		when(requestManager.getNumericParameter(request, Parameter.FOLLOW_AUTHOR)).thenReturn(idUser);
		testCommonController();
		try {
			verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		when(userService.getUser(idUser)).thenReturn(user);
		
		testCommonController();
		verify(userService).followAuthor(user, user);
		verify(requestManager, times(4)).setSessionAttribute(eq(request), eq(SessionAttribute.ERROR), any(ErrorBean.class));
		try {
			verify(response).sendRedirect(anyString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		when(requestManager.getNumericParameter(request, Parameter.FOLLOW_AUTHOR)).thenReturn(invalidIdUser);
		when(requestManager.getNumericParameter(request, Parameter.UNFOLLOW_AUTHOR)).thenReturn(idUser);
		
		testCommonController();
		verify(userService).unfollowAuthor(user, user);
		verify(requestManager, times(6)).setSessionAttribute(eq(request), eq(SessionAttribute.ERROR), any(ErrorBean.class));
		try {
			verify(response, times(2)).sendRedirect(anyString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

}
