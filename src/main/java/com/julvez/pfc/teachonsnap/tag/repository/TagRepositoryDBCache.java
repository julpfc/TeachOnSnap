package com.julvez.pfc.teachonsnap.tag.repository;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.tag.model.Tag;

public class TagRepositoryDBCache implements TagRepository {

	private TagRepositoryDB repoDB = new TagRepositoryDB();
	private CacheManager cache = CacheManagerFactory.getCacheManager();
	private StringManager stringManager = StringManagerFactory.getManager();

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
	public List<Object[]> getCloudTags() {
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
		
		cache.clearCache("getLessonIDsFromTag");
		cache.clearCache("getCloudTags");			
	}

	@Override
	public int getTagID(String tag) {
		return (int)cache.executeImplCached(repoDB, tag);		
	}

	@Override
	public int createTag(String tag) {
		return (int)cache.updateImplCached(repoDB, null, null, tag);
	}

	@Override
	public void removeLessonTags(int idLesson, ArrayList<Integer> removeTagIDs) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getLessonTagIDs"}, idLesson, removeTagIDs);
		
		cache.clearCache("getLessonIDsFromTag");
		cache.clearCache("getCloudTags");
		
	}
}
