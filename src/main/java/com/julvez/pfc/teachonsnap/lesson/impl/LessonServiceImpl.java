package com.julvez.pfc.teachonsnap.lesson.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.model.LessonMessageKey;
import com.julvez.pfc.teachonsnap.lesson.repository.LessonRepository;
import com.julvez.pfc.teachonsnap.lesson.repository.LessonRepositoryFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.notify.NotifyServiceFactory;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.text.TextServiceFactory;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;

public class LessonServiceImpl implements LessonService{

	private LessonRepository lessonRepository = LessonRepositoryFactory.getRepository();	
	
	private UserService userService = UserServiceFactory.getService();
	private LangService langService = LangServiceFactory.getService();
	private NotifyService notifyService = NotifyServiceFactory.getService();
	private TextService textService = TextServiceFactory.getService();
	private URLService urlService = URLServiceFactory.getService();
	
	private StringManager stringManager = StringManagerFactory.getManager();


	
	

	@Override
	public Lesson getLessonFromURI(String lessonURI) {
		Lesson lesson = null;

		if(!stringManager.isEmpty(lessonURI)){
			int id = lessonRepository.getLessonIDFromURI(lessonURI);
			
			lesson = getLesson(id);
		}
		
		return lesson;
	}

	@Override
	public List<Lesson> getLinkedLessons(int idLesson) {
		List<Lesson> lessons = new ArrayList<Lesson>();
		
		List<Integer> ids = lessonRepository.getLinkedLessonIDs(idLesson);
		
		for(int id:ids){
			lessons.add(getLesson(id));
		}
		
		return lessons;
	}


	
	@Override
	public Lesson getLesson(int idLesson) {
		Lesson lesson = null;
		
		if(idLesson>0){
			lesson = lessonRepository.getLesson(idLesson);
			
			if(lesson!=null){
				lesson.setAuthor(userService.getUser(lesson.getIdUser()));
				lesson.setLanguage(langService.getLanguage(lesson.getIdLanguage()));
			}
		}
		return lesson;
	}

	@Override
	public List<Lesson> getLessonsFromAuthor(String author,int firstResult) {
		List<Lesson> lessons = new ArrayList<Lesson>();
		
		List<Integer> ids = lessonRepository.getLessonIDsFromAuthor(author,firstResult);
		
		for(int id:ids){
			lessons.add(getLesson(id));
		}
		
		return lessons;
	}

	@Override
	public List<Lesson> getLastLessons(int firstResult) {
		List<Lesson> lessons = new ArrayList<Lesson>();
		
		List<Integer> ids = lessonRepository.getLastLessonIDs(firstResult);
		
		for(int id:ids){
			lessons.add(getLesson(id));
		}
		
		return lessons;
	}

	@Override
	public Lesson createLesson(Lesson newLesson) {
		Lesson ret = null;
		if(newLesson!=null){
			newLesson.setURIname(stringManager.generateURIname(newLesson.getTitle()));
			int idLesson = lessonRepository.createLesson(newLesson);
			if(idLesson > 0){
				newLesson.setId(idLesson);
				if(!stringManager.isEmpty(newLesson.getText()))
					saveLessonText(newLesson, newLesson.getText());
				ret = getLesson(idLesson);
			}
		}
		return ret;
	}

	@Override
	public Lesson saveLessonText(Lesson lesson, String newText) {
		Lesson ret = null;
		if(lesson!=null && lesson.getId()>0 && newText!=null){
			lessonRepository.saveLessonText(lesson.getId(),newText);
			lesson.setText(newText);
			ret = lesson;
		}
		return ret;
	}

	@Override
	public boolean notifyNewLesson(Lesson lesson) {		
		boolean success = false;
		
		if(lesson != null){
			String url = urlService.getAbsoluteURL(lesson.getURL());
			String subject = textService.getLocalizedText(lesson.getAuthor().getLanguage(),LessonMessageKey.NEW_LESSON_SUBJECT, lesson.getTitle());
			String message = textService.getLocalizedText(lesson.getAuthor().getLanguage(),LessonMessageKey.NEW_LESSON_MESSAGE, url);
			
			success = notifyService.info(lesson.getAuthor(), subject, message, lesson.getURL());
		}
		
		return success;		
	}

}
