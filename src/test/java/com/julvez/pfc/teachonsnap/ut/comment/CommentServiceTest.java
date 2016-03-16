package com.julvez.pfc.teachonsnap.ut.comment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.comment.CommentService;
import com.julvez.pfc.teachonsnap.comment.model.Comment;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.ut.service.ServiceTest;

public abstract class CommentServiceTest extends ServiceTest<CommentService> {

	protected int idLesson = 1;
	protected int invalidIdLesson = -1;
	
	protected int idComment = 1;
	protected int idParentComment = 1;
	protected int invalidIdComment = -1;

	protected int idUser = 1;
	protected int invalidIdUser = -1;
	
	protected String body = "comment";
	protected String newBody = "comment2";
	
	@Test
	public void testGetComments() {
		List<Comment> comments = test.getComments(idLesson, FIRST_RESULT);		
		assertNotNull(comments);
		
		int i=1;
		for(Comment	c:comments){
			assertEquals(i++, c.getId());
		}		
		
		comments = test.getComments(idLesson, SECOND_RESULT);		
		assertNotNull(comments);
		
		for(Comment	c:comments){
			assertEquals(i++, c.getId());
		}
		
		comments = test.getComments(invalidIdLesson, FIRST_RESULT);
		assertNotNull(comments);
		assertEquals(0, comments.size());
		
		comments = test.getComments(idLesson, INVALID_RESULT);
		assertNotNull(comments);
		assertEquals(0, comments.size());
	}

	@Test
	public void testGetComment() {
		Comment comment = test.getComment(idComment);
		assertNotNull(comment);
				
		assertNull(test.getComment(invalidIdComment));
	}

	@Test
	public void testCreateComment() {
		assertNotNull(test.createComment(idLesson, idUser, body, idParentComment));
		
		assertNull(test.createComment(invalidIdLesson, idUser, body, idParentComment));
		assertNull(test.createComment(idLesson, invalidIdUser, body, idParentComment));
	}

	@Test
	public void testSaveCommentBody() {
		Comment comment = test.getComment(idComment);
		assertNotNull(comment);
		assertEquals("<p>"+body+"</p>", comment.getBody());
		
		test.saveCommentBody(invalidIdComment, invalidIdUser, NULL_STRING);
		
		comment = test.getComment(idComment);
		assertNotNull(comment);
		assertEquals("<p>"+body+"</p>", comment.getBody());
		
		test.saveCommentBody(idComment, idUser, newBody);
		
		comment = test.getComment(idComment);
		assertNotNull(comment);
		assertEquals("<p>"+newBody+"</p>", comment.getBody());
	}

	@Test
	public void testBlockComment() {
		User admin = mock(User.class);
		when(admin.getId()).thenReturn(idUser);
		when(admin.isAdmin()).thenReturn(true);
		
		Comment comment = test.getComment(idComment);
		assertNotNull(comment);
		assertFalse(comment.isBanned());
		
		test.blockComment(invalidIdComment, admin, EMPTY_STRING);
		
		comment = test.getComment(idComment);
		assertNotNull(comment);
		assertFalse(comment.isBanned());
		
		test.blockComment(idComment, null, EMPTY_STRING);
		
		comment = test.getComment(idComment);
		assertNotNull(comment);
		assertFalse(comment.isBanned());
		
		test.blockComment(idComment, admin, "reason");
		
		comment = test.getComment(idComment);
		assertNotNull(comment);
		assertTrue(comment.isBanned());	
	}

	@Test
	public void testUnblockComment() {
		User admin = mock(User.class);
		when(admin.getId()).thenReturn(idUser);
		when(admin.isAdmin()).thenReturn(true);
		
		Comment comment = test.getComment(idComment);
		assertNotNull(comment);
		assertTrue(comment.isBanned());
		
		test.unblockComment(invalidIdComment, admin);
		
		comment = test.getComment(idComment);
		assertNotNull(comment);
		assertTrue(comment.isBanned());
		
		test.unblockComment(idComment, admin);
		
		comment = test.getComment(idComment);
		assertNotNull(comment);
		assertFalse(comment.isBanned());
	}

	@Test
	public void testNotifyComment() {
		Comment comment = test.getComment(idLesson);
		assertNotNull(comment);
		
		assertFalse(test.notifyComment(idLesson, comment));
		assertFalse(test.notifyComment(invalidIdLesson, comment));
		assertFalse(test.notifyComment(idLesson, null));
	}

	@Test
	public void testNotifyParentComment() {
		Comment comment = test.getComment(idLesson);
		assertNotNull(comment);
		
		assertFalse(test.notifyParentComment(idLesson, comment, idParentComment));
		assertFalse(test.notifyParentComment(invalidIdLesson, comment, invalidIdComment));
		assertFalse(test.notifyParentComment(idLesson, null, invalidIdComment));
	}
}
