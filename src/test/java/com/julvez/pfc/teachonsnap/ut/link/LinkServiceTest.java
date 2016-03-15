package com.julvez.pfc.teachonsnap.ut.link;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.link.LinkService;
import com.julvez.pfc.teachonsnap.link.model.Link;
import com.julvez.pfc.teachonsnap.ut.service.ServiceTest;

public abstract class LinkServiceTest extends ServiceTest<LinkService> {

	protected int idLesson = 1;
	protected int invalidIdLesson = -1;
	
	protected int idLink = 1;	
	protected int idLink2 = 2;	
	protected int invalidIdLink = -1;
	protected String url = "url";
	protected String url2 = "url2";
	
	
	@Test
	public void testGetLink() {
		Link link = test.getLink(idLink);
		assertNotNull(link);
				
		assertNull(test.getLink(invalidIdLink));
	}

	@Test
	public void testGetMoreInfoLinks() {
		List<Link> links = test.getMoreInfoLinks(invalidIdLesson);		
		assertNotNull(links);
		assertEquals(0, links.size());

		links = test.getMoreInfoLinks(idLesson);		
		assertNotNull(links);
		
		int i=1;
		for(Link l:links){
			assertEquals(i++, l.getId());
		}
	}

	@Test
	public void testGetSourceLinks() {
		List<Link> links = test.getSourceLinks(invalidIdLesson);		
		assertNotNull(links);
		assertEquals(0, links.size());

		links = test.getSourceLinks(idLesson);		
		assertNotNull(links);
		
		int i=1;
		for(Link l:links){
			assertEquals(i++, l.getId());
		}
	}

	@Test
	public void testAddLessonSources() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		
		List<String> sources = new ArrayList<String>();
		sources.add(url);
		
		List<Link> links = test.getSourceLinks(idLesson);		
		assertNotNull(links);
		assertEquals(0, links.size());
		
		test.addLessonSources(null, sources);
		
		links = test.getSourceLinks(idLesson);		
		assertNotNull(links);
		assertEquals(0, links.size());
		
		test.addLessonSources(lesson, null);
		
		links = test.getSourceLinks(idLesson);		
		assertNotNull(links);
		assertEquals(0, links.size());
		
		test.addLessonSources(lesson, sources);
		
		links = test.getSourceLinks(idLesson);		
		assertNotNull(links);
		assertEquals(idLink, links.get(0).getId());
	}

	@Test
	public void testCreateLink() {
		assertEquals(idLink2, test.createLink(url2));
		
		assertEquals(invalidIdLink, test.createLink(NULL_STRING));
		assertEquals(invalidIdLink, test.createLink(EMPTY_STRING));
		assertEquals(invalidIdLink, test.createLink(BLANK_STRING));
	}

	@Test
	public void testAddLessonMoreInfo() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		
		List<String> sources = new ArrayList<String>();
		sources.add(url);
		
		List<Link> links = test.getMoreInfoLinks(idLesson);		
		assertNotNull(links);
		assertEquals(0, links.size());
		
		test.addLessonMoreInfo(null, sources);
		
		links = test.getMoreInfoLinks(idLesson);		
		assertNotNull(links);
		assertEquals(0, links.size());
		
		test.addLessonMoreInfo(lesson, null);
		
		links = test.getMoreInfoLinks(idLesson);		
		assertNotNull(links);
		assertEquals(0, links.size());
		
		test.addLessonMoreInfo(lesson, sources);
		
		links = test.getMoreInfoLinks(idLesson);		
		assertNotNull(links);
		assertEquals(idLink, links.get(0).getId());
	}

	@Test
	public void testSaveLessonMoreInfo() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		
		List<String> moreInfos = new ArrayList<String>();
		moreInfos.add(url);
		
		List<Link> links = test.getMoreInfoLinks(idLesson);		
		assertNotNull(links);
		assertEquals(0, links.size());			
		
		test.saveLessonMoreInfo(null, null, null);
		
		links = test.getMoreInfoLinks(idLesson);		
		assertNotNull(links);
		assertEquals(0, links.size());
		
		test.saveLessonMoreInfo(lesson, links, moreInfos);
		
		links = test.getMoreInfoLinks(idLesson);		
		assertNotNull(links);
		assertEquals(idLink, links.get(0).getId());	
	}

	@Test
	public void testSaveLessonSources() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		
		List<String> sources = new ArrayList<String>();
		sources.add(url);
		
		List<Link> links = test.getSourceLinks(idLesson);		
		assertNotNull(links);
		assertEquals(0, links.size());			
		
		test.saveLessonSources(null, null, null);
		
		links = test.getSourceLinks(idLesson);		
		assertNotNull(links);
		assertEquals(0, links.size());
		
		test.saveLessonSources(lesson, links, sources);
		
		links = test.getSourceLinks(idLesson);		
		assertNotNull(links);
		assertEquals(idLink, links.get(0).getId());	
	}

	@Test
	public void testRemoveLessonSources() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		
		ArrayList<Integer> sourceIDs = new ArrayList<Integer>();
		sourceIDs.add(idLink);
		
		List<Link> links = test.getSourceLinks(idLesson);		
		assertNotNull(links);
		assertEquals(idLink, links.get(0).getId());
		
		test.removeLessonSources(null, sourceIDs);
		
		links = test.getSourceLinks(idLesson);		
		assertNotNull(links);
		assertEquals(idLink, links.get(0).getId());
		
		test.removeLessonSources(lesson, null);
		
		links = test.getSourceLinks(idLesson);		
		assertNotNull(links);
		assertEquals(idLink, links.get(0).getId());
		
		test.removeLessonSources(lesson, sourceIDs);
		
		links = test.getSourceLinks(idLesson);		
		assertNotNull(links);
		assertEquals(0, links.size());	
	}

	@Test
	public void testRemoveLessonMoreInfos() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		
		ArrayList<Integer> moreIDs = new ArrayList<Integer>();
		moreIDs.add(idLink);
		
		List<Link> links = test.getMoreInfoLinks(idLesson);		
		assertNotNull(links);
		assertEquals(idLink, links.get(0).getId());
		
		test.removeLessonMoreInfos(null, moreIDs);
		
		links = test.getMoreInfoLinks(idLesson);		
		assertNotNull(links);
		assertEquals(idLink, links.get(0).getId());
		
		test.removeLessonMoreInfos(lesson, null);
		
		links = test.getMoreInfoLinks(idLesson);		
		assertNotNull(links);
		assertEquals(idLink, links.get(0).getId());
		
		test.removeLessonMoreInfos(lesson, moreIDs);
		
		links = test.getMoreInfoLinks(idLesson);		
		assertNotNull(links);
		assertEquals(0, links.size());
	}
}
