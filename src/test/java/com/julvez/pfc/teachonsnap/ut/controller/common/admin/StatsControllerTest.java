package com.julvez.pfc.teachonsnap.ut.controller.common.admin;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.common.admin.StatsController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.stats.model.StatsData;
import com.julvez.pfc.teachonsnap.ut.controller.common.AdminControllerTest;

public class StatsControllerTest extends AdminControllerTest {

	@Override
	protected CommonController getController() {
		return new StatsController(userService, langService, urlService, 
				statsService, requestManager, logger, 
				properties, stringManager, pageService);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testProcessAdminController() {
		testProcessController();
		try { verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(request.getServletPath()).thenReturn("/");
		testProcessController();		
		try { verify(response, times(2)).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(request.getServletPath()).thenReturn("/admin/stats/year");
		when(statsService.getCSVFromStats((List<StatsData>)anyObject())).thenReturn(EMPTY_STRING);
		testProcessController();	
		verify(statsService).getCSVFromStats((List<StatsData>)anyObject());
		verify(requestManager).downloadFile(eq(response), anyString(), anyString(), any(InputStream.class));
		
		when(requestManager.getNumericParameter(request, Parameter.EXPORT)).thenReturn(-1);		
		testProcessController();	
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_PAGE_STACK), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_STATS_TYPE), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_STATS_DATA), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_STATS_EXTRA), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_STATS_EXTRA_2), anyObject());
		try { verify(dispatcher).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }	
	}
}
