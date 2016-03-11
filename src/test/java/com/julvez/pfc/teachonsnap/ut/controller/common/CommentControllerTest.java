package com.julvez.pfc.teachonsnap.ut.controller.common;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.comment.CommentService;
import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.common.CommentController;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.ut.controller.CommonControllerTest;

public class CommentControllerTest extends CommonControllerTest {

	@Mock
	private LessonService lessonService;
	
	@Mock
	private CommentService commentService;

	@Override
	protected CommonController getController() {
		return new CommentController(userService, langService, urlService, 
				statsService, requestManager, logger, properties, 
				stringManager, lessonService, commentService);
	}
	
	@Test
	public void testProcessController() {
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_NOT_FOUND); }
		catch (Throwable t) {t.printStackTrace();}
				
		Lesson lesson = mock(Lesson.class);
		when(lessonService.getLessonFromURI(anyString())).thenReturn(lesson);
		testCommonController();
		try { verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(requestManager.getParameter(request,Parameter.LESSON_COMMENT_BAN)).thenReturn(EMPTY_STRING);
		when(stringManager.isTrue(anyString())).thenReturn(true);		
		testCommonController();
		try { verify(response, times(2)).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(stringManager.isTrue(anyString())).thenReturn(false);		
		testCommonController();
		verify(commentService).unblockComment(anyInt(), eq(user));
		try { verify(response).sendRedirect(anyString());}
		catch (Throwable t) {t.printStackTrace();}		
		
		when(request.getMethod()).thenReturn("POST");
		testCommonController();
		try { verify(response, times(3)).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}		
		
		when(stringManager.unescapeHTML(anyString())).thenReturn("data");
		testCommonController();
		try { verify(response, times(4)).sendError(HttpServletResponse.SC_BAD_REQUEST); }
		catch (Throwable t) {t.printStackTrace();}
		
		when(stringManager.isTrue(anyString())).thenReturn(true);
		testCommonController();
		verify(commentService).blockComment(anyInt(), eq(user), anyString());
		try { verify(response, times(2)).sendRedirect(anyString());}
		catch (Throwable t) {t.printStackTrace();}

		when(requestManager.getParameter(request,Parameter.LESSON_COMMENT_BAN)).thenReturn(null);
		testCommonController();		
		verify(commentService).createComment(anyInt(), anyInt(), anyString(), anyInt());
		try { verify(response, times(3)).sendRedirect(anyString());}
		catch (Throwable t) {t.printStackTrace();}
		
		when(requestManager.getBooleanParameter(request,Parameter.LESSON_COMMENT_EDIT)).thenReturn(true);
		testCommonController();
		verify(commentService).saveCommentBody(anyInt(), anyInt(), anyString());
		try { verify(response, times(4)).sendRedirect(anyString());}
		catch (Throwable t) {t.printStackTrace();}
	}
}
