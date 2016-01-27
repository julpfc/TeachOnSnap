package com.julvez.pfc.teachonsnap.lesson.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.model.LessonMessageKey;
import com.julvez.pfc.teachonsnap.lesson.repository.LessonRepository;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Implementation of the LessonService interface, uses an internal {@link LessonRepository} 
 * to access/modify the lessons related data.
 */
public class LessonServiceImpl implements LessonService{

	/** Repository than provides data access/modification */
	private LessonRepository lessonRepository;	
	
	/** Provides the functionality to work with application's users */
	private UserService userService;
	
	/** Provides the functionality to work with localized texts. */
	private TextService textService;
	
	/** Provides the functionality to work with notifications */
	private NotifyService notifyService;
	
	/** Provides the functionality to work with application's URLs */
	private URLService urlService;
	
	/** Provides the functionality to work with different languages to the application. */
	private LangService langService;

	/** String manager providing string manipulation utilities */
	private StringManager stringManager;
	
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param lessonRepository Repository than provides data access/modification
	 * @param langService Provides the functionality to work different languages to the application.
	 * @param userService Provides the functionality to work with application's users
	 * @param textService Provides the functionality to work with localized texts
	 * @param notifyService Provides the functionality to work with notifications
	 * @param urlService Provides the functionality to work with application's URLs
	 * @param stringManager String manager providing string manipulation utilities
	 */
	public LessonServiceImpl(LessonRepository lessonRepository,
			LangService langService, UserService userService,
			TextService textService, NotifyService notifyService,
			URLService urlService, StringManager stringManager) {
		
		if(lessonRepository == null || langService == null || urlService == null
				|| notifyService == null || textService == null || userService == null 
				|| stringManager == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		
		this.lessonRepository = lessonRepository;
		this.langService = langService;
		this.userService = userService;
		this.textService = textService;
		this.notifyService = notifyService;		
		this.urlService = urlService;
		this.stringManager = stringManager;
	}


	
	@Override
	public Lesson getLessonFromURI(String lessonURI) {
		Lesson lesson = null;

		if(!stringManager.isEmpty(lessonURI)){
			//get lesson id from URI
			int id = lessonRepository.getLessonIDFromURI(lessonURI);
			
			//get lesson from id
			lesson = getLesson(id);
		}
		
		return lesson;
	}

	
	@Override
	public Lesson getLesson(int idLesson) {
		Lesson lesson = null;
		
		if(idLesson>0){
			//get lesson from id
			lesson = lessonRepository.getLesson(idLesson);
			
			if(lesson!=null){
				//set author and language objects
				lesson.setAuthor(userService.getUser(lesson.getIdUser()));
				lesson.setLanguage(langService.getLanguage(lesson.getIdLanguage()));
				
				//set lesson's URLs
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
		
		//get lessons ids from author URI
		List<Integer> ids = lessonRepository.getLessonIDsFromAuthor(author,firstResult);
		
		if(ids != null){
			lessons = new ArrayList<Lesson>();
			//get lessons from ids
			for(int id:ids){
				lessons.add(getLesson(id));
			}
		}
		
		return lessons;
	}

	@Override
	public List<Lesson> getLastLessons(int firstResult) {
		List<Lesson> lessons = Collections.emptyList();
		
		//get last lessons ids from repo. Pagination
		List<Integer> ids = lessonRepository.getLastLessonIDs(firstResult);
		
		if(ids != null){
			lessons = new ArrayList<Lesson>();
			//get lessons from ids
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
			//generate URI name from title
			newLesson.setURIname(stringManager.generateURIname(newLesson.getTitle()));
			
			//create lesson
			int idLesson = lessonRepository.createLesson(newLesson);
			if(idLesson > 0){
				newLesson.setId(idLesson);
				//save optional text if present
				if(!stringManager.isEmpty(newLesson.getText()))
					saveLessonText(newLesson, newLesson.getText());
				//get the newly created lesson
				ret = getLesson(idLesson);
			}
		}
		return ret;
	}

	@Override
	public Lesson saveLessonText(Lesson lesson, String newText) {
		Lesson ret = null;
		if(lesson!=null && lesson.getId()>0){
			//check if there is a new text or changes to save
			if((newText != null && !newText.equals(lesson.getText())) 
					|| (lesson.getText() != null && !lesson.getText().equals(newText))){
				
				if(newText == null){
					//remove text
					lessonRepository.removeLessonText(lesson.getId());
				}
				else{
					//updates text
					lessonRepository.saveLessonText(lesson.getId(),newText);
				}
				//update modified lesson
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
			//get lesson's URL
			String url = lesson.getURL();
			
			//notify author the lesson was successfully created
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
			//get lesson's url
			String url = lesson.getURL();
			String subject = textService.getLocalizedText(lesson.getAuthor().getLanguage(),LessonMessageKey.MODIFIED_LESSON_SUBJECT, lesson.getTitle());
			String message = textService.getLocalizedText(lesson.getAuthor().getLanguage(),LessonMessageKey.MODIFIED_LESSON_MESSAGE, url);
			
			//notify author
			success = notifyService.info(lesson.getAuthor(), subject, message);

			if(!lesson.isDraft()){
				//notify lesson's followers
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
			//save language
			lessonRepository.saveLessonLanguage(lesson.getId(), language.getId());
			//update modified lesson
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
			//generate new URI name from new title
			String URIName = stringManager.generateURIname(title);
			
			//save new title
			if(lessonRepository.saveLessonTitle(lesson, title, URIName)){
				//update modified lesson
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
			//publish the lesson
			lessonRepository.publish(lesson.getId());
			lesson = getLesson(lesson.getId());
			//notifications
			notifyLessonPublished(lesson);
			notifyLessonModified(lesson);
		}		
	}
	
	@Override
	public void republish(Lesson lesson) {
		if(lesson != null && lesson.getId() > 0 && !lesson.isDraft()){
			//publish the lesson
			lessonRepository.publish(lesson.getId());
		}		
	}


	@Override
	public void unpublish(Lesson lesson) {
		if(lesson != null && lesson.getId() > 0 && !lesson.isDraft()){
			//unpublish the lesson
			lessonRepository.unpublish(lesson.getId());
		}
	}


	@Override
	public List<Lesson> getLessonDraftsFromUser(User user, int firstResult) {
		List<Lesson> lessons = Collections.emptyList();
		
		if(user!=null){			  
			//get drafts ids from repo
			List<Integer> ids = lessonRepository.getDraftLessonIDsFromUser(user.getId(), firstResult);
		
			if(ids != null){
				lessons = new ArrayList<Lesson>();
				for(int id:ids){
					//get lessons from ids
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
					//get lessons from ids
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
			
			//get lesson's author
			User author = lesson.getAuthor();
			
			//get author's followers
			List<User> followers = userService.getAuthorFollowers(author);
			
			if(followers != null){			
				
				String url = lesson.getURL();
				//notify author's followers
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
