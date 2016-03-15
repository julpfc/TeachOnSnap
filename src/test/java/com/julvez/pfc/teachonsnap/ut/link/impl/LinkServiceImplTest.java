package com.julvez.pfc.teachonsnap.ut.link.impl;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.link.LinkService;
import com.julvez.pfc.teachonsnap.link.impl.LinkServiceImpl;
import com.julvez.pfc.teachonsnap.link.model.Link;
import com.julvez.pfc.teachonsnap.link.repository.LinkRepository;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.ut.link.LinkServiceTest;

public class LinkServiceImplTest extends LinkServiceTest {

	@Mock
	private LinkRepository linkRepository;
	
	@Mock
	private LogManager logger;
	
	@Mock
	private StringManager stringManager;
	
	@Override
	protected LinkService getService() {
		return new LinkServiceImpl(linkRepository, logger, stringManager);
	}
	
	@Test
	public void testGetMoreInfoLinks() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(idLink);		
		when(linkRepository.getMoreInfoLinkIDs(idLesson)).thenReturn(ids);
		
		Link link = mock(Link.class);
		when(link.getId()).thenReturn(idLink);
		
		when(linkRepository.getLink(idLink)).thenReturn(link);
		
		super.testGetMoreInfoLinks();
		
		verify(linkRepository, times(2)).getMoreInfoLinkIDs(anyInt());
	}

	@Test
	public void testGetSourceLinks() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(idLink);		
		when(linkRepository.getSourceLinkIDs(idLesson)).thenReturn(ids);
		
		Link link = mock(Link.class);
		when(link.getId()).thenReturn(idLink);
		
		when(linkRepository.getLink(idLink)).thenReturn(link);
		
		super.testGetSourceLinks();
		
		verify(linkRepository, times(2)).getSourceLinkIDs(anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testAddLessonSources() {
		when(linkRepository.getLinkID("http://" + url)).thenReturn(idLink);
		
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(idLink);		
		when(linkRepository.getSourceLinkIDs(idLesson)).thenReturn(null, null, null, ids);
		
		Link link = mock(Link.class);
		when(link.getId()).thenReturn(idLink);
		
		when(linkRepository.getLink(idLink)).thenReturn(link);
				
		super.testAddLessonSources();
		
		verify(linkRepository).addLessonSources(anyInt(), (ArrayList<Integer>)anyObject());
	}

	@Test
	public void testCreateLink() {
		when(linkRepository.createLink(anyString(), anyString())).thenReturn(invalidIdLink);
		when(linkRepository.createLink("http://" + url2, url2)).thenReturn(idLink2);
		super.testCreateLink();
		
		verify(linkRepository, times(3)).createLink(anyString(), anyString());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testAddLessonMoreInfo() {
		when(linkRepository.getLinkID("http://" + url)).thenReturn(idLink);
		
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(idLink);		
		when(linkRepository.getMoreInfoLinkIDs(idLesson)).thenReturn(null, null, null, ids);
		
		Link link = mock(Link.class);
		when(link.getId()).thenReturn(idLink);
		
		when(linkRepository.getLink(idLink)).thenReturn(link);
				
		super.testAddLessonMoreInfo();
		
		verify(linkRepository).addLessonMoreInfos(anyInt(), (ArrayList<Integer>)anyObject());
	}

	@Test
	public void testGetLink() {
		Link link = mock(Link.class);
		when(link.getId()).thenReturn(idLink);
		
		when(linkRepository.getLink(idLink)).thenReturn(link);
		super.testGetLink();
		verify(linkRepository).getLink(anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSaveLessonMoreInfo() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(idLink);		
		when(linkRepository.getMoreInfoLinkIDs(idLesson)).thenReturn(null, null, ids);
		
		Link link = mock(Link.class);
		when(link.getId()).thenReturn(idLink);
		
		when(linkRepository.getLink(idLink)).thenReturn(link);
		
		super.testSaveLessonMoreInfo();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSaveLessonSources() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(idLink);		
		when(linkRepository.getSourceLinkIDs(idLesson)).thenReturn(null, null, ids);
		
		Link link = mock(Link.class);
		when(link.getId()).thenReturn(idLink);
		
		when(linkRepository.getLink(idLink)).thenReturn(link);
		
		super.testSaveLessonSources();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testRemoveLessonSources() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(idLink);		
		when(linkRepository.getSourceLinkIDs(idLesson)).thenReturn(ids, ids, ids, null);
		
		Link link = mock(Link.class);
		when(link.getId()).thenReturn(idLink);
		
		when(linkRepository.getLink(idLink)).thenReturn(link);
		
		super.testRemoveLessonSources();
		
		verify(linkRepository).removeLessonSources(anyInt(), (ArrayList<Integer>)anyObject());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testRemoveLessonMoreInfos() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(idLink);		
		when(linkRepository.getMoreInfoLinkIDs(idLesson)).thenReturn(ids, ids, ids, null);
		
		Link link = mock(Link.class);
		when(link.getId()).thenReturn(idLink);
		
		when(linkRepository.getLink(idLink)).thenReturn(link);
		
		super.testRemoveLessonMoreInfos();
		
		verify(linkRepository).removeLessonMoreInfos(anyInt(), (ArrayList<Integer>)anyObject());
	}
}
