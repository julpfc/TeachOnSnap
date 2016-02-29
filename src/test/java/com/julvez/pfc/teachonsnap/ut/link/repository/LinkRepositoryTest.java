package com.julvez.pfc.teachonsnap.ut.link.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.link.model.Link;
import com.julvez.pfc.teachonsnap.link.repository.LinkRepository;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryTest;

public abstract class LinkRepositoryTest extends RepositoryTest<LinkRepository> {

	protected int idLesson = 1;
	protected int invalidIdLesson = -1;
	
	protected int idLink = 1;
	protected int invalidIdLink = -1;
	protected String url = "url";
	
	@Test
	public void testGetMoreInfoLinkIDs() {
		List<Integer> ids = test.getMoreInfoLinkIDs(invalidIdLesson);		
		assertNull(ids);

		ids = test.getMoreInfoLinkIDs(idLesson);		
		assertNotNull(ids);
		
		int i=1;
		for(int b:ids){
			assertEquals(i++, b);
		}
	}

	@Test
	public void testGetSourceLinkIDs() {
		List<Integer> ids = test.getSourceLinkIDs(invalidIdLesson);		
		assertNull(ids);

		ids = test.getSourceLinkIDs(idLesson);		
		assertNotNull(ids);
		
		int i=1;
		for(int b:ids){
			assertEquals(i++, b);
		}
	}

	@Test
	public void testGetLink() {
		Link link = test.getLink(idLink);
		assertNotNull(link);
				
		assertNull(test.getLink(invalidIdLink));
	}

	@Test
	public void testGetLinkID() {
		assertEquals(idLink, test.getLinkID(url));
		
		assertEquals(invalidIdLink, test.getLinkID(NULL_STRING));
		assertEquals(invalidIdLink, test.getLinkID(EMPTY_STRING));
		assertEquals(invalidIdLink, test.getLinkID(BLANK_STRING));
	}

	@Test
	public void testCreateLink() {
		assertEquals(idLink, test.createLink(url, url));
		
		assertEquals(invalidIdLink, test.createLink(NULL_STRING, EMPTY_STRING));
		assertEquals(invalidIdLink, test.createLink(EMPTY_STRING, EMPTY_STRING));
		assertEquals(invalidIdLink, test.createLink(BLANK_STRING, EMPTY_STRING));
	}

	@Test
	public void testAddLessonSources() {
		ArrayList<Integer> sourceIDs = new ArrayList<Integer>();
		sourceIDs.add(idLink);
		
		List<Integer> ids = test.getSourceLinkIDs(idLesson);		
		assertNull(ids);
		
		test.addLessonSources(invalidIdLesson, sourceIDs);
		
		ids = test.getSourceLinkIDs(idLesson);		
		assertNull(ids);
		
		test.addLessonSources(idLesson, null);
		
		ids = test.getSourceLinkIDs(idLesson);		
		assertNull(ids);
		
		test.addLessonSources(idLesson, sourceIDs);
		
		ids = test.getSourceLinkIDs(idLesson);		
		assertNotNull(ids);
		assertEquals(idLink, (int)ids.get(0));
	}

	@Test
	public void testAddLessonMoreInfos() {
		ArrayList<Integer> moreIDs = new ArrayList<Integer>();
		moreIDs.add(idLink);
		
		List<Integer> ids = test.getMoreInfoLinkIDs(idLesson);		
		assertNull(ids);
		
		test.addLessonMoreInfos(invalidIdLesson, moreIDs);
		
		ids = test.getMoreInfoLinkIDs(idLesson);		
		assertNull(ids);
		
		test.addLessonMoreInfos(idLesson, null);
		
		ids = test.getMoreInfoLinkIDs(idLesson);		
		assertNull(ids);
		
		test.addLessonMoreInfos(idLesson, moreIDs);
		
		ids = test.getMoreInfoLinkIDs(idLesson);		
		assertNotNull(ids);
		assertEquals(idLink, (int)ids.get(0));
	}

	@Test
	public void testRemoveLessonSources() {
		List<Integer> ids = test.getSourceLinkIDs(idLesson);		
		assertNotNull(ids);
		assertEquals(idLink, (int)ids.get(0));
		
		test.removeLessonSources(invalidIdLesson, (ArrayList<Integer>)ids);
		
		ids = test.getSourceLinkIDs(idLesson);		
		assertNotNull(ids);
		assertEquals(idLink, (int)ids.get(0));
		
		test.removeLessonSources(idLesson, null);
		
		ids = test.getSourceLinkIDs(idLesson);		
		assertNotNull(ids);
		assertEquals(idLink, (int)ids.get(0));
		
		test.removeLessonSources(idLesson, (ArrayList<Integer>)ids);
		
		ids = test.getSourceLinkIDs(idLesson);		
		assertNull(ids);		
	}

	@Test
	public void testRemoveLessonMoreInfos() {
		List<Integer> ids = test.getMoreInfoLinkIDs(idLesson);
		assertNotNull(ids);
		assertEquals(idLink, (int)ids.get(0));
		
		test.removeLessonMoreInfos(invalidIdLesson, (ArrayList<Integer>)ids);
		
		ids = test.getMoreInfoLinkIDs(idLesson);		
		assertNotNull(ids);
		assertEquals(idLink, (int)ids.get(0));
		
		test.removeLessonMoreInfos(idLesson, null);
		
		ids = test.getMoreInfoLinkIDs(idLesson);		
		assertNotNull(ids);
		assertEquals(idLink, (int)ids.get(0));
		
		test.removeLessonMoreInfos(idLesson, (ArrayList<Integer>)ids);
		
		ids = test.getMoreInfoLinkIDs(idLesson);		
		assertNull(ids);		
	}

}
