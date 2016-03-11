package com.julvez.pfc.teachonsnap.ut.controller.common;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.common.EditLessonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.LessonTestService;
import com.julvez.pfc.teachonsnap.link.LinkService;
import com.julvez.pfc.teachonsnap.media.MediaFileService;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.upload.UploadService;
import com.julvez.pfc.teachonsnap.ut.controller.CommonControllerTest;

public class EditLessonControllerTest extends CommonControllerTest {

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
	
	@Mock
	private PageService pageService;
	
	@Mock
	private UploadService uploadService;

	private int idLesson = 1;
	
	
	@Override
	protected CommonController getController() {		
		return new EditLessonController(userService, langService, 
				urlService, statsService, requestManager, logger, 
				properties, stringManager, lessonService, tagService, 
				linkService, lessonTestService, mediaFileService, 
				pageService, uploadService);
	}
	
	@Test
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
		try { verify(response).sendError(HttpServletResponse.SC_FORBIDDEN); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(userService.isAllowedForLesson(user, lesson)).thenReturn(true);
		testCommonController();
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LESSON), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_MEDIAFILE_LESSONFILES), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_TAG), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_LINK_MOREINFO), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_LINK_SOURCES), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LESSONTEST_QUESTIONS), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LONG_MAX_UPLOAD_FILE_SIZE), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_STRING_MEDIATYPE), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_PAGE_STACK), anyObject());		
		try { verify(dispatcher).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }
		
		when(requestManager.getParameter(request,Parameter.LESSON_PUBLISH)).thenReturn(EMPTY_STRING);
		testCommonController();
		verify(lessonService).unpublish(lesson);
		try { verify(response).sendRedirect(anyString());}
		catch (Throwable t) {t.printStackTrace();}
		
		when(requestManager.getParameter(request,Parameter.LESSON_PUBLISH)).thenReturn(null);
		when(request.getMethod()).thenReturn("POST");
		when(stringManager.isEmpty(anyString())).thenReturn(true);
		testCommonController();
		
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LESSON), anyObject());		
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LIST_MEDIAFILE_LESSONFILES), anyObject());		
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LIST_TAG), anyObject());		
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LIST_LINK_MOREINFO), anyObject());		
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LIST_LINK_SOURCES), anyObject());		
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LESSONTEST_QUESTIONS), anyObject());		
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LONG_MAX_UPLOAD_FILE_SIZE), anyObject());		
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LIST_STRING_MEDIATYPE), anyObject());		
		verify(requestManager, times(2)).setAttribute(eq(request), eq(Attribute.LIST_PAGE_STACK), anyObject());		
		try { verify(dispatcher, times(2)).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }
	}
}
