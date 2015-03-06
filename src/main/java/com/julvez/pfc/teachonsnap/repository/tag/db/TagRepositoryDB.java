package com.julvez.pfc.teachonsnap.repository.tag.db;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.model.tag.Tag;
import com.julvez.pfc.teachonsnap.repository.tag.TagRepository;

public class TagRepositoryDB implements TagRepository {

	private DBManager dbm = DBManagerFactory.getDBManager();
	@Override
	public List<Integer> getLessonIDsFromTag(String tag,int firstResult) {
		
		@SuppressWarnings("unchecked")
		List<Integer> ids =  (List<Integer>) dbm.getQueryResultList("SQL_TAG_GET_LESSONIDS_FROM_TAG", null, tag,firstResult);
						
		return ids; 
	}

	@Override
	public List<Integer> getLessonTagIDs(int idLesson) {
		@SuppressWarnings("unchecked")
		List<Integer> ids =  (List<Integer>) dbm.getQueryResultList("SQL_TAG_GET_LESSONTAGIDS", null, idLesson);
						
		return ids;
	}
	
	@Override
	public Tag getTag(int idTag) {
		return (Tag) dbm.getQueryResultUnique("SQL_TAG_GET_TAG", Tag.class, idTag);
	}
	
	@Override
	public List<Object[]> getCloudTags() {
		@SuppressWarnings("unchecked")
		List<Object[]> ids =  (List<Object[]>) dbm.getQueryResultList("SQL_TAG_GET_CLOUDTAG_TAG", null, new Object[0]);
		return ids;
	}

	@Override
	public List<Object[]> getAuthorCloudTags() {
		@SuppressWarnings("unchecked")
		List<Object[]> ids =  (List<Object[]>) dbm.getQueryResultList("SQL_TAG_GET_CLOUDTAG_AUTHOR", null, new Object[0]);
		return ids;
	}
	
	@Override
	public void addLessonTags(int idLesson, ArrayList<Integer> tagIDs) {
		for(int tagID:tagIDs){
			dbm.updateQuery("SQL_TAG_SAVE_TAG", idLesson,tagID);
		}		
	}

	@Override
	public int getTagID(String tag) {
		int id = -1;
		Object obj = dbm.getQueryResultUnique("SQL_TAG_GET_TAGID", null, tag);
		if(obj!=null)
			id = Integer.parseInt(obj.toString());
		return id; 	
	}

	@Override
	public int createTag(String tag) {
		return (int)dbm.updateQuery("SQL_TAG_CREATE_TAG", tag);
	}
}
