package com.julvez.pfc.teachonsnap.url;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Provides the functionality to work with application's URLs.
 */
public interface URLService {

	/**
	 * Returns the default protocol defined for the application's URLs:
	 * http, https, ...
	 * @return protocol 
	 */
	public String getProtocol();
	
	/**
	 * Returns application's host and port if present (includes protocol)
	 * @return application's host 
	 */
	public String getHost();
	
	/**
	 * Constructs an absolute URL from a relative one by adding the protocol, host and port (if present)
	 * @param relativeURL to be completed
	 * @return an absolute URL to the relative path
	 * 
	 */
	public String getAbsoluteURL(String relativeURL);

	/**
	 * Gets the homepage URL (absolute)
	 * @return homepage URL
	 */
	public String getHomeURL();

	/**
	 * Returns lesson page URL (absolute), without specifying a lesson 
	 * @return lesson page URL without lesson identifier
	 */
	public String getLessonURL();
	
	/**
	 * Returns lesson's preview page URL (absolute), without specifying a lesson 
	 * @return lesson's preview page URL without lesson identifier
	 */
	public String getLessonPreviewURL();

	/**
	 * Returns lesson's edit page URL (absolute), without specifying a lesson 
	 * @return lesson's edit page URL without lesson identifier
	 */
	public String getLessonEditURL();
	/**
	 * Returns lesson's comment page URL (absolute), without specifying a lesson 
	 * @return lesson's comment page URL without lesson identifier
	 */
	public String getLessonCommentURL();
	/**
	 * Returns new lesson test creation page URL (absolute), without specifying a lesson 
	 * @return new lesson test creation page URL without lesson identifier
	 */
	public String getLessonNewTestURL();
	
	/**
	 * Returns lesson page URL (absolute) and completes it to point to the specified lesson 
	 * @param lesson we want URL to point to
	 * @return lesson page URL (null if lesson is null or empty)
	 */
	public String getLessonURL(Lesson lesson);

	/**
	 * Returns lesson's preview page URL (absolute) and completes it to point to the specified lesson 
	 * @param lesson we want URL to point to
	 * @return lesson's preview page URL (null if lesson is null or empty)
	 */
	public String getLessonPreviewURL(Lesson lesson);
	
	/**
	 * Returns lesson's edit page URL (absolute) and completes it to point to the specified lesson 
	 * @param lesson we want URL to point to
	 * @return lesson's edit page URL (null if lesson is null or empty)
	 */
	public String getLessonEditURL(Lesson lesson);

	/**
	 * Returns lesson's comment page URL (absolute) and completes it to point to the specified lesson 
	 * @param lesson we want URL to point to
	 * @return lesson's comment page URL (null if lesson is null or empty)
	 */
	public String getLessonCommentURL(Lesson lesson);

	/**
	 * Returns new lesson test creation page URL (absolute) and completes it to point to the specified lesson 
	 * @param lesson we want URL to point to
	 * @return new lesson test creation page URL (null if lesson is null or empty)
	 */
	public String getLessonNewTestURL(Lesson lesson);
	
	/**
	 * Returns author's lessons page URL (absolute), without specifying an author 
	 * @return author's lessons page URL without auhtor identifier
	 */
	public String getAuthorURL();

	/**
	 * Returns author's lessons page URL (absolute) and completes it to point to the specified author 
	 * @param author we want URL to point to
	 * @return author's lessons page URL (null if author is null or empty)
	 */
	public String getAuthorURL(User author);

	/**
	 * Returns author's drafts page URL (absolute), without specifying an author 
	 * @return author's drafts page URL without auhtor identifier
	 */
	public String getAuthorDraftsURL();

	/**
	 * Returns author's drafts page URL (absolute) and completes it to point to the specified author 
	 * @param author we want URL to point to
	 * @return author's drafts page URL (null if author is null or empty)
	 */
	public String getAuthorDraftsURL(User author);
	
	/**
	 * Returns lesson test page URL (absolute), without specifying a lesson test 
	 * @return lesson test page URL without lesson test identifier
	 */
	public String getLessonTestURL();

	/**
	 * Returns lesson test edit page URL (absolute), without specifying a lesson test 
	 * @return lesson test edit page URL without lesson test identifier
	 */
	public String getLessonTestEditURL();
	
	/**
	 * Returns lesson test's new question page URL (absolute), without specifying a lesson test 
	 * @return lesson test's new question page URL without lesson test identifier
	 */
	public String getLessonTestNewQuestionURL();
	
	/**
	 * Returns lesson test page URL (absolute) and completes it to point to the specified test 
	 * @param test we want URL to point to
	 * @return lesson test page URL (null if test is null or empty)
	 */
	public String getLessonTestURL(LessonTest test);
	
	/**
	 * Returns lesson test edit page URL (absolute) and completes it to point to the specified test 
	 * @param test we want URL to point to
	 * @return lesson test edit page URL (null if test is null or empty)
	 */
	public String getLessonTestEditURL(LessonTest test);
	
	/**
	 * Returns lesson test's new question page URL (absolute) and completes it to point to the specified test 
	 * @param test we want URL to point to
	 * @return lesson test's new question page URL (null if test is null or empty)
	 */
	public String getLessonTestNewQuestionURL(LessonTest test);

	
	/**
	 * Return lesson test question edit page URL (absolute) and completes it to point ti the specified test
	 * @param idLessonTest we want the URL to point to
	 * @return lesson test question edit page URL (null if test is null or empty)
	 */
	public String getLessonTestEditQuestionURL(int idLessonTest);

	/**
	 * Returns lessons from tag page URL (absolute), without specifying a tag 
	 * @return lessons from tag page URL without tag identifier
	 */
	public String getTagURL();
	
	/**
	 * Returns lessons from tag page URL (absolute) and completes it to point to the specified tag 
	 * @param tag we want URL to point to
	 * @return lessons from tag page URL (null if tag is null or empty)
	 */
	public String getTagURL(Tag tag);

	/**
	 * Returns media file URL (absolute), without specifying a media file 
	 * @return media file URL without media file identifier
	 */
	public String getMediaFileURL();
	
	/**
	 * Returns media file URL (absolute) and completes it to point to the specified media file 
	 * @param media we want URL to point to
	 * @return media file URL (null if media is null or empty)
	 */
	public String getMediaFileURL(MediaFile media);

}
