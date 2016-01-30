package com.julvez.pfc.teachonsnap.url.impl;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.url.model.URLPropertyName;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Implementation of the URLService interface, uses the application properties 
 * file to get default parameters.
 */
public class URLServiceImpl implements URLService {

	/** Property manager providing access to properties files */
	private PropertyManager properties;
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param properties Property manager providing access to properties files
	 */
	public URLServiceImpl(PropertyManager properties) {
		if(properties == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.properties = properties;
	}

	@Override
	public String getProtocol() {
		String defaultProtocol = "https://";
		String protocol = properties.getProperty(URLPropertyName.TEACHONSNAP_PROTOCOL);
		
		//Default protocol is used in case no protocol is defined on properties file.
		if(protocol != null){
			if(!protocol.contains("://")){
				protocol = protocol + "://"; 
			}		
		}
		else{
			protocol = defaultProtocol;
		}
			
		return protocol;
	}
	
	@Override
	public String getHost() {
		return getProtocol() + properties.getProperty(URLPropertyName.TEACHONSNAP_HOST);		
	}

	@Override
	public String getAbsoluteURL(String relativeURL) {
		return getHost() + relativeURL;
	}

	@Override
	public String getHomeURL() {		
		return getHost() + ControllerURI.HOME;
	}

	@Override
	public String getLessonURL() {
		return getHost() + ControllerURI.LESSON.toString();
	}

	@Override
	public String getLessonPreviewURL() {
		return getHost() + ControllerURI.LESSON_PREVIEW.toString();
	}

	@Override
	public String getLessonEditURL() {
		return getHost() + ControllerURI.LESSON_EDIT.toString();
	}

	@Override
	public String getLessonCommentURL() {
		return getHost() + ControllerURI.LESSON_COMMENT.toString();
	}

	@Override
	public String getLessonNewTestURL() {
		return getHost() + ControllerURI.LESSON_TEST_NEW.toString();
	}

	@Override
	public String getLessonURL(Lesson lesson) {
		if(lesson != null){
			return getLessonURL() + lesson.getURIname();
		}
		else return null;
	}

	@Override
	public String getLessonPreviewURL(Lesson lesson) {
		if(lesson != null){
			return getLessonPreviewURL() + lesson.getId();
		}
		else return null;
	}

	@Override
	public String getLessonEditURL(Lesson lesson) {
		if(lesson != null){
			return getLessonEditURL() + lesson.getId();
		}
		else return null;
	}

	@Override
	public String getLessonCommentURL(Lesson lesson) {
		if(lesson != null){
			return getLessonCommentURL() + lesson.getURIname();
		}
		else return null;
	}

	@Override
	public String getLessonNewTestURL(Lesson lesson) {
		if(lesson != null){
			return getLessonNewTestURL() + lesson.getId();
		}
		else return null;
	}

	@Override
	public String getAuthorURL() {
		return getHost() + ControllerURI.AUTHOR.toString();
	}

	@Override
	public String getAuthorURL(User author) {
		if(author != null && author.getURIName()!=null){
			return getAuthorURL() + author.getURIName();
		}
		else return null;		
	}

	@Override
	public String getAuthorDraftsURL() {
		return getHost() + ControllerURI.LESSON_DRAFTS_BY_USER.toString();
	}

	@Override
	public String getAuthorDraftsURL(User author) {
		if(author != null){
			return getAuthorDraftsURL() + author.getId();
		}
		else return null;
	}

	@Override
	public String getLessonTestURL() {
		return getHost() + ControllerURI.LESSON_TEST.toString();
	}

	@Override
	public String getLessonTestEditURL() {
		return getHost() + ControllerURI.LESSON_TEST_EDIT.toString();
	}

	@Override
	public String getLessonTestNewQuestionURL() {
		return getHost() + ControllerURI.LESSON_TEST_QUESTION.toString();
	}

	@Override
	public String getLessonTestURL(LessonTest test) {
		if(test != null){
			return getLessonTestURL() + test.getId();
		}
		else return null;
	}

	@Override
	public String getLessonTestEditURL(LessonTest test) {
		if(test != null){
			return getLessonTestEditURL() + test.getId();
		}
		else return null;
	}

	@Override
	public String getLessonTestNewQuestionURL(LessonTest test) {
		if(test != null){
			return getLessonTestNewQuestionURL() + test.getId();
		}
		else return null;
	}

	@Override
	public String getLessonTestEditQuestionURL(int idLessonTest) {
		if(idLessonTest > 0){
			return getLessonTestNewQuestionURL() + idLessonTest + "/";
		}
		else return null;
	}

	
	@Override
	public String getTagURL() {
		return getHost() + ControllerURI.TAG.toString();
	}

	@Override
	public String getTagURL(Tag tag) {
		if(tag != null){
			return getTagURL() + tag.getTag();
		}
		else return null;
	}

	@Override
	public String getMediaFileURL() {
		return getHost() + ControllerURI.RESOURCES_MEDIA.toString();
	}

	@Override
	public String getMediaFileURL(MediaFile media) {
		if(media != null){
			return getMediaFileURL() + media.getIdLessonMedia() + "/" + media.getId() +"/" + media.getFilename();
		}
		else return null;
	}


}
