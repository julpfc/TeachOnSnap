package com.julvez.pfc.teachonsnap.ut.link.repository;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.link.model.Link;
import com.julvez.pfc.teachonsnap.link.repository.LinkRepository;
import com.julvez.pfc.teachonsnap.link.repository.LinkRepositoryDB;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;

public class LinkRepositoryDBTest extends LinkRepositoryTest {

	@Mock
	private DBManager dbm;
	
	@Override
	protected LinkRepository getRepository() {
		return new LinkRepositoryDB(dbm);
	}
	
	@Test
	public void testGetMoreInfoLinkIDs() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		when(dbm.getQueryResultList(eq("SQL_LINK_GET_MOREINFOLINKIDS"), eq(Integer.class), eq(invalidIdLesson))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_LINK_GET_MOREINFOLINKIDS"), eq(Integer.class), eq(idLesson))).thenReturn(ids);

		super.testGetMoreInfoLinkIDs();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_LINK_GET_MOREINFOLINKIDS"), eq(Integer.class),anyInt());
	}

	@Test
	public void testGetSourceLinkIDs() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		when(dbm.getQueryResultList(eq("SQL_LINK_GET_SOURCELINKIDS"), eq(Integer.class), eq(invalidIdLesson))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_LINK_GET_SOURCELINKIDS"), eq(Integer.class), eq(idLesson))).thenReturn(ids);

		super.testGetSourceLinkIDs();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_LINK_GET_SOURCELINKIDS"), eq(Integer.class),anyInt());
	}

	@Test
	public void testGetLink() {
		Link link = new Link();	
		
		when(dbm.getQueryResultUnique(eq("SQL_LINK_GET_LINK"), eq(Link.class), eq(invalidIdLink))).thenReturn(null);
		when(dbm.getQueryResultUnique(eq("SQL_LINK_GET_LINK"), eq(Link.class), eq(idLink))).thenReturn(link);
		
		super.testGetLink();
		
		verify(dbm, times(2)).getQueryResultUnique(eq("SQL_LINK_GET_LINK"), eq(Link.class), anyInt());
	}

	@Test
	public void testGetLinkID() {
		when(dbm.getQueryResultUnique(eq("SQL_LINK_GET_LINKID"), eq(Integer.class), eq(url))).thenReturn(idLink);
		super.testGetLinkID();
		verify(dbm, times(4)).getQueryResultUnique(eq("SQL_LINK_GET_LINKID"), eq(Integer.class), anyString());
	}

	@Test
	public void testCreateLink() {
		when(dbm.insertQueryAndGetLastInserID(eq("SQL_LINK_CREATE_LINK"), anyString(), anyString())).thenReturn((long)invalidIdLink);
		when(dbm.insertQueryAndGetLastInserID(eq("SQL_LINK_CREATE_LINK"), eq(url), eq(url))).thenReturn((long)idLink);
		super.testCreateLink();
		verify(dbm, times(4)).insertQueryAndGetLastInserID(eq("SQL_LINK_CREATE_LINK"), anyString(), anyString());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testAddLessonSources() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(dbm.getQueryResultList(eq("SQL_LINK_GET_SOURCELINKIDS"), eq(Integer.class), eq(idLesson)))
			.thenReturn(null, null, null, ids);
		
		when(dbm.updateQuery(eq("SQL_LINK_SAVE_SOURCE"), anyInt(), anyInt())).thenReturn(-1, -1, 1);

		super.testAddLessonSources();
		
		verify(dbm, times(2)).updateQuery(eq("SQL_LINK_SAVE_SOURCE"), anyInt(), anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testAddLessonMoreInfos() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(dbm.getQueryResultList(eq("SQL_LINK_GET_MOREINFOLINKIDS"), eq(Integer.class), eq(idLesson)))
			.thenReturn(null, null, null, ids);
		
		when(dbm.updateQuery(eq("SQL_LINK_SAVE_MOREINFO"), anyInt(), anyInt())).thenReturn(-1, -1, 1);

		super.testAddLessonMoreInfos();
		
		verify(dbm, times(2)).updateQuery(eq("SQL_LINK_SAVE_MOREINFO"), anyInt(), anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testRemoveLessonSources() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(dbm.getQueryResultList(eq("SQL_LINK_GET_SOURCELINKIDS"), eq(Integer.class), eq(idLesson)))
			.thenReturn(ids, ids, ids, null);
		
		super.testRemoveLessonSources();
		
		verify(dbm, times(2)).updateQuery(eq("SQL_LINK_DELETE_SOURCE"), anyInt(), anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testRemoveLessonMoreInfos() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(dbm.getQueryResultList(eq("SQL_LINK_GET_MOREINFOLINKIDS"), eq(Integer.class), eq(idLesson)))
			.thenReturn(ids, ids, ids, null);
		
		super.testRemoveLessonMoreInfos();
		
		verify(dbm, times(2)).updateQuery(eq("SQL_LINK_DELETE_MOREINFO"), anyInt(), anyInt());
	}
}
