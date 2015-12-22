package com.julvez.pfc.teachonsnap.url.impl;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.test.model.LessonTest;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.url.model.URLPropertyName;
import com.julvez.pfc.teachonsnap.user.model.User;

public class URLServiceImpl implements URLService {

	private static final String PROTOCOL = "https://";
	
	protected PropertyManager properties = PropertyManagerFactory.getManager();
	protected RequestManager requestManager = RequestManagerFactory.getManager();

	@Override
	public String getHost() {
		return PROTOCOL + properties.getProperty(URLPropertyName.TEACHONSNAP_HOST);		
	}

	@Override
	public String getAbsoluteURL(String relativeURL) {
		return PROTOCOL +  properties.getProperty(URLPropertyName.TEACHONSNAP_HOST) + relativeURL;
	}

	@Override
	public String getHomeURL() {		
		String context = "/";

		return PROTOCOL +  properties.getProperty(URLPropertyName.TEACHONSNAP_HOST) + context;
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
