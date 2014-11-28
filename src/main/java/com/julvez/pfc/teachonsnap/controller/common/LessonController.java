package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.Link;
import com.julvez.pfc.teachonsnap.model.lesson.Tag;
import com.julvez.pfc.teachonsnap.model.lesson.VideoFile;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;

public class LessonController extends CommonController {

	private static final long serialVersionUID = 7608540908435958036L;
	
	private LessonService lessonService = LessonServiceFactory.getService();

	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String lessonURI = request.getRequestURI().replaceFirst(request.getServletPath()+"/", "");
		
		Lesson lesson = lessonService.getLessonFromURI(lessonURI);
		List<Tag> tags = lessonService.getLessonTags(lesson.getId());
		List<Lesson> linkedLessons = lessonService.getLinkedLessons(lesson.getId());
		List<Link> moreInfoLinks = lessonService.getMoreInfoLinks(lesson.getId());
		List<Link> sourceLinks = lessonService.getSourceLinks(lesson.getId());
		List<VideoFile> videos = lessonService.getLessonVideos(lesson.getIdLessonVideo());
		
		request.setAttribute("lesson", lesson);
		request.setAttribute("videos", videos);
		request.setAttribute("tags", tags);
		request.setAttribute("linkedLessons", linkedLessons);
		request.setAttribute("moreInfoLinks", moreInfoLinks);
		request.setAttribute("sourceLinks", sourceLinks);
				
	    request.getRequestDispatcher("/WEB-INF/views/lesson.jsp").forward(request, response);
	}

}
