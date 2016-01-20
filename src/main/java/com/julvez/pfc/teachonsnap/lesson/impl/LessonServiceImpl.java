package com.julvez.pfc.teachonsnap.lesson.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.lang.model.Language;
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
import com.julvez.pfc.teachonsnap.user.model.User;

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
	public Lesson getLesson(int idLesson) {
		Lesson lesson = null;
		
		if(idLesson>0){
			lesson = lessonRepository.getLesson(idLesson);
			
			if(lesson!=null){
				lesson.setAuthor(userService.getUser(lesson.getIdUser()));
				lesson.setLanguage(langService.getLanguage(lesson.getIdLanguage()));
				
				lesson.setURLs(urlService.getLessonURL(), urlService.getLessonPreviewURL(),
						urlService.getLessonEditURL(), urlService.getLessonCommentURL(), 
						urlService.getLessonNewTestURL());
			}
		}
		return lesson;
	}

	@Override
	public List<Lesson> getLessonsFromAuthor(String author,int firstResult) {
		List<Lesson> lessons = Collections.emptyList();
		
		List<Integer> ids = lessonRepository.getLessonIDsFromAuthor(author,firstResult);
		
		if(ids != null){
			lessons = new ArrayList<Lesson>();
			for(int id:ids){
				lessons.add(getLesson(id));
			}
		}
		
		return lessons;
	}

	@Override
	public List<Lesson> getLastLessons(int firstResult) {
		List<Lesson> lessons = Collections.emptyList();
		
		List<Integer> ids = lessonRepository.getLastLessonIDs(firstResult);
		
		if(ids != null){
			lessons = new ArrayList<Lesson>();
			for(int id:ids){
				lessons.add(getLesson(id));
			}
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
		if(lesson!=null && lesson.getId()>0){
			if((newText != null && !newText.equals(lesson.getText())) 
					|| (lesson.getText() != null && !lesson.getText().equals(newText))){
				
				if(newText == null){
					lessonRepository.removeLessonText(lesson.getId());
				}
				else{
					lessonRepository.saveLessonText(lesson.getId(),newText);
				}
				lesson.setText(newText);
				ret = lesson;
			}
		}
		return ret;
	}

	@Override
	public boolean notifyLessonCreated(Lesson lesson) {		
		boolean success = false;
		
		if(lesson != null){
			String url = lesson.getURL();
			String subject = textService.getLocalizedText(lesson.getAuthor().getLanguage(),LessonMessageKey.NEW_LESSON_SUBJECT, lesson.getTitle());
			String message = textService.getLocalizedText(lesson.getAuthor().getLanguage(),LessonMessageKey.NEW_LESSON_MESSAGE, url);
			
			success = notifyService.info(lesson.getAuthor(), subject, message);
		}
		
		return success;		
	}


	@Override
	public boolean notifyLessonModified(Lesson lesson) {
		boolean success = false;
		
		if(lesson != null){
			String url = lesson.getURL();
			String subject = textService.getLocalizedText(lesson.getAuthor().getLanguage(),LessonMessageKey.MODIFIED_LESSON_SUBJECT, lesson.getTitle());
			String message = textService.getLocalizedText(lesson.getAuthor().getLanguage(),LessonMessageKey.MODIFIED_LESSON_MESSAGE, url);
			
			success = notifyService.info(lesson.getAuthor(), subject, message);

			if(!lesson.isDraft()){
				List<User> followers = userService.getLessonFollowers(lesson);
				if(followers != null){
					for(User follower:followers){
						subject = textService.getLocalizedText(follower.getLanguage(),LessonMessageKey.MODIFIED_LESSON_SUBJECT, lesson.getTitle());
						message = textService.getLocalizedText(follower.getLanguage(),LessonMessageKey.MODIFIED_LESSON_MESSAGE, url);
						notifyService.info(follower, subject, message);
					}
				}
			}
			
		}
		
		return success;		}


	@Override
	public Lesson saveLessonLanguage(Lesson lesson, Language language) {
		Lesson ret = null;
		if(lesson!=null && lesson.getId()>0 && language != null && language.getId() != lesson.getIdLanguage()){
			lessonRepository.saveLessonLanguage(lesson.getId(), language.getId());
			lesson.setLanguage(language);
			lesson.setIdLanguage(language.getId());
			ret = lesson;
		}
		return ret;
	}


	@Override
	public Lesson saveLessonTitle(Lesson lesson, String title) {
		Lesson ret = null;
		if(lesson!=null && lesson.getId()>0 && title!=null && !title.equals(lesson.getTitle())){
			String URIName = stringManager.generateURIname(title);
			
			if(lessonRepository.saveLessonTitle(lesson, title, URIName)){
				lesson.setTitle(title);
				lesson.setURIname(URIName);
				ret = lesson;
			}
		}
		return ret;
	}


	@Override
	public void publish(Lesson lesson) {
		if(lesson != null && lesson.getId() > 0 && lesson.isDraft()){
			lessonRepository.publish(lesson.getId());
			lesson = getLesson(lesson.getId());
			notifyLessonPublished(lesson);
			notifyLessonModified(lesson);
		}		
	}
	
	@Override
	public void republish(Lesson lesson) {
		if(lesson != null && lesson.getId() > 0 && !lesson.isDraft()){
			lessonRepository.publish(lesson.getId());
		}		
	}


	@Override
	public void unpublish(Lesson lesson) {
		if(lesson != null && lesson.getId() > 0 && !lesson.isDraft()){
			lessonRepository.unpublish(lesson.getId());
		}
	}


	@Override
	public List<Lesson> getLessonDraftsFromUser(User user, int firstResult) {
		List<Lesson> lessons = Collections.emptyList();
		
		if(user!=null){			  
		
			List<Integer> ids = lessonRepository.getDraftLessonIDsFromUser(user.getId(), firstResult);
		
			if(ids != null){
				lessons = new ArrayList<Lesson>();
				for(int id:ids){
					lessons.add(getLesson(id));
				}
			}
		}
		return lessons;	
	}


	@Override
	public List<Lesson> getLessonsFromIDs(Map<String, String> lessonFollowed) {
		List<Lesson> lessons = null;
		
		if(lessonFollowed != null){
			lessons = new ArrayList<Lesson>();
			
			for(String id:lessonFollowed.values()){
				if(stringManager.isNumeric(id)){
					int idLesson = Integer.parseInt(id);
					Lesson lesson = getLesson(idLesson);
					lessons.add(lesson);
				}
			}
		}
		return lessons;
	}


	@Override
	public boolean notifyLessonPublished(Lesson lesson) {
		boolean success = false;
		
		if(lesson != null){
			
			User author = lesson.getAuthor();
			
			List<User> followers = userService.getAuthorFollowers(author);
			
			if(followers != null){			
				
				String url = lesson.getURL();
				
				for(User follower:followers){
					String subject = textService.getLocalizedText(follower.getLanguage(),LessonMessageKey.PUBLISHED_LESSON_SUBJECT, lesson.getTitle(), lesson.getAuthor().getFullName());
					String message = textService.getLocalizedText(follower.getLanguage(),LessonMessageKey.PUBLISHED_LESSON_MESSAGE, url, lesson.getAuthor().getFullName());
					
					success = notifyService.info(follower, subject, message);
				}
			}				
		}
		
		return success;
	}

}
