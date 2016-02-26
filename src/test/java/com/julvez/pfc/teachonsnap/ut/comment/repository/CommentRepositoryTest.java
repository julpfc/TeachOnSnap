package com.julvez.pfc.teachonsnap.ut.comment.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.comment.model.Comment;
import com.julvez.pfc.teachonsnap.comment.repository.CommentRepository;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryTest;

public abstract class CommentRepositoryTest extends RepositoryTest<CommentRepository> {

	protected int idLesson = 1;
	protected int invalidIdLesson = -1;
	
	protected int idComment = 1;
	protected int invalidIdComment = -1;

	protected int idUser = 1;
	protected int invalidIdUser = -1;
	
	@Test
	public void testGetCommentIDs() {
		List<Integer> ids = test.getCommentIDs(idLesson, FIRST_RESULT);		
		assertNotNull(ids);
		
		int i=1;
		for(int b:ids){
			assertEquals(i++, b);
		}		
		
		ids = test.getCommentIDs(idLesson, SECOND_RESULT);		
		assertNotNull(ids);
		
		for(int b:ids){
			assertEquals(i++, b);
		}
		
		ids = test.getCommentIDs(invalidIdLesson, FIRST_RESULT);
		assertNull(ids);
		
		ids = test.getCommentIDs(idLesson, INVALID_RESULT);
		assertNull(ids);		
	}

	@Test
	public void testGetComment() {
		Comment comment = test.getComment(idComment);
		assertNotNull(comment);
		assertEquals("<p>comment</p>",comment.getBody());
		
		assertNull(test.getComment(invalidIdComment));
	}

	@Test
	public void testCreateComment() {
		assertEquals(idComment, test.createComment(idLesson, idUser, "comment"));
		
		assertEquals(invalidIdComment, test.createComment(invalidIdLesson, idUser, "comment"));
		assertEquals(invalidIdComment, test.createComment(idLesson, invalidIdUser, "comment"));
	}

	@Test
	public void testSaveCommentParent() {
		Comment comment = test.getComment(idComment);
		assertNotNull(comment);
		assertEquals(invalidIdComment, comment.getIdParentComment());
		
		test.saveCommentParent(idComment, 0);
		
		comment = test.getComment(idComment);
		assertNotNull(comment);
		assertEquals(invalidIdComment, comment.getIdParentComment());

		test.saveCommentParent(invalidIdComment, idComment);
		
		comment = test.getComment(idComment);
		assertNotNull(comment);
		assertEquals(invalidIdComment, comment.getIdParentComment());
		
		test.saveCommentParent(idComment, idComment);

		comment = test.getComment(idComment);
		assertNotNull(comment);
		assertEquals(idComment, comment.getIdParentComment());		
	}

	@Test
	public void testSaveCommentBody() {
		Comment comment = test.getComment(idComment);
		assertNotNull(comment);
		assertEquals("", comment.getBody());
		
		test.saveCommentBody(invalidIdComment, NULL_STRING);
		
		comment = test.getComment(idComment);
		assertNotNull(comment);
		assertEquals("", comment.getBody());
		
		test.saveCommentBody(idComment, "comment");
		
		comment = test.getComment(idComment);
		assertNotNull(comment);
		assertEquals("comment", comment.getBody());		
	}

	@Test
	public void testBlockComment() {
		Comment comment = test.getComment(idComment);
		assertNotNull(comment);
		assertFalse(comment.isBanned());
		
		test.blockComment(invalidIdComment, idUser, EMPTY_STRING);
		
		comment = test.getComment(idComment);
		assertNotNull(comment);
		assertFalse(comment.isBanned());
		
		test.blockComment(idComment, invalidIdUser, EMPTY_STRING);
		
		comment = test.getComment(idComment);
		assertNotNull(comment);
		assertFalse(comment.isBanned());
		
		test.blockComment(idComment, idUser, "reason");
		
		comment = test.getComment(idComment);
		assertNotNull(comment);
		assertTrue(comment.isBanned());		
	}

	@Test
	public void testUnblockComment() {
		Comment comment = test.getComment(idComment);
		assertNotNull(comment);
		assertTrue(comment.isBanned());
		
		test.unblockComment(invalidIdComment);
		
		comment = test.getComment(idComment);
		assertNotNull(comment);
		assertTrue(comment.isBanned());
		
		test.unblockComment(idComment);
		
		comment = test.getComment(idComment);
		assertNotNull(comment);
		assertFalse(comment.isBanned());				
	}

}
