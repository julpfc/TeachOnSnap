package com.julvez.pfc.teachonsnap.tag.repository;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.LessonPropertyName;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.tag.model.TagPropertyName;

/**
 * Repository implementation to access/modify data from a Database
 * <p>
 * {@link DBManager} is used to provide database access
 */
public class TagRepositoryDB implements TagRepository {

	/** Database manager providing access/modification capabilities */
	private DBManager dbm;
	
	/** Property manager providing access to properties files */
	private PropertyManager properties;
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param dbm Database manager providing access/modification capabilities
	 * @param properties Property manager providing access to properties files
	 */
	public TagRepositoryDB(DBManager dbm, PropertyManager properties) {
		if(dbm == null || properties == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.dbm = dbm;
		this.properties = properties;
	}

	@Override
	public List<Integer> getLessonIDsFromTag(String tag,int firstResult) {
		long maxResults = properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
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
	public List<Object[]> getTagUseCloudTags() {
		long limit = properties.getNumericProperty(TagPropertyName.LIMIT_CLOUDTAG);		
		return  dbm.getQueryResultList("SQL_TAG_GET_CLOUDTAG_USE_TAG", Object[].class, limit);		
	}

	@Override
	public List<Object[]> getAuthorCloudTags() {
		long limit = properties.getNumericProperty(TagPropertyName.LIMIT_CLOUDTAG);
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

	@Override
	public List<Integer> searchTag(String searchQuery, int firstResult) {
		long limit = properties.getNumericProperty(TagPropertyName.LIMIT_CLOUDTAG);
		return (List<Integer>)dbm.getQueryResultList("SQL_TAG_SEARCH_TAGIDS", Integer.class, searchQuery, firstResult, limit+1);
	}

	@Override
	public List<Integer> getTags(int firstResult) {
		long limit = properties.getNumericProperty(TagPropertyName.LIMIT_CLOUDTAG);
		return (List<Integer>)dbm.getQueryResultList("SQL_TAG_GET_TAGIDS", Integer.class, firstResult, limit+1);
	}

	@Override
	public List<Integer> getTagSearchCloudTags() {
		long limit = properties.getNumericProperty(TagPropertyName.LIMIT_CLOUDTAG);		
		return  dbm.getQueryResultList("SQL_TAG_GET_CLOUDTAG_SEARCH_TAGIDS", Integer.class, limit);	
	}

	@Override
	public List<Integer> getLessonViewCloudTags() {
		long maxResults = properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);		
		return  dbm.getQueryResultList("SQL_TAG_GET_CLOUDTAG_VIEW_LESSONIDS", Integer.class, maxResults);	
	}
}
