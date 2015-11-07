package com.julvez.pfc.teachonsnap.link.repository;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.link.model.Link;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

public class LinkRepositoryDBCache implements LinkRepository {

	private LinkRepositoryDB repoDB = new LinkRepositoryDB();
	private CacheManager cache = CacheManagerFactory.getCacheManager();
	private StringManager stringManager = StringManagerFactory.getManager();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getMoreInfoLinkIDs(int idLesson) {
		return (List<Integer>)cache.executeImplCached(repoDB, idLesson);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getSourceLinkIDs(int idLesson) {
		return (List<Integer>)cache.executeImplCached(repoDB, idLesson);
	}

	@Override
	public Link getLink(int idLink) {
		return (Link)cache.executeImplCached(repoDB, idLink);
	}

	@Override
	public int getLinkID(String link) {
		return (int)cache.executeImplCached(repoDB, link);		
	}

	@Override
	public int createLink(String url, String desc) {
		return (int)cache.updateImplCached(repoDB, null, null, url, desc);
	}

	@Override
	public void addLessonSources(int idLesson, ArrayList<Integer> sourceLinkIDs) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getSourceLinkIDs"}, idLesson, sourceLinkIDs);		
	}

	@Override
	public void addLessonMoreInfos(int idLesson, ArrayList<Integer> moreInfoLinkIDs) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getMoreInfoLinkIDs"}, idLesson, moreInfoLinkIDs);		
	}

}
