package com.julvez.pfc.teachonsnap.link;

import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.link.model.Link;

public interface LinkService {

	public Link getLink(int idLink);
	
	public List<Link> getMoreInfoLinks(int idLesson);
	
	public List<Link> getSourceLinks(int idLesson);
	
	public Lesson addLessonSources(Lesson lesson, List<String> sources);

	public int createLink(String URL);

	public Lesson addLessonMoreInfo(Lesson lesson, List<String> moreInfos);

}
