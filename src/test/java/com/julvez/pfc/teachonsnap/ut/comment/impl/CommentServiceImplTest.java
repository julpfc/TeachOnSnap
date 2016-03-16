package com.julvez.pfc.teachonsnap.ut.comment.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.comment.CommentService;
import com.julvez.pfc.teachonsnap.comment.impl.CommentServiceImpl;
import com.julvez.pfc.teachonsnap.comment.model.Comment;
import com.julvez.pfc.teachonsnap.comment.repository.CommentRepository;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.ut.comment.CommentServiceTest;

public class CommentServiceImplTest extends CommentServiceTest {

	@Mock
	private CommentRepository commentRepository;
	
	@Mock
	private UserService userService;
	
	@Mock
	private LessonService lessonService;
	
	@Mock
	private TextService textService;
	
	@Mock
	private NotifyService notifyService;
	
	@Override
	protected CommentService getService() {		
		return new CommentServiceImpl(commentRepository, userService, 
				lessonService, textService, notifyService);
	}
	
	@Test
	public void testGetComments() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids2.add(3);
		ids2.add(4);
		
		when(commentRepository.getCommentIDs(idLesson, FIRST_RESULT)).thenReturn(ids);
		when(commentRepository.getCommentIDs(idLesson, SECOND_RESULT)).thenReturn(ids2);
		
		Comment comment = mock(Comment.class);
		when(comment.getId()).thenReturn(idComment, 2, 3 , 4);
		when(commentRepository.getComment(anyInt())).thenReturn(comment);
		
		super.testGetComments();
		
		verify(commentRepository, times(2)).getCommentIDs(anyInt(), anyInt());
	}

	@Test
	public void testGetComment() {
		Comment comment = mock(Comment.class);
		when(comment.getId()).thenReturn(idComment);
		when(commentRepository.getComment(idComment)).thenReturn(comment);
		
		super.testGetComment();
		
		verify(commentRepository).getComment(anyInt());
	}

	@Test
	public void testCreateComment() {
		Comment comment = mock(Comment.class);
		when(comment.getId()).thenReturn(idComment);
		when(commentRepository.getComment(anyInt())).thenReturn(comment);

		when(commentRepository.createComment(idLesson, idUser, body)).thenReturn(idComment);
		super.testCreateComment();
		
		verify(commentRepository).createComment(anyInt(), anyInt(), anyString());
	}

	@Test
	public void testSaveCommentBody() {
		Comment comment = mock(Comment.class);
		when(comment.getId()).thenReturn(idComment);
		when(comment.getIdUser()).thenReturn(idUser);
		when(comment.getBody()).thenReturn("<p>"+body+"</p>", "<p>"+body+"</p>", "<p>"+newBody+"</p>");
		when(commentRepository.getComment(idComment)).thenReturn(comment);
		
		super.testSaveCommentBody();
		
		verify(commentRepository).saveCommentBody(anyInt(), anyString());
	}

	@Test
	public void testBlockComment() {
		Comment comment = mock(Comment.class);
		when(comment.getId()).thenReturn(idComment);
		when(comment.getIdUser()).thenReturn(idUser);
		when(comment.isBanned()).thenReturn(false, false, false, false, false, true);
		when(commentRepository.getComment(idComment)).thenReturn(comment);
		
		super.testBlockComment();
		
		verify(commentRepository).blockComment(anyInt(), anyInt(), anyString());
	}

	@Test
	public void testUnblockComment() {
		Comment comment = mock(Comment.class);
		when(comment.getId()).thenReturn(idComment);
		when(comment.getIdUser()).thenReturn(idUser);
		when(comment.isBanned()).thenReturn(true, true, true, false);
		when(commentRepository.getComment(idComment)).thenReturn(comment);
		
		super.testUnblockComment();
		
		verify(commentRepository).unblockComment(anyInt());
	}

	@Test
	public void testNotifyComment() {
		Comment comment = mock(Comment.class);
		when(comment.getId()).thenReturn(idComment);
		when(comment.getIdUser()).thenReturn(idUser);		
		when(commentRepository.getComment(idComment)).thenReturn(comment);
		
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		
		when(lessonService.getLesson(idLesson)).thenReturn(lesson);
		
		List<User> followers = new ArrayList<User>();
		followers.add(new User());
		when(userService.getLessonFollowers(any(Lesson.class))).thenReturn(followers);
		
		User author = new User();
		author.setId(idUser);
		when(userService.getUser(idUser)).thenReturn(author);
		
		super.testNotifyComment();
		verify(notifyService).info(any(User.class), anyString(), anyString());
	}

	@Test
	public void testNotifyParentComment() {
		Comment comment = mock(Comment.class);
		when(comment.getId()).thenReturn(idComment);
		when(comment.getIdUser()).thenReturn(idUser, 0);		
		when(commentRepository.getComment(anyInt())).thenReturn(comment);
		
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		
		when(lessonService.getLesson(idLesson)).thenReturn(lesson);
		
		User author = mock(User.class);
		when(author.getId()).thenReturn(idUser, 0);
		when(userService.getUser(anyInt())).thenReturn(author);
		
		super.testNotifyParentComment();
		verify(notifyService).info(any(User.class), anyString(), anyString());
	}
}
