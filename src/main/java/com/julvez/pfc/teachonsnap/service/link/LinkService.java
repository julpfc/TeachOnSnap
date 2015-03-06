package com.julvez.pfc.teachonsnap.service.link;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.link.Link;

public interface LinkService {

	public Link getLink(int idLink);
	
	public List<Link> getMoreInfoLinks(int idLesson);
	
	public List<Link> getSourceLinks(int idLesson);
	
	public Lesson addLessonSources(Lesson lesson, List<String> sources);

	public int createLink(String URL);

	public Lesson addLessonMoreInfo(Lesson lesson, List<String> moreInfos);

}
