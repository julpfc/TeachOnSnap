package com.julvez.pfc.teachonsnap.ut.controller.common;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.common.NewLessonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.link.LinkService;
import com.julvez.pfc.teachonsnap.media.MediaFileService;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.upload.UploadService;
import com.julvez.pfc.teachonsnap.ut.controller.CommonControllerTest;

public class NewLessonControllerTest extends CommonControllerTest {
	
	@Mock
	private LessonService lessonService;
	
	@Mock
	private TagService tagService;
	
	@Mock
	private LinkService linkService;
	
	@Mock
	private MediaFileService mediaFileService;
	
	@Mock
	private UploadService uploadService;
	
	
	@Override
	protected CommonController getController() {		
		return new NewLessonController(userService, langService, 
				urlService, statsService, requestManager, logger, 
				properties, stringManager, lessonService, tagService, 
				linkService, mediaFileService, uploadService);
	}
	
	@Override
	public void testProcessController() {
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_FORBIDDEN); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(user.isAuthor()).thenReturn(true);
		testCommonController();
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LONG_MAX_UPLOAD_FILE_SIZE), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_STRING_MEDIATYPE), anyObject());		
		try { verify(dispatcher).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }
		
		when(request.getMethod()).thenReturn("POST");
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(stringManager.unescapeHTML(anyString())).thenReturn("data");
		when(requestManager.getNumericParameter(request, Parameter.LESSON_NEW_LANGUAGE)).thenReturn(1);
		testCommonController();		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LESSON), anyObject());
		verify(requestManager, times(3)).setAttribute(eq(request), eq(Attribute.LONG_MAX_UPLOAD_FILE_SIZE), anyObject());		
		verify(requestManager, times(3)).setAttribute(eq(request), eq(Attribute.LIST_STRING_MEDIATYPE), anyObject());		
		try { verify(dispatcher, times(2)).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }
	
		Lesson lesson = mock(Lesson.class);
		when(lessonService.createLesson(any(Lesson.class))).thenReturn(lesson);
		testCommonController();	
		verify(lessonService).notifyLessonCreated(any(Lesson.class));
		try { verify(response).sendRedirect(anyString()); } 
		catch (IOException e) { e.printStackTrace(); }		
	}
}
