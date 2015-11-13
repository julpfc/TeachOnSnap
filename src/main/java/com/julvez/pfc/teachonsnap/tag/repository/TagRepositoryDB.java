package com.julvez.pfc.teachonsnap.tag.repository;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.LessonPropertyName;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.tag.model.TagPropertyName;

public class TagRepositoryDB implements TagRepository {

	private DBManager dbm = DBManagerFactory.getDBManager();
	private PropertyManager properties = PropertyManagerFactory.getManager();
	
	@Override
	public List<Integer> getLessonIDsFromTag(String tag,int firstResult) {
		int maxResults = properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
		return dbm.getQueryResultList("SQL_TAG_GET_LESSONIDS_FROM_TAG", Integer.class, tag,firstResult, maxResults + 1);
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
		int limit = properties.getNumericProperty(TagPropertyName.LIMIT_CLOUDTAG);		
		return  dbm.getQueryResultList("SQL_TAG_GET_CLOUDTAG_TAG", Object[].class, limit);		
	}

	@Override
	public List<Object[]> getAuthorCloudTags() {
		int limit = properties.getNumericProperty(TagPropertyName.LIMIT_CLOUDTAG);
		return dbm.getQueryResultList("SQL_TAG_GET_CLOUDTAG_AUTHOR", Object[].class, limit);		
	}
	
	@Override
	public void addLessonTags(int idLesson, ArrayList<Integer> tagIDs) {
		for(int tagID:tagIDs){
			dbm.updateQuery("SQL_TAG_SAVE_LESSON_TAG", idLesson,tagID);
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
		return (int)dbm.insertQueryAndGetLastInserID("SQL_TAG_CREATE_TAG", tag);
	}

	@Override
	public void removeLessonTags(int idLesson, ArrayList<Integer> removeTagIDs) {
		for(int tagID:removeTagIDs){
			dbm.updateQuery("SQL_TAG_DELETE_LESSON_TAG", idLesson,tagID);
		}		
		
	}
}
