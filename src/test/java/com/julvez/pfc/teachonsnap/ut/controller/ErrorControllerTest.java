package com.julvez.pfc.teachonsnap.ut.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.controller.ErrorController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;

public class ErrorControllerTest extends ControllerTest<ErrorController> {

	@Override
	protected ErrorController getController() {
		return new ErrorController(langService, urlService, requestManager, logger);
	}
	
	@Test
	public void testServiceHttpServletRequestHttpServletResponse() {
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		
		try {
			test.service(request, response);
			verify(request).getRequestDispatcher(anyString());
			verify(requestManager, times(4)).setAttribute(eq(request), any(Attribute.class), anyObject());
			verify(requestManager).getAttribute(request, Attribute.INT_ERROR_STATUS_CODE, Object.class);
			verify(requestManager).getAttribute(request, Attribute.THROWABLE_ERROR_EXCEPTION, Throwable.class);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
