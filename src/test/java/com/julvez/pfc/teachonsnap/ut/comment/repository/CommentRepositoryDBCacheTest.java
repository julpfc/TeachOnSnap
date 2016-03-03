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
import com.julvez.pfc.teachonsnap.comment.repository.CommentRepository;
import com.julvez.pfc.teachonsnap.comment.repository.CommentRepositoryDB;
import com.julvez.pfc.teachonsnap.comment.repository.CommentRepositoryDBCache;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;

public class CommentRepositoryDBCacheTest extends CommentRepositoryTest {

	@Mock
	private CommentRepositoryDB repoDB;
	
	@Mock
	private CacheManager cache;
	
	@Mock
	private StringManager stringManager;
	
	@Override
	protected CommentRepository getRepository() {
		return new CommentRepositoryDBCache(repoDB, cache, stringManager);
	}
	
	@Test
	public void testGetCommentIDs() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids2.add(3);
		ids2.add(4);
		
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdLesson),anyInt())).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(idLesson),eq(FIRST_RESULT))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(idLesson),eq(SECOND_RESULT))).thenReturn(ids2);
		when(cache.executeImplCached(eq(repoDB), eq(idLesson),eq(INVALID_RESULT))).thenReturn(null);
		
		super.testGetCommentIDs();
		
		verify(cache, times(4)).executeImplCached(eq(repoDB),anyInt(),anyInt());		
	}

	@Test
	public void testGetComment() {
		Comment comment= new Comment();		
		comment.setBody("<p>comment</p>");
		
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdComment))).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(idComment))).thenReturn(comment);
		
		super.testGetComment();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyInt());

	}

	@Test
	public void testCreateComment() {
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), eq(idLesson), eq(idUser), anyString())).thenReturn(idComment);
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), eq(idLesson), eq(invalidIdUser), anyString())).thenReturn(invalidIdComment);
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), eq(invalidIdLesson), eq(idUser), anyString())).thenReturn(invalidIdComment);
		
		super.testCreateComment();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt(), anyString());
	}

	@Test
	public void testSaveCommentParent() {
		Comment comment= new Comment();
		comment.setIdParentComment(idComment);
		
		Comment commentNoParent= new Comment();
		commentNoParent.setIdParentComment(invalidIdComment);
				
		when(cache.executeImplCached(eq(repoDB), eq(idComment)))
			.thenReturn(commentNoParent, commentNoParent, commentNoParent, comment);		
		
		super.testSaveCommentParent();
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt());
	}

	@Test
	public void testSaveCommentBody() {
		Comment comment = new Comment();
		comment.setBody("comment");
		
		Comment commentNoBody = new Comment();
		commentNoBody.setBody("");
		
		when(cache.executeImplCached(eq(repoDB), eq(idComment)))
		.thenReturn(commentNoBody, commentNoBody, comment);		
		
		super.testSaveCommentBody();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyString());	
	}

	@Test
	public void testBlockComment() {
		Comment comment= new Comment();
		comment.setBanned(true);
		
		Comment commentNoBan= new Comment();
		commentNoBan.setBanned(false);
				
		when(cache.executeImplCached(eq(repoDB), eq(idComment)))		
			.thenReturn(commentNoBan, commentNoBan, commentNoBan, comment);		
		
		super.testBlockComment();
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), 
									anyInt(), anyInt(), anyString());
	}

	@Test
	public void testUnblockComment() {
		Comment comment= new Comment();
		comment.setBanned(true);
		
		Comment commentNoBan= new Comment();
		commentNoBan.setBanned(false);
				
		when(cache.executeImplCached(eq(repoDB), eq(idComment)))		
			.thenReturn(comment, comment, commentNoBan);		
		
		super.testUnblockComment();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt());
	}

}
