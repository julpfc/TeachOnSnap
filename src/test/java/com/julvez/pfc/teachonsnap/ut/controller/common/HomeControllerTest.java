package com.julvez.pfc.teachonsnap.ut.controller.common;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.common.HomeController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.ut.controller.CommonControllerTest;

public class HomeControllerTest extends CommonControllerTest {

	@Mock
	private LessonService lessonService;
	
	@Mock
	private TagService tagService;
	
	@Override
	protected CommonController getController() {
		return new HomeController(userService, langService, urlService, 
				statsService, requestManager, logger, properties, 
				stringManager, lessonService, tagService);
	}

	@Test
	public void testProcessController() {
		when(request.getMethod()).thenReturn("GET");
		
		testCommonController();
		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_CLOUDTAG_AUTHOR), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_CLOUDTAG_TAG_USE), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_CLOUDTAG_LESSON), anyObject());
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_LESSON), anyObject());
		
		try { verify(dispatcher).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }
	}
}
