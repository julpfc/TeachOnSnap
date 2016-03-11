package com.julvez.pfc.teachonsnap.ut.controller.common;

import static org.mockito.Matchers.anyInt;
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

import com.julvez.pfc.teachonsnap.comment.CommentService;
import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.common.LessonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.LessonTestService;
import com.julvez.pfc.teachonsnap.link.LinkService;
import com.julvez.pfc.teachonsnap.media.MediaFileService;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.ut.controller.CommonControllerTest;

public class LessonControllerTest extends CommonControllerTest {

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
	private CommentService commentService;

	private int idLesson = 1;
	private int invalidIdLesson = -1;
	
	@Override
	protected CommonController getController() {
		return new LessonController(userService, langService, urlService, 
				statsService, requestManager, logger, properties, stringManager, 
				lessonService, tagService, linkService, lessonTestService, 
				mediaFileService, commentService);
	}

	@Override
	public void testProcessController() {
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}

		when(requestManager.splitParamsFromControllerURI(request)).thenReturn(new String[]{"data"});
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_NOT_FOUND); }
		catch (Throwable t) {t.printStackTrace();}

		Lesson lesson = mock(Lesson.class);
		when(lessonService.getLessonFromURI(anyString())).thenReturn(lesson);
		testCommonController();
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LESSON), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_MEDIAFILE_LESSONFILES), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_TAG), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_LINK_MOREINFO), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_LINK_SOURCES), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.LIST_COMMENTS), anyObject());				
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_NEXTPAGE), anyObject());		
		verify(requestManager).setAttribute(eq(request), eq(Attribute.STRING_PREVPAGE), anyObject());		
		try { verify(dispatcher).forward(request, response); } 
		catch (Throwable t) { t.printStackTrace(); }
		
		
		when(requestManager.getNumericParameter(request, Parameter.FOLLOW_LESSON)).thenReturn(idLesson);
		testCommonController();
		try { verify(response, times(2)).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(lessonService.getLesson(anyInt())).thenReturn(lesson);
		testCommonController();
		verify(userService).followLesson(user, lesson);		
		try {
			verify(response).sendRedirect(anyString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		when(requestManager.getNumericParameter(request, Parameter.FOLLOW_LESSON)).thenReturn(invalidIdLesson);
		when(requestManager.getNumericParameter(request, Parameter.UNFOLLOW_LESSON)).thenReturn(idLesson);
		
		testCommonController();
		verify(userService).unfollowLesson(user, lesson);		
		try {
			verify(response, times(2)).sendRedirect(anyString());
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
}
