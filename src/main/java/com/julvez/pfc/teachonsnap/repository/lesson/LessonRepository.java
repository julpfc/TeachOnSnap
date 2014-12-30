package com.julvez.pfc.teachonsnap.repository.lesson;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.Answer;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.LessonTest;
import com.julvez.pfc.teachonsnap.model.lesson.Link;
import com.julvez.pfc.teachonsnap.model.lesson.Question;
import com.julvez.pfc.teachonsnap.model.lesson.Tag;

public interface LessonRepository {

	public Lesson getLesson(int idLesson);
	
	public List<Integer> getLessonIDsFromTag(String tag, int firstResult);

	public int getLessonIDFromURI(String lessonURI);

	public List<Integer> getLessonTagIDs(int idLesson);

	public List<Integer> getLinkedLessonIDs(int idLesson);

	public List<Integer> getMoreInfoLinkIDs(int idLesson);

	public List<Integer> getSourceLinkIDs(int idLesson);

	public Tag getTag(int idTag);

	public Link getLink(int idLink);

	public List<Integer> getLastLessonIDs(int firstResult);

	public List<Object[]> getCloudTags();

	public List<Integer> getLessonIDsFromAuthor(String author, int firstResult);

	public LessonTest getLessonTest(int idLessonTest);

	public List<Integer> getLessonTestQuestionIDs(int idLessonTest);

	public Question getQuestion(int idQuestion);

	public List<Integer> getQuestionAnswerIDs(int idQuestion);

	public Answer getAnswer(int idAnswer);

	public List<Object[]> getAuthorCloudTags();

	public int createLesson(Lesson newLesson);

	public void saveLessonText(int idLesson, String newText);

	public void addLessonTags(int idLesson, ArrayList<Integer> tagIDs);

	public int getTagID(String tag);

	public int createTag(String tag);

		
}
