package com.julvez.pfc.teachonsnap.ut.tag.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.tag.repository.TagRepository;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryTest;

public abstract class TagRepositoryTest extends RepositoryTest<TagRepository> {

	protected int idTag = 1;
	protected int invalidIdTag = -1;

	protected int idLesson = 1;
	protected int invalidIdLesson = -1;
	protected String query = "tag";
	
	@Test
	public void testGetTag() {
		Tag tag = test.getTag(idTag);
		assertNotNull(tag);
				
		assertNull(test.getTag(invalidIdTag ));
	}

	@Test
	public void testGetLessonIDsFromTag() {
		List<Integer> ids = test.getLessonIDsFromTag(query , FIRST_RESULT);		
		assertNotNull(ids);
		
		int i=1;
		for(int b:ids){
			assertEquals(i++, b);
		}		
		
		ids = test.getLessonIDsFromTag(query, SECOND_RESULT);		
		assertNotNull(ids);
		
		for(int b:ids){
			assertEquals(i++, b);
		}
		
		ids = test.getLessonIDsFromTag(NULL_STRING, FIRST_RESULT);
		assertNull(ids);
		ids = test.getLessonIDsFromTag(EMPTY_STRING, FIRST_RESULT);
		assertNull(ids);
		ids = test.getLessonIDsFromTag(BLANK_STRING, FIRST_RESULT);
		assertNull(ids);
		
		ids = test.getLessonIDsFromTag(query, INVALID_RESULT);
		assertNull(ids);
	}

	@Test
	public void testGetLessonTagIDs() {
		List<Integer> ids = test.getLessonTagIDs(invalidIdLesson);		
		assertNull(ids);

		ids = test.getLessonTagIDs(idLesson);		
		assertNotNull(ids);
		
		int i=1;
		for(int b:ids){
			assertEquals(i++, b);
		}
	}

	@Test
	public void testGetTagUseCloudTags() {
		List<Object[]> ids = test.getTagUseCloudTags();
		
		assertNotNull(ids);
		
		int i=1;
		for(Object[] b:ids){
			assertEquals(i++, b[0]);
		}
	}

	@Test
	public void testGetAuthorCloudTags() {
		List<Object[]> ids = test.getAuthorCloudTags();
		
		assertNotNull(ids);
		
		int i=1;
		for(Object[] b:ids){
			assertEquals(i++, b[0]);
		}
	}

	@Test
	public void testAddLessonTags() {
		ArrayList<Integer> tagIDs = new ArrayList<Integer>();
		tagIDs.add(idTag);
		
		List<Integer> ids = test.getLessonTagIDs(idLesson);		
		assertNull(ids);
		
		test.addLessonTags(invalidIdLesson, tagIDs);
		
		ids = test.getLessonTagIDs(idLesson);		
		assertNull(ids);
		
		test.addLessonTags(idLesson, null);
		
		ids = test.getLessonTagIDs(idLesson);		
		assertNull(ids);
		
		test.addLessonTags(idLesson, tagIDs);
		
		ids = test.getLessonTagIDs(idLesson);		
		assertNotNull(ids);
		assertEquals(idTag, (int)ids.get(0));
	}

	@Test
	public void testGetTagID() {
		assertEquals(idTag, test.getTagID(query));
		
		assertEquals(invalidIdTag, test.getTagID(NULL_STRING));
		assertEquals(invalidIdTag, test.getTagID(EMPTY_STRING));
		assertEquals(invalidIdTag, test.getTagID(BLANK_STRING));
	}

	@Test
	public void testCreateTag() {
		assertEquals(idTag, test.createTag(query));
		
		assertEquals(invalidIdTag, test.createTag(NULL_STRING));
		assertEquals(invalidIdTag, test.createTag(EMPTY_STRING));
		assertEquals(invalidIdTag, test.createTag(BLANK_STRING));
	}

	@Test
	public void testRemoveLessonTags() {
		List<Integer> ids = test.getLessonTagIDs(idLesson);		
		assertNotNull(ids);
		assertEquals(idTag, (int)ids.get(0));
		
		test.removeLessonTags(invalidIdLesson, (ArrayList<Integer>)ids);
		
		ids = test.getLessonTagIDs(idLesson);		
		assertNotNull(ids);
		assertEquals(idTag, (int)ids.get(0));
		
		test.removeLessonTags(idLesson, null);
		
		ids = test.getLessonTagIDs(idLesson);		
		assertNotNull(ids);
		assertEquals(idTag, (int)ids.get(0));
		
		test.removeLessonTags(idLesson, (ArrayList<Integer>)ids);
		
		ids = test.getLessonTagIDs(idLesson);		
		assertNull(ids);		
	}

	@Test
	public void testSearchTag() {
		List<Integer> ids = test.searchTag(query , FIRST_RESULT);		
		assertNotNull(ids);
		
		int i=1;
		for(int b:ids){
			assertEquals(i++, b);
		}		
		
		ids = test.searchTag(query, SECOND_RESULT);		
		assertNotNull(ids);
		
		for(int b:ids){
			assertEquals(i++, b);
		}
		
		ids = test.searchTag(NULL_STRING, FIRST_RESULT);
		assertNull(ids);
		ids = test.searchTag(EMPTY_STRING, FIRST_RESULT);
		assertNull(ids);
		ids = test.searchTag(BLANK_STRING, FIRST_RESULT);
		assertNull(ids);
		
		ids = test.searchTag(query, INVALID_RESULT);
		assertNull(ids);
	}

	@Test
	public void testGetTags() {
		List<Integer> ids = test.getTags(FIRST_RESULT);		
		assertNotNull(ids);
		
		int i=1;
		for(int b:ids){
			assertEquals(i++, b);
		}		
		
		ids = test.getTags(SECOND_RESULT);		
		assertNotNull(ids);
		
		for(int b:ids){
			assertEquals(i++, b);
		}
		
		ids = test.getTags(INVALID_RESULT);
		assertNull(ids);
	}

	@Test
	public void testGetTagSearchCloudTags() {
		List<Integer> ids = test.getTagSearchCloudTags();
		
		assertNotNull(ids);
		
		int i=1;
		for(int b:ids){
			assertEquals(i++, b);
		}
	}

	@Test
	public void testGetLessonViewCloudTags() {
		List<Integer> ids = test.getLessonViewCloudTags();
		
		assertNotNull(ids);
		
		int i=1;
		for(int b:ids){
			assertEquals(i++, b);
		}
	}
}
