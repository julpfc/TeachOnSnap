package com.julvez.pfc.teachonsnap.ut.controller.common;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.ut.controller.CommonControllerTest;

public abstract class PagerControllerTest extends CommonControllerTest {

	@Mock
	protected LessonService lessonService;
	
	@Mock
	protected TagService tagService;

	@Test
	public void testProcessController() {
		when(request.getServletPath()).thenReturn("/");
		when(request.getMethod()).thenReturn("GET");
		
		testCommonController();
		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_NEXTPAGE), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_PREVPAGE), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_CLOUDTAG_TAG_SEARCH), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_CLOUDTAG_AUTHOR), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_CLOUDTAG_TAG_USE), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_CLOUDTAG_LESSON), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_LESSON), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_SEARCHTYPE), anyObject());
		
		try { verify(dispatcher).forward(request, response);} 
		catch (Throwable t) {t.printStackTrace();}		
	}	
	
	@Test
	public void testProcessControllerError() {
		when(request.getMethod()).thenReturn("GET");
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}		
	}
}
