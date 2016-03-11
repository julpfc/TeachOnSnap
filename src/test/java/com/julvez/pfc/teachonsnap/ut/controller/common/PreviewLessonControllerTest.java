package com.julvez.pfc.teachonsnap.ut.controller.common;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletResponse;

import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.common.PreviewLessonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.LessonTestService;
import com.julvez.pfc.teachonsnap.link.LinkService;
import com.julvez.pfc.teachonsnap.media.MediaFileService;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.ut.controller.CommonControllerTest;

public class PreviewLessonControllerTest extends CommonControllerTest {

	@Mock
	private LessonService lessonService;
	
	@Mock
	private TagService tagService;
	
	@Mock
	private LinkService linkService;
	
	@Mock
	private LessonTestService lessonTestService;
	
	@Mock
	private MediaFileService mediaFileService;
	
	private int idLesson = 1;
	
	@Override
	protected CommonController getController() {
		return new PreviewLessonController(userService, langService, 
				urlService, statsService, requestManager, logger, 
				properties, stringManager, lessonService, tagService, 
				linkService, lessonTestService, mediaFileService);
	}
	
	@Override
	public void testProcessController() {
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}

		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(new String[]{""+idLesson});
		when(stringManager.isNumeric(anyString())).thenReturn(true);
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_NOT_FOUND); }
		catch (Throwable t) {t.printStackTrace();}

		Lesson lesson = mock(Lesson.class);
		when(lessonService.getLesson(idLesson)).thenReturn(lesson);
		testCommonController();
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LESSON), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_MEDIAFILE_LESSONFILES), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_TAG), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_LINK_MOREINFO), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_LINK_SOURCES), anyObject());		
		try { verify(dispatcher).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }
	}
}
