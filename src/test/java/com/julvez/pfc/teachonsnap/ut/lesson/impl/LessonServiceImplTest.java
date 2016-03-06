package com.julvez.pfc.teachonsnap.ut.lesson.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.impl.LessonServiceImpl;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.repository.LessonRepository;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.ut.lesson.LessonServiceTest;

public class LessonServiceImplTest extends LessonServiceTest {

	@Mock
	private LessonRepository lessonRepository;	
	
	@Mock
	private UserService userService;
	
	@Mock
	private TextService textService;
	
	@Mock
	private NotifyService notifyService;
	
	@Mock
	private URLService urlService;
	
	@Mock
	private LangService langService;

	@Mock
	private StringManager stringManager;
	
	@Override
	protected LessonService getService() {
		return new LessonServiceImpl(lessonRepository, langService, userService, 
				textService, notifyService, urlService, stringManager);
	}
		
	@Test
	public void testGetLessonFromURI() {
		when(stringManager.isEmpty(anyString())).thenReturn(true);
		when(stringManager.isEmpty(URI)).thenReturn(false);

		when(lessonRepository.getLessonIDFromURI(URI)).thenReturn(idLesson);
		
		Lesson lesson = mock(Lesson.class);		
		when(lessonRepository.getLesson(idLesson)).thenReturn(lesson);
		
		super.testGetLessonFromURI();
		verify(lessonRepository).getLessonIDFromURI(anyString());
	}

	@Test
	public void testGetLesson() {
		Lesson lesson = mock(Lesson.class);		
		when(lessonRepository.getLesson(idLesson)).thenReturn(lesson);
		super.testGetLesson();
		verify(lessonRepository).getLesson(anyInt());
	}

	@Test
	public void testGetLessonsFromAuthor() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids2.add(3);
		ids2.add(4);
		
		when(lessonRepository.getLessonIDsFromAuthor(URI, FIRST_RESULT)).thenReturn(ids);
		when(lessonRepository.getLessonIDsFromAuthor(URI, SECOND_RESULT)).thenReturn(ids2);
		
		Lesson lesson = mock(Lesson.class);		
		when(lesson.getId()).thenReturn(1,2,3,4);
		when(lessonRepository.getLesson(anyInt())).thenReturn(lesson);
		
		super.testGetLessonsFromAuthor();
		
		verify(lessonRepository, times(6)).getLessonIDsFromAuthor(anyString(), anyInt());
	}

	@Test
	public void testGetLastLessons() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids2.add(3);
		ids2.add(4);
		
		when(lessonRepository.getLastLessonIDs(FIRST_RESULT)).thenReturn(ids);
		when(lessonRepository.getLastLessonIDs(SECOND_RESULT)).thenReturn(ids2);
		
		Lesson lesson = mock(Lesson.class);		
		when(lesson.getId()).thenReturn(1,2,3,4);
		when(lessonRepository.getLesson(anyInt())).thenReturn(lesson);
		
		super.testGetLastLessons();
		
		verify(lessonRepository, times(3)).getLastLessonIDs(anyInt());
	}

	@Test
	public void testCreateLesson() {
		when(stringManager.generateURIname(anyString())).thenReturn(URI);
		
		Lesson lesson = mock(Lesson.class);		
		when(lesson.getId()).thenReturn(idLesson);
		when(lessonRepository.getLesson(anyInt())).thenReturn(lesson);
		
		when(lessonRepository.createLesson(any(Lesson.class))).thenReturn(idLesson);
		
		super.testCreateLesson();
		
		verify(lessonRepository).createLesson(any(Lesson.class));
	}

	@Test
	public void testSaveLessonText() {
		Lesson lesson = mock(Lesson.class);		
		when(lesson.getId()).thenReturn(idLesson);
		when(lesson.getText()).thenReturn(EMPTY_STRING, EMPTY_STRING, text);
		when(lessonRepository.getLesson(idLesson)).thenReturn(lesson);
		
		super.testSaveLessonText();
	}

	@Test
	public void testNotifyLessonCreated() {
		Lesson lesson = mock(Lesson.class);		
		when(lesson.getId()).thenReturn(idLesson);
		when(lessonRepository.getLesson(idLesson)).thenReturn(lesson);
		
		User user = mock(User.class);
		when(lesson.getAuthor()).thenReturn(user);
		
		when(notifyService.info(any(User.class), anyString(), anyString())).thenReturn(true);
		super.testNotifyLessonCreated();
		verify(notifyService).info(any(User.class), anyString(), anyString());
	}

	@Test
	public void testNotifyLessonModified() {
		Lesson lesson = mock(Lesson.class);		
		when(lesson.getId()).thenReturn(idLesson);
		when(lessonRepository.getLesson(idLesson)).thenReturn(lesson);
		
		User user = mock(User.class);
		when(lesson.getAuthor()).thenReturn(user);
		
		when(notifyService.info(any(User.class), anyString(), anyString())).thenReturn(true);
		super.testNotifyLessonModified();
		verify(notifyService).info(any(User.class), anyString(), anyString());
	}

	@Test
	public void testSaveLessonLanguage() {
		Lesson lesson = mock(Lesson.class);		
		when(lesson.getId()).thenReturn(idLesson);
		when(lesson.getIdLanguage()).thenReturn((short)1, (short)1, (short)1, (short)2);
		when(lessonRepository.getLesson(idLesson)).thenReturn(lesson);
		
		super.testSaveLessonLanguage();
	}

	@Test
	public void testSaveLessonTitle() {
		when(stringManager.generateURIname(anyString())).thenReturn(URI);
		Lesson lesson = mock(Lesson.class);		
		when(lesson.getId()).thenReturn(idLesson);
		when(lesson.getTitle()).thenReturn(EMPTY_STRING, EMPTY_STRING, title);
		when(lesson.getURIname()).thenReturn(URI);
		when(lessonRepository.getLesson(idLesson)).thenReturn(lesson);
		
		when(lessonRepository.saveLessonTitle(any(Lesson.class), eq(title), eq(URI))).thenReturn(true);
		super.testSaveLessonTitle();
		verify(lessonRepository).saveLessonTitle(any(Lesson.class), anyString(), anyString());
	}

	@Test
	public void testPublish() {
		Lesson lesson = mock(Lesson.class);		
		when(lesson.getId()).thenReturn(idLesson);
		when(lesson.isDraft()).thenReturn(true, true, true, false);		
		when(lessonRepository.getLesson(idLesson)).thenReturn(lesson);
		
		User user = mock(User.class);
		when(lesson.getAuthor()).thenReturn(user);
		
		super.testPublish();
		
		verify(lessonRepository).publish(anyInt());
	}

	@Test
	public void testRepublish() {
		Lesson lesson = mock(Lesson.class);		
		when(lesson.getId()).thenReturn(idLesson);
		when(lesson.isDraft()).thenReturn(false);		
		when(lessonRepository.getLesson(idLesson)).thenReturn(lesson);
		
		super.testRepublish();
		
		verify(lessonRepository).publish(anyInt());
	}

	@Test
	public void testUnpublish() {
		Lesson lesson = mock(Lesson.class);		
		when(lesson.getId()).thenReturn(idLesson);
		when(lesson.isDraft()).thenReturn(false, false, false, true);		
		when(lessonRepository.getLesson(idLesson)).thenReturn(lesson);
		
		super.testUnpublish();
		
		verify(lessonRepository).unpublish(anyInt());
	}

	@Test
	public void testGetLessonDraftsFromUser() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids2.add(3);
		ids2.add(4);
		
		when(lessonRepository.getDraftLessonIDsFromUser(idUser, FIRST_RESULT)).thenReturn(ids);
		when(lessonRepository.getDraftLessonIDsFromUser(idUser, SECOND_RESULT)).thenReturn(ids2);
		
		Lesson lesson = mock(Lesson.class);		
		when(lesson.getId()).thenReturn(1,2,3,4);
		when(lessonRepository.getLesson(anyInt())).thenReturn(lesson);
		
		super.testGetLessonDraftsFromUser();
		
		verify(lessonRepository, times(3)).getDraftLessonIDsFromUser(anyInt(), anyInt());
	}

	@Test
	public void testGetLessonsFromIDs() {
		when(stringManager.isNumeric(anyString())).thenReturn(true);
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		when(lessonRepository.getLesson(idLesson)).thenReturn(lesson);
		super.testGetLessonsFromIDs();
	}

	@Test
	public void testNotifyLessonPublished() {
		Lesson lesson = mock(Lesson.class);		
		when(lesson.getId()).thenReturn(idLesson);
		List<User> followers = new ArrayList<User>();
		followers.add(new User());
		when(userService.getAuthorFollowers(any(User.class))).thenReturn(followers);
		when(lessonRepository.getLesson(idLesson)).thenReturn(lesson);
		
		User user = mock(User.class);
		when(lesson.getAuthor()).thenReturn(user);
		
		when(notifyService.info(any(User.class), anyString(), anyString())).thenReturn(true);
		super.testNotifyLessonPublished();
		verify(notifyService).info(any(User.class), anyString(), anyString());
	}
}
