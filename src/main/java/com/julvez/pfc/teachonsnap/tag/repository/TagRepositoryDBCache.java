package com.julvez.pfc.teachonsnap.tag.repository;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.tag.model.Tag;

/**
 * Repository implementation to access/modify data from a Database through a cache.
 * <p>
 * A repository database implementation ({@link TagRepositoryDB}) is used to provide the database layer under the cache.
 * <p>
 * {@link CacheManager} is used to provide a cache system
 */
public class TagRepositoryDBCache implements TagRepository {

	/** Database repository providing data access and modification capabilities */
	private TagRepositoryDB repoDB;	
	
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
	public TagRepositoryDBCache(TagRepositoryDB repoDB, CacheManager cache,
			StringManager stringManager) {
		
		if(repoDB == null || stringManager == null || cache == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.repoDB = repoDB;
		this.cache = cache;
		this.stringManager = stringManager;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> getLessonIDsFromTag(String tag,int firstResult) {
		return (List<Integer>)cache.executeImplCached(repoDB, tag,firstResult);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> getLessonTagIDs(int idLesson) {
		return (List<Integer>)cache.executeImplCached(repoDB, idLesson);
	}
	
	@Override
	public Tag getTag(int idTag) {
		return (Tag)cache.executeImplCached(repoDB, idTag);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> getTagUseCloudTags() {
		return (List<Object[]>)cache.executeImplCached(repoDB, new Object[0]);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> getAuthorCloudTags() {
		return (List<Object[]>)cache.executeImplCached(repoDB, new Object[0]);
	}

	@Override
	public void addLessonTags(int idLesson, ArrayList<Integer> tagIDs) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getLessonTagIDs"}, idLesson, tagIDs);
		//clear lesson's tag related caches
		cache.clearCache("getLessonIDsFromTag");
		cache.clearCache("getTagUseCloudTags");			
		cache.clearCache("getTagSearchCloudTags");			
	}

	@Override
	public int getTagID(String tag) {
		return (int)cache.executeImplCached(repoDB, tag);		
	}

	@Override
	public int createTag(String tag) {
		//clear tag related caches
		cache.clearCache("getTags");
		cache.clearCache("searchTag");
		return (int)cache.updateImplCached(repoDB, null, null, tag);
	}

	@Override
	public void removeLessonTags(int idLesson, ArrayList<Integer> removeTagIDs) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getLessonTagIDs"}, idLesson, removeTagIDs);
		//clear lesson's tag related caches
		cache.clearCache("getLessonIDsFromTag");
		cache.clearCache("getCloudTags");
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> searchTag(String searchQuery, int firstResult) {		
		return (List<Integer>)cache.executeImplCached(repoDB, searchQuery, firstResult);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> getTags(int firstResult) {
		return (List<Integer>)cache.executeImplCached(repoDB, firstResult);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> getTagSearchCloudTags() {
		return (List<Integer>)cache.executeImplCached(repoDB, new Object[0]);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> getLessonViewCloudTags() {
		return (List<Integer>)cache.executeImplCached(repoDB, new Object[0]);
	}
}
