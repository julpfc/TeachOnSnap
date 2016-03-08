package com.julvez.pfc.teachonsnap.ut.tag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.model.CloudTag;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.tag.model.TagFollowed;
import com.julvez.pfc.teachonsnap.ut.service.ServiceTest;

public abstract class TagServiceTest extends ServiceTest<TagService> {

	protected int idTag = 1;
	protected int invalidIdTag = -1;

	protected int idLesson = 1;
	protected int invalidIdLesson = -1;
	protected String query = "tag";
	
	@Test
	public void testGetLessonsFromTag() {
		Tag tag = new Tag();
		tag.setId(idTag);
		tag.setTag(query);
		
		List<Lesson> lessons = test.getLessonsFromTag(tag , FIRST_RESULT);		
		assertNotNull(lessons);
		
		int i=1;
		for(Lesson l:lessons){
			assertEquals(i++, l.getId());
		}		
		
		lessons = test.getLessonsFromTag(tag, SECOND_RESULT);		
		assertNotNull(lessons);
		
		for(Lesson l:lessons){
			assertEquals(i++, l.getId());
		}
		
		lessons = test.getLessonsFromTag(null, FIRST_RESULT);
		assertNotNull(lessons);
		assertEquals(0, lessons.size());
		
		lessons = test.getLessonsFromTag(tag, INVALID_RESULT);
		assertNotNull(lessons);
		assertEquals(0, lessons.size());
	}

	@Test
	public void testGetLessonTags() {
		List<Tag> tags = test.getLessonTags(invalidIdLesson);		
		assertNotNull(tags);
		assertEquals(0, tags.size());


		tags = test.getLessonTags(idLesson);		
		assertNotNull(tags);
		
		int i=1;
		for(Tag t:tags){
			assertEquals(i++, t.getId());
		}
	}

	@Test
	public void testGetTagInt() {
		Tag tag = test.getTag(idTag);
		assertNotNull(tag);
				
		assertNull(test.getTag(invalidIdTag ));
	}

	@Test
	public void testGetTagString() {
		assertNotNull(test.getTag(query));
		
		assertNull(test.getTag(NULL_STRING));
		assertNull(test.getTag(EMPTY_STRING));
		assertNull(test.getTag(BLANK_STRING));
	}

	@Test
	public void testGetTagUseCloudTags() {
		List<CloudTag> clouds = test.getTagUseCloudTags();
		
		assertNotNull(clouds);
		
		int i=1;
		for(CloudTag tag:clouds){
			assertEquals(i++, tag.getWeight());
		}
	}

	@Test
	public void testGetTagSearchCloudTags() {
		List<CloudTag> clouds = test.getTagSearchCloudTags();
		
		assertNotNull(clouds);
		
		int i=1;
		for(CloudTag tag:clouds){
			assertEquals(i++, tag.getWeight());	
		}
	}

	@Test
	public void testGetAuthorCloudTags() {
		List<CloudTag> clouds = test.getAuthorCloudTags();
		
		assertNotNull(clouds);
		
		int i=1;
		for(CloudTag tag:clouds){
			assertEquals(i++, tag.getWeight());
		}
	}

	@Test
	public void testGetLessonViewCloudTags() {
		List<CloudTag> clouds = test.getLessonViewCloudTags();
		
		assertNotNull(clouds);
		
		int i=1;
		for(CloudTag tag:clouds){
			assertEquals(i++, tag.getWeight());
		}
	}

	@Test
	public void testAddLessonTags() {
		List<String> newTags = new ArrayList<String>();
		newTags.add(query);
		
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		
		List<Tag> tags = test.getLessonTags(idLesson);		
		assertNotNull(tags);
		assertEquals(0, tags.size());
		
		test.addLessonTags(null, newTags);
		
		tags = test.getLessonTags(idLesson);		
		assertNotNull(tags);
		assertEquals(0, tags.size());
		
		test.addLessonTags(lesson, null);
		
		tags = test.getLessonTags(idLesson);		
		assertNotNull(tags);
		assertEquals(0, tags.size());
		
		test.addLessonTags(lesson, newTags);
		
		tags = test.getLessonTags(idLesson);		
		assertNotNull(tags);
		assertEquals(idTag, tags.get(0).getId());
	}

	@Test
	public void testSaveLessonTags() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		
		List<String> newTags = new ArrayList<String>();
		newTags.add(query);
		
		List<Tag> tags = test.getLessonTags(idLesson);			
		assertNotNull(tags);
		assertEquals(0, tags.size());			
		
		test.saveLessonTags(null, null, null);
		
		tags = test.getLessonTags(idLesson);		
		assertNotNull(tags);
		assertEquals(0, tags.size());
		
		test.saveLessonTags(lesson, tags, newTags);
		
		tags = test.getLessonTags(idLesson);		
		assertNotNull(tags);
		assertEquals(idTag, tags.get(0).getId());	
	}

	@Test
	public void testRemoveLessonTags() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.add(idTag);
		
		List<Tag> tags = test.getLessonTags(idLesson);			
		assertNotNull(tags);
		assertEquals(idTag, tags.get(0).getId());
		
		test.removeLessonTags(null, ids);
		
		tags = test.getLessonTags(idLesson);		
		assertNotNull(tags);
		assertEquals(idTag, tags.get(0).getId());	
		
		test.removeLessonTags(lesson, null);
		
		tags = test.getLessonTags(idLesson);		
		assertNotNull(tags);
		assertEquals(idTag, tags.get(0).getId());	
		
		test.removeLessonTags(lesson, ids);
		
		tags = test.getLessonTags(idLesson);		
		assertNotNull(tags);
		assertEquals(0, tags.size());		
	}

	@Test
	public void testGetTags() {
		List<Tag> tags = test.getTags(FIRST_RESULT);		
		assertNotNull(tags);
		
		int i=1;
		for(Tag t:tags){
			assertEquals(i++, t.getId());
		}		
		
		tags = test.getTags(SECOND_RESULT);		
		assertNotNull(tags);
		
		for(Tag t:tags){
			assertEquals(i++, t.getId());
		}	
		
		tags = test.getTags(INVALID_RESULT);
		assertNotNull(tags);
		assertEquals(0, tags.size());
	}

	@Test
	public void testGetTagsFollowed() {
		Tag tag1 = new Tag();
		tag1.setId(idTag);
		
		Tag user2 = new Tag();
		user2.setId(2*idTag);
		
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(tag1);
		tags.add(user2);
		
		List<Tag> followings = new ArrayList<Tag>();
		followings.add(tag1);
		
		List<TagFollowed> followed = test.getTagsFollowed(null, null);
		assertNotNull(followed);
		assertEquals(0, followed.size());

		followed = test.getTagsFollowed(null, followings);
		assertNotNull(followed);
		assertEquals(0, followed.size());
		
		followed = test.getTagsFollowed(tags, null);
		assertNotNull(followed);
		assertEquals(2, followed.size());
		
		assertEquals(tag1.getId(), followed.get(0).getId());
		assertEquals(user2.getId(), followed.get(1).getId());
		assertFalse(followed.get(0).isFollowed());
		assertFalse(followed.get(1).isFollowed());
		
		followed = test.getTagsFollowed(tags, followings);
		assertNotNull(followed);
		assertEquals(2, followed.size());
		
		assertEquals(tag1.getId(), followed.get(0).getId());
		assertEquals(user2.getId(), followed.get(1).getId());
		assertTrue(followed.get(0).isFollowed());
		assertFalse(followed.get(1).isFollowed());	
	}

	@Test
	public void testSearchTag() {
		List<Tag> tags = test.searchTag(query, FIRST_RESULT);		
		assertNotNull(tags);
		
		int i=1;
		for(Tag t:tags){
			assertEquals(i++, t.getId());
		}		
		
		tags = test.searchTag(query, SECOND_RESULT);		
		assertNotNull(tags);
		
		for(Tag t:tags){
			assertEquals(i++, t.getId());
		}	
		
		tags = test.searchTag(NULL_STRING, FIRST_RESULT);
		assertNotNull(tags);
		assertEquals(0, tags.size());
		tags = test.searchTag(EMPTY_STRING, FIRST_RESULT);
		assertNotNull(tags);
		assertEquals(0, tags.size());
		tags = test.searchTag(BLANK_STRING, FIRST_RESULT);
		assertNotNull(tags);
		assertEquals(0, tags.size());
		
		tags = test.searchTag(query, INVALID_RESULT);
		assertNotNull(tags);
		assertEquals(0, tags.size());
	}

	@Test
	public void testNotifyLessonTagged() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		when(lesson.isDraft()).thenReturn(false);
		
		assertTrue(test.notifyLessonTagged(lesson, idTag));
		assertFalse(test.notifyLessonTagged(null, invalidIdTag));
	}

}
