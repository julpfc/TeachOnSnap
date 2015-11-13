package com.julvez.pfc.teachonsnap.link;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.link.model.Link;

public interface LinkService {

	public Link getLink(int idLink);
	
	public List<Link> getMoreInfoLinks(int idLesson);
	
	public List<Link> getSourceLinks(int idLesson);
	
	public void addLessonSources(Lesson lesson, List<String> sources);

	public int createLink(String URL);

	public void addLessonMoreInfo(Lesson lesson, List<String> moreInfos);

	public boolean saveLessonMoreInfo(Lesson lesson, List<Link> oldLinks, List<String> newLinks);
	public boolean saveLessonSources(Lesson lesson, List<Link> oldLinks, List<String> newLinks);

	public void removeLessonSources(Lesson lesson, ArrayList<Integer> removeLinkIDs);
	public void removeLessonMoreInfos(Lesson lesson, ArrayList<Integer> removeLinkIDs);

	

}
