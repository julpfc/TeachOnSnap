package com.julvez.pfc.teachonsnap.ut.controller.common;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.common.UserProfileController;
import com.julvez.pfc.teachonsnap.ut.controller.CommonControllerTest;

public class UserProfileControllerTest extends CommonControllerTest {

	private String name = "name";
	
	
	@Override
	protected CommonController getController() {
		return new UserProfileController(userService, langService, urlService, 
				statsService, requestManager, logger, properties, stringManager);
	}
	
	@Test
	public void testProcessController() {
		testCommonController();
		try { verify(dispatcher).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }
		
		when(request.getMethod()).thenReturn("POST");
		when(stringManager.unescapeHTML(anyString())).thenReturn(name);
		testCommonController();
		verify(userService).saveFirstName(user, name);		
		try { verify(response).sendRedirect(anyString());}
		catch (Throwable t) {t.printStackTrace();}		
	}
}
