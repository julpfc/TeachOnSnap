package com.julvez.pfc.teachonsnap.ut.controller.common;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.ut.controller.CommonControllerTest;

public abstract class AdminControllerTest extends CommonControllerTest {

	@Mock
	protected PageService pageService;
	
	public void testProcessController() {		
		when(user.isAdmin()).thenReturn(true);
		if(request.getMethod() == null){
			when(request.getMethod()).thenReturn("GET");
		}
		testCommonController();
	}	
	
	@Test
	public void testProcessControllerError() {		
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_FORBIDDEN); }
		catch (Throwable t) {t.printStackTrace();}		
	}
	
	@Test
	public abstract void testProcessAdminController();
}
