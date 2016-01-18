package com.julvez.pfc.teachonsnap.link.repository;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.link.model.Link;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;

/**
 * Repository implementation to access/modify data from a Database through a cache.
 * <p>
 * A repository database implementation ({@link LinkRepositoryDB}) is used to provide the database layer under the cache.
 * <p>
 * {@link CacheManager} is used to provide a cache system
 */
public class LinkRepositoryDBCache implements LinkRepository {

	/** Database repository providing data access and modification capabilities */
	private LinkRepositoryDB repoDB;
	
	/** Cache manager providing access/modification capabilities to the cache system */
	private CacheManager cache;
	
	/** String manager providing string manipulation utilities */
	private StringManager stringManager;
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param repoDB Database repository providing data access and modification capabilities
	 * @param cache Cache manager providing access/modification capabilities to the cache system
	 * @param stringManager String manager providing string manipulation utilities
	 */
	public LinkRepositoryDBCache(LinkRepositoryDB repoDB, CacheManager cache,
			StringManager stringManager) {
		
		if(repoDB == null || stringManager == null || cache == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.repoDB = repoDB;
		this.cache = cache;
		this.stringManager = stringManager;
	}

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
	public int getLinkID(String url) {
		return (int)cache.executeImplCached(repoDB, url);		
	}

	@Override
	public int createLink(String url, String desc) {
		return (int)cache.updateImplCached(repoDB, null, null, url, desc);
	}

	@Override
	public void addLessonSources(int idLesson, ArrayList<Integer> sourceLinkIDs) {
		//Add new links to the lesson and clear this lesson related caches
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getSourceLinkIDs"}, idLesson, sourceLinkIDs);		
	}

	@Override
	public void addLessonMoreInfos(int idLesson, ArrayList<Integer> moreInfoLinkIDs) {
		//Add new links to the lesson and clear this lesson related caches
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getMoreInfoLinkIDs"}, idLesson, moreInfoLinkIDs);		
	}

	@Override
	public void removeLessonSources(int idLesson, ArrayList<Integer> removeLinkIDs) {
		//Remove links from the lesson and clear this lesson related caches
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getSourceLinkIDs"}, idLesson, removeLinkIDs);		
	}

	@Override
	public void removeLessonMoreInfos(int idLesson, ArrayList<Integer> removeLinkIDs) {
		//Remove links from the lesson and clear this lesson related caches
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getMoreInfoLinkIDs"}, idLesson, removeLinkIDs);			
	}

}
