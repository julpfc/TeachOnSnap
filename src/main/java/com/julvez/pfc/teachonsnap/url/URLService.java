package com.julvez.pfc.teachonsnap.url;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.test.model.LessonTest;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.user.model.User;


public interface URLService {

	public String getProtocol();
	
	public String getHost();
	
	public String getAbsoluteURL(String relativeURL);

	public String getHomeURL();

	public String getLessonURL();
	public String getLessonPreviewURL();
	public String getLessonEditURL();
	public String getLessonCommentURL();
	public String getLessonNewTestURL();
	public String getLessonURL(Lesson lesson);
	public String getLessonPreviewURL(Lesson lesson);
	public String getLessonEditURL(Lesson lesson);
	public String getLessonCommentURL(Lesson lesson);
	public String getLessonNewTestURL(Lesson lesson);
	
	public String getAuthorURL();
	public String getAuthorURL(User author);
	public String getAuthorDraftsURL();
	public String getAuthorDraftsURL(User author);
	
	public String getLessonTestURL();
	public String getLessonTestEditURL();
	public String getLessonTestNewQuestionURL();
	public String getLessonTestURL(LessonTest test);
	public String getLessonTestEditURL(LessonTest test);
	public String getLessonTestNewQuestionURL(LessonTest test);

	public String getTagURL();
	public String getTagURL(Tag tag);

	public String getMediaFileURL();
	public String getMediaFileURL(MediaFile media);
}
