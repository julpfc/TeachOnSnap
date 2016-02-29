package com.julvez.pfc.teachonsnap.ut.tag.repository;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.lesson.model.LessonPropertyName;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.tag.model.TagPropertyName;
import com.julvez.pfc.teachonsnap.tag.repository.TagRepository;
import com.julvez.pfc.teachonsnap.tag.repository.TagRepositoryDB;

public class TagRepositoryDBTest extends TagRepositoryTest {

	@Mock
	private DBManager dbm;
	
	@Mock
	private PropertyManager properties;
	
	@Override
	protected TagRepository getRepository() {
		return new TagRepositoryDB(dbm, properties);
	}

	@Test
	public void testGetLessonIDsFromTag() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids.add(3);
		ids.add(4);
		
		when(properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS)).thenReturn((long)2);
		
		when(dbm.getQueryResultList(eq("SQL_TAG_GET_LESSONIDS_FROM_TAG"), eq(Integer.class), anyString(), anyInt(), eq((long)3))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_TAG_GET_LESSONIDS_FROM_TAG"), eq(Integer.class), eq(query), eq(FIRST_RESULT), eq((long)3))).thenReturn(ids);
		when(dbm.getQueryResultList(eq("SQL_TAG_GET_LESSONIDS_FROM_TAG"), eq(Integer.class), eq(query), eq(SECOND_RESULT), eq((long)3))).thenReturn(ids2);

		super.testGetLessonIDsFromTag();
		
		verify(dbm, times(6)).getQueryResultList(eq("SQL_TAG_GET_LESSONIDS_FROM_TAG"), eq(Integer.class),anyString(), anyInt(),anyInt());
		verify(properties, times(6)).getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
	}

	@Test
	public void testGetLessonTagIDs() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		when(dbm.getQueryResultList(eq("SQL_TAG_GET_LESSONTAGIDS"), eq(Integer.class), eq(invalidIdLesson))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_TAG_GET_LESSONTAGIDS"), eq(Integer.class), eq(idLesson))).thenReturn(ids);

		super.testGetLessonTagIDs();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_TAG_GET_LESSONTAGIDS"), eq(Integer.class),anyInt());
	}

	@Test
	public void testGetTag() {
		Tag tag = new Tag();	
		
		when(dbm.getQueryResultUnique(eq("SQL_TAG_GET_TAG"), eq(Tag.class), eq(invalidIdTag))).thenReturn(null);
		when(dbm.getQueryResultUnique(eq("SQL_TAG_GET_TAG"), eq(Tag.class), eq(idTag))).thenReturn(tag);
		
		super.testGetTag();
		
		verify(dbm, times(2)).getQueryResultUnique(eq("SQL_TAG_GET_TAG"), eq(Tag.class), anyInt());
	}

	@Test
	public void testGetTagUseCloudTags() {
		List<Object[]> ids = new ArrayList<Object[]>();
		ids.add(new Object[]{1,0});
		ids.add(new Object[]{2,0});
		
		when(properties.getNumericProperty(TagPropertyName.LIMIT_CLOUDTAG)).thenReturn((long)2);
		when(dbm.getQueryResultList(eq("SQL_TAG_GET_CLOUDTAG_USE_TAG"), eq(Object[].class), anyInt())).thenReturn(ids);
		super.testGetTagUseCloudTags();
		verify(dbm).getQueryResultList(eq("SQL_TAG_GET_CLOUDTAG_USE_TAG"), eq(Object[].class), anyInt());

		verify(properties).getNumericProperty(TagPropertyName.LIMIT_CLOUDTAG);
	}

	@Test
	public void testGetAuthorCloudTags() {
		List<Object[]> ids = new ArrayList<Object[]>();
		ids.add(new Object[]{1,0});
		ids.add(new Object[]{2,0});
		
		when(properties.getNumericProperty(TagPropertyName.LIMIT_CLOUDTAG)).thenReturn((long)2);
		when(dbm.getQueryResultList(eq("SQL_TAG_GET_CLOUDTAG_AUTHOR"), eq(Object[].class), anyInt())).thenReturn(ids);
		super.testGetAuthorCloudTags();
		verify(dbm).getQueryResultList(eq("SQL_TAG_GET_CLOUDTAG_AUTHOR"), eq(Object[].class), anyInt());

		verify(properties).getNumericProperty(TagPropertyName.LIMIT_CLOUDTAG);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testAddLessonTags() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(dbm.getQueryResultList(eq("SQL_TAG_GET_LESSONTAGIDS"), eq(Integer.class), eq(idLesson)))
			.thenReturn(null, null, null, ids);
		
		when(dbm.updateQuery(eq("SQL_TAG_SAVE_LESSON_TAG"), anyInt(), anyInt())).thenReturn(-1, -1, 1);

		super.testAddLessonTags();
		
		verify(dbm, times(2)).updateQuery(eq("SQL_TAG_SAVE_LESSON_TAG"), anyInt(), anyInt());
	}

	@Test
	public void testGetTagID() {
		when(dbm.getQueryResultUnique(eq("SQL_TAG_GET_TAGID"), eq(Integer.class), eq(query))).thenReturn(idTag);
		super.testGetTagID();
		verify(dbm, times(4)).getQueryResultUnique(eq("SQL_TAG_GET_TAGID"), eq(Integer.class), anyString());
	}

	@Test
	public void testCreateTag() {
		when(dbm.insertQueryAndGetLastInserID(eq("SQL_TAG_CREATE_TAG"), anyString())).thenReturn((long)invalidIdTag);
		when(dbm.insertQueryAndGetLastInserID(eq("SQL_TAG_CREATE_TAG"), eq(query))).thenReturn((long)idTag);
		super.testCreateTag();
		verify(dbm, times(4)).insertQueryAndGetLastInserID(eq("SQL_TAG_CREATE_TAG"), anyString());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testRemoveLessonTags() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(dbm.getQueryResultList(eq("SQL_TAG_GET_LESSONTAGIDS"), eq(Integer.class), eq(idLesson)))
			.thenReturn(ids, ids, ids, null);
		
		super.testRemoveLessonTags();
		
		verify(dbm, times(2)).updateQuery(eq("SQL_TAG_DELETE_LESSON_TAG"), anyInt(), anyInt());
	}

	@Test
	public void testSearchTag() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids.add(3);
		ids.add(4);
		
		when(properties.getNumericProperty(TagPropertyName.LIMIT_CLOUDTAG)).thenReturn((long)2);
		
		when(dbm.getQueryResultList(eq("SQL_TAG_SEARCH_TAGIDS"), eq(Integer.class), anyString(), anyInt(), eq((long)3))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_TAG_SEARCH_TAGIDS"), eq(Integer.class), eq(query), eq(FIRST_RESULT), eq((long)3))).thenReturn(ids);
		when(dbm.getQueryResultList(eq("SQL_TAG_SEARCH_TAGIDS"), eq(Integer.class), eq(query), eq(SECOND_RESULT), eq((long)3))).thenReturn(ids2);

		super.testSearchTag();
		
		verify(dbm, times(6)).getQueryResultList(eq("SQL_TAG_SEARCH_TAGIDS"), eq(Integer.class),anyString(), anyInt(),anyInt());
		verify(properties, times(6)).getNumericProperty(TagPropertyName.LIMIT_CLOUDTAG);
	}

	@Test
	public void testGetTags() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids.add(3);
		ids.add(4);
		
		when(properties.getNumericProperty(TagPropertyName.LIMIT_CLOUDTAG)).thenReturn((long)2);
		
		when(dbm.getQueryResultList(eq("SQL_TAG_GET_TAGIDS"), eq(Integer.class), eq(INVALID_RESULT), eq((long)3))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_TAG_GET_TAGIDS"), eq(Integer.class), eq(FIRST_RESULT), eq((long)3))).thenReturn(ids);
		when(dbm.getQueryResultList(eq("SQL_TAG_GET_TAGIDS"), eq(Integer.class), eq(SECOND_RESULT), eq((long)3))).thenReturn(ids2);

		super.testGetTags();
		
		verify(dbm, times(3)).getQueryResultList(eq("SQL_TAG_GET_TAGIDS"), eq(Integer.class),anyInt(),anyInt());
		verify(properties, times(3)).getNumericProperty(TagPropertyName.LIMIT_CLOUDTAG);
	}

	@Test
	public void testGetTagSearchCloudTags() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		when(properties.getNumericProperty(TagPropertyName.LIMIT_CLOUDTAG)).thenReturn((long)2);
		when(dbm.getQueryResultList(eq("SQL_TAG_GET_CLOUDTAG_SEARCH_TAGIDS"), eq(Integer.class), anyInt())).thenReturn(ids);
		super.testGetTagSearchCloudTags();
		verify(dbm).getQueryResultList(eq("SQL_TAG_GET_CLOUDTAG_SEARCH_TAGIDS"), eq(Integer.class), anyInt());

		verify(properties).getNumericProperty(TagPropertyName.LIMIT_CLOUDTAG);
	}

	@Test
	public void testGetLessonViewCloudTags() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		when(properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS)).thenReturn((long)2);
		when(dbm.getQueryResultList(eq("SQL_TAG_GET_CLOUDTAG_VIEW_LESSONIDS"), eq(Integer.class), anyInt())).thenReturn(ids);
		super.testGetLessonViewCloudTags();
		verify(dbm).getQueryResultList(eq("SQL_TAG_GET_CLOUDTAG_VIEW_LESSONIDS"), eq(Integer.class), anyInt());

		verify(properties).getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
	}
}
