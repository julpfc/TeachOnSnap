package com.julvez.pfc.teachonsnap.ut.tag.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.impl.TagServiceImpl;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.tag.repository.TagRepository;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.ut.tag.TagServiceTest;

public class TagServiceImplTest extends TagServiceTest {

	@Mock
	private TagRepository tagRepository;

	@Mock
	private LessonService lessonService;
	
	@Mock
	private UserService userService;
	
	@Mock
	private TextService textService;
	
	@Mock
	private NotifyService notifyService;
	
	@Mock
	private StatsService statsService;
	
	@Mock
	private URLService urlService;
	
	@Mock
	private StringManager stringManager;
	
	
	@Override
	protected TagService getService() {
		return new TagServiceImpl(tagRepository, lessonService, userService, 
				textService, notifyService, statsService, urlService, stringManager);
	}
	
	@Test
	public void testGetTagInt() {
		Tag tag = mock(Tag.class);
		when(tag.getId()).thenReturn(idTag);		
		when(tagRepository.getTag(idTag)).thenReturn(tag);
		
		super.testGetTagInt();
		
		verify(tagRepository).getTag(anyInt());
	}

	@Test
	public void testGetLessonsFromTag() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids2.add(3);
		ids2.add(4);
		
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(1,2,3,4);
		
		when(lessonService.getLesson(anyInt())).thenReturn(lesson);
		
		when(tagRepository.getLessonIDsFromTag(query, FIRST_RESULT)).thenReturn(ids);
		when(tagRepository.getLessonIDsFromTag(query, SECOND_RESULT)).thenReturn(ids2);
		
		super.testGetLessonsFromTag();
		
		verify(tagRepository, times(2)).getLessonIDsFromTag(anyString(), anyInt());
	}

	@Test
	public void testGetLessonTags() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);

		Tag tag = mock(Tag.class);
		when(tag.getId()).thenReturn(1,2);		
		when(tagRepository.getTag(anyInt())).thenReturn(tag);
		
		when(tagRepository.getLessonTagIDs(idLesson)).thenReturn(ids);
		
		super.testGetLessonTags();
		
		verify(tagRepository, times(2)).getLessonTagIDs(anyInt());
	}

	@Test
	public void testGetTagUseCloudTags() {
		List<Object[]> ids = new ArrayList<Object[]>();
		ids.add(new Object[]{1,BigInteger.valueOf(1)});
		
		when(tagRepository.getTagUseCloudTags()).thenReturn(ids);
		
		Tag tag = mock(Tag.class);
		when(tag.getId()).thenReturn(idTag);		
		when(tagRepository.getTag(anyInt())).thenReturn(tag);
		
		super.testGetTagUseCloudTags();
		
		verify(tagRepository).getTagUseCloudTags();
	}

	@Test
	public void testGetAuthorCloudTags() {
		List<Object[]> ids = new ArrayList<Object[]>();
		ids.add(new Object[]{(short)1,BigInteger.valueOf(1)});
		
		when(tagRepository.getAuthorCloudTags()).thenReturn(ids);
		
		when(userService.getUser(anyInt())).thenReturn(new User());
		
		super.testGetAuthorCloudTags();
		
		verify(tagRepository).getAuthorCloudTags();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testAddLessonTags() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);

		Tag tag = mock(Tag.class);
		when(tag.getId()).thenReturn(1,2);		
		when(tagRepository.getTag(anyInt())).thenReturn(tag);
		
		when(tagRepository.getLessonTagIDs(idLesson)).thenReturn(null, null, null, ids);
		
		when(tagRepository.getTagID(query)).thenReturn(idTag);
		super.testAddLessonTags();
		verify(tagRepository).addLessonTags(anyInt(), (ArrayList<Integer>)anyObject());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSaveLessonTags() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);

		Tag tag = mock(Tag.class);
		when(tag.getId()).thenReturn(1,2);		
		when(tagRepository.getTag(anyInt())).thenReturn(tag);
		
		when(tagRepository.getLessonTagIDs(idLesson)).thenReturn(null, null, ids);
		
		when(tagRepository.getTagID(query)).thenReturn(idTag);
		
		super.testSaveLessonTags();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testRemoveLessonTags() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);		

		Tag tag = mock(Tag.class);
		when(tag.getId()).thenReturn(1);		
		when(tagRepository.getTag(anyInt())).thenReturn(tag);
		
		when(tagRepository.getLessonTagIDs(idLesson)).thenReturn(ids, ids, ids, null);
		
		when(tagRepository.getTagID(query)).thenReturn(idTag);
		
		super.testRemoveLessonTags();
	}

	@Test
	public void testGetTags() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids2.add(3);
		ids2.add(4);
		
		when(tagRepository.getTags(FIRST_RESULT)).thenReturn(ids);
		when(tagRepository.getTags(SECOND_RESULT)).thenReturn(ids2);
		
		Tag tag = mock(Tag.class);
		when(tag.getId()).thenReturn(1,2,3,4);		
		when(tagRepository.getTag(anyInt())).thenReturn(tag);
				
		super.testGetTags();
		
		verify(tagRepository, times(2)).getTags(anyInt());
	}

	
	@Test
	public void testSearchTag() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids2.add(3);
		ids2.add(4);

		when(tagRepository.searchTag(query, FIRST_RESULT)).thenReturn(ids);
		when(tagRepository.searchTag(query, SECOND_RESULT)).thenReturn(ids2);
		
		Tag tag = mock(Tag.class);
		when(tag.getId()).thenReturn(1,2,3,4);		
		when(tagRepository.getTag(anyInt())).thenReturn(tag);
		
		super.testSearchTag();
		
		verify(tagRepository, times(5)).searchTag(anyString(), anyInt());
	}

	@Test
	public void testNotifyLessonTagged() {
		Tag tag = mock(Tag.class);
		when(tag.getId()).thenReturn(idTag);		
		when(tagRepository.getTag(anyInt())).thenReturn(tag);
		
		List<User> followers = new ArrayList<User>();
		followers.add(new User());
				
		when(userService.getTagFollowers(any(Tag.class))).thenReturn(followers);
		
		when(notifyService.info(any(User.class), anyString(), anyString())).thenReturn(true);
		super.testNotifyLessonTagged();
		
		verify(notifyService).info(any(User.class), anyString(), anyString());
	}

	@Test
	public void testGetTagString() {
		when(stringManager.isEmpty(anyString())).thenReturn(true);
		when(stringManager.isEmpty(query)).thenReturn(false);
		
		Tag tag = mock(Tag.class);
		when(tag.getId()).thenReturn(idTag);		
		when(tagRepository.getTag(anyInt())).thenReturn(tag);
		
		when(tagRepository.getTagID(query)).thenReturn(idTag);
		
		super.testGetTagString();
		
		verify(tagRepository).getTagID(anyString());
	}

	@Test
	public void testGetTagSearchCloudTags() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(tagRepository.getTagSearchCloudTags()).thenReturn(ids);
		
		when(statsService.getTagViewsCount(any(Tag.class))).thenReturn(1);
		
		Tag tag = mock(Tag.class);
		when(tag.getId()).thenReturn(idTag);		
		when(tagRepository.getTag(anyInt())).thenReturn(tag);
		
		super.testGetTagSearchCloudTags();
		
		verify(tagRepository).getTagSearchCloudTags();
	}

	@Test
	public void testGetLessonViewCloudTags() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(tagRepository.getLessonViewCloudTags()).thenReturn(ids);
		
		when(statsService.getLessonViewsCount(any(Lesson.class))).thenReturn(1);
		
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(1);
		
		when(lessonService.getLesson(anyInt())).thenReturn(lesson);
		
		Tag tag = mock(Tag.class);
		when(tag.getId()).thenReturn(idTag);		
		when(tagRepository.getTag(anyInt())).thenReturn(tag);
		
		super.testGetLessonViewCloudTags();
		
		verify(tagRepository).getLessonViewCloudTags();
	}
}