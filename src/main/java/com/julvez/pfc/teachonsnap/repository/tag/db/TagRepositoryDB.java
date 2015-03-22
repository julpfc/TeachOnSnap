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
		return dbm.getQueryResultList("SQL_TAG_GET_LESSONIDS_FROM_TAG", Integer.class, tag,firstResult);
	}

	@Override
	public List<Integer> getLessonTagIDs(int idLesson) {
		return dbm.getQueryResultList("SQL_TAG_GET_LESSONTAGIDS", Integer.class, idLesson);
	}
	
	@Override
	public Tag getTag(int idTag) {
		return dbm.getQueryResultUnique("SQL_TAG_GET_TAG", Tag.class, idTag);
	}
	
	@Override
	public List<Object[]> getCloudTags() {
		return  dbm.getQueryResultList("SQL_TAG_GET_CLOUDTAG_TAG", Object[].class, new Object[0]);		
	}

	@Override
	public List<Object[]> getAuthorCloudTags() {
		return dbm.getQueryResultList("SQL_TAG_GET_CLOUDTAG_AUTHOR", Object[].class, new Object[0]);		
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
		Integer obj = dbm.getQueryResultUnique("SQL_TAG_GET_TAGID", Integer.class, tag);
		if(obj!=null)
			id = obj;
		return id; 	
	}

	@Override
	public int createTag(String tag) {
		return (int)dbm.updateQuery("SQL_TAG_CREATE_TAG", tag);
	}
}
