package com.julvez.pfc.teachonsnap.ut.comment.repository;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.comment.model.Comment;
import com.julvez.pfc.teachonsnap.comment.model.CommentPropertyName;
import com.julvez.pfc.teachonsnap.comment.repository.CommentRepository;
import com.julvez.pfc.teachonsnap.comment.repository.CommentRepositoryDB;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;

public class CommentRepositoryDBTest extends CommentRepositoryTest {

	@Mock
	private DBManager dbm;
	
	@Mock
	private StringManager stringManager;
	
	@Mock
	private PropertyManager properties;
	
	@Override
	protected CommentRepository getRepository() {
		return new CommentRepositoryDB(dbm, stringManager, properties);
	}
	
	@Test
	public void testGetCommentIDs() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids2.add(3);
		ids2.add(4);
		
		when(properties.getNumericProperty(CommentPropertyName.MAX_PAGE_COMMENTS)).thenReturn((long)2);
		
		when(dbm.getQueryResultList(eq("SQL_COMMENT_GET_COMMENTIDS"), eq(Integer.class), eq(invalidIdLesson),anyInt(), anyInt())).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_COMMENT_GET_COMMENTIDS"), eq(Integer.class), eq(idLesson),eq(FIRST_RESULT),eq((long)3))).thenReturn(ids);
		when(dbm.getQueryResultList(eq("SQL_COMMENT_GET_COMMENTIDS"), eq(Integer.class), eq(idLesson),eq(SECOND_RESULT),eq((long)3))).thenReturn(ids2);
		when(dbm.getQueryResultList(eq("SQL_COMMENT_GET_COMMENTIDS"), eq(Integer.class), eq(idLesson),eq(INVALID_RESULT),eq((long)3))).thenReturn(null);
		
		super.testGetCommentIDs();
		
		verify(dbm, times(4)).getQueryResultList(eq("SQL_COMMENT_GET_COMMENTIDS"), eq(Integer.class),anyInt(),anyInt(),anyInt());
		verify(properties, times(4)).getNumericProperty(CommentPropertyName.MAX_PAGE_COMMENTS);
	}

	@Test
	public void testGetComment() {
		Comment comment= new Comment();	
		comment.setBody("comment");
		
		when(dbm.getQueryResultUnique(eq("SQL_COMMENT_GET_COMMENT"), eq(Comment.class), eq(invalidIdComment))).thenReturn(null);
		when(dbm.getQueryResultUnique(eq("SQL_COMMENT_GET_COMMENT"), eq(Comment.class), eq(idComment))).thenReturn(comment);
		
		when(stringManager.escapeHTML(anyString())).thenReturn("comment");
		when(stringManager.convertToHTMLParagraph(anyString())).thenReturn("<p>comment</p>");
		super.testGetComment();
		
		verify(dbm, times(2)).getQueryResultUnique(eq("SQL_COMMENT_GET_COMMENT"), eq(Comment.class), anyInt());
	}

	@Test
	public void testCreateComment() {
		when(dbm.insertQueryAndGetLastInserID_NoCommit(anyObject(), eq("SQL_COMMENT_CREATE_COMMENT"), eq(idLesson), eq(idUser))).thenReturn((long)idComment);
		when(dbm.insertQueryAndGetLastInserID_NoCommit(anyObject(), eq("SQL_COMMENT_CREATE_COMMENT"), eq(idLesson), eq(invalidIdUser))).thenReturn((long)invalidIdComment);
		when(dbm.insertQueryAndGetLastInserID_NoCommit(anyObject(), eq("SQL_COMMENT_CREATE_COMMENT"), eq(invalidIdLesson), eq(idUser))).thenReturn((long)invalidIdComment);
		
		when(dbm.updateQuery_NoCommit(anyObject(), eq("SQL_COMMENT_SAVE_BODY"), eq(idComment), anyString(), anyString())).thenReturn(1);
		when(dbm.updateQuery_NoCommit(anyObject(), eq("SQL_COMMENT_SAVE_BODY"), eq(invalidIdComment), anyString(), anyString())).thenReturn(-1);
		super.testCreateComment();
		
		verify(dbm, times(3)).insertQueryAndGetLastInserID_NoCommit(anyObject(), eq("SQL_COMMENT_CREATE_COMMENT"), anyInt(), anyInt());
		verify(dbm).updateQuery_NoCommit(anyObject(), eq("SQL_COMMENT_SAVE_BODY"), anyInt(), anyString(), anyString());
	}

	@Test
	public void testSaveCommentParent() {
		Comment comment= new Comment();
		comment.setIdParentComment(idComment);
		
		Comment commentNoParent= new Comment();
		commentNoParent.setIdParentComment(invalidIdComment);
				
		when(dbm.getQueryResultUnique(eq("SQL_COMMENT_GET_COMMENT"), eq(Comment.class), eq(idComment)))
			.thenReturn(commentNoParent, commentNoParent, commentNoParent, comment);		
		
		super.testSaveCommentParent();
		verify(dbm, times(3)).updateQuery(eq("SQL_COMMENT_SAVE_PARENT"), anyInt(), anyInt(), anyInt());
	}

	@Test
	public void testSaveCommentBody() {
		Comment comment= new Comment();
			
		when(stringManager.convertToHTMLParagraph(anyString())).thenReturn("","","comment");
		when(dbm.getQueryResultUnique(eq("SQL_COMMENT_GET_COMMENT"), eq(Comment.class), eq(idComment)))
			.thenReturn(comment);		
		
		super.testSaveCommentBody();
		verify(dbm, times(2)).updateQuery_NoCommit(anyObject(), eq("SQL_COMMENT_SAVE_BODY"), anyInt(), anyString(), anyString());
	}

	@Test
	public void testBlockComment() {
		Comment comment= new Comment();
		comment.setBanned(true);
		
		Comment commentNoBan= new Comment();
		commentNoBan.setBanned(false);
				
		when(dbm.getQueryResultUnique(eq("SQL_COMMENT_GET_COMMENT"), eq(Comment.class), eq(idComment)))
			.thenReturn(commentNoBan, commentNoBan, commentNoBan, comment);		
		
		super.testBlockComment();
		verify(dbm, times(3)).updateQuery(eq("SQL_COMMENT_ADD_BANNED"), anyInt(), anyInt(), anyString(), anyInt(), anyString());
	}

	@Test
	public void testUnblockComment() {
		Comment comment= new Comment();
		comment.setBanned(true);
		
		Comment commentNoBan= new Comment();
		commentNoBan.setBanned(false);
				
		when(dbm.getQueryResultUnique(eq("SQL_COMMENT_GET_COMMENT"), eq(Comment.class), eq(idComment)))
			.thenReturn(comment, comment, commentNoBan);		
		
		super.testUnblockComment();
		verify(dbm, times(2)).updateQuery(eq("SQL_COMMENT_REMOVE_BANNED"), anyInt());
	}
}
