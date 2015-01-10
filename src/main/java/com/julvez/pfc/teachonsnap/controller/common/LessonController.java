package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.manager.request.Attribute;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.Link;
import com.julvez.pfc.teachonsnap.model.lesson.Tag;
import com.julvez.pfc.teachonsnap.model.media.MediaFile;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.service.media.MediaFileService;
import com.julvez.pfc.teachonsnap.service.media.MediaFileServiceFactory;

public class LessonController extends CommonController {

	private static final long serialVersionUID = 7608540908435958036L;
	
	private LessonService lessonService = LessonServiceFactory.getService();
	private MediaFileService mediaFileService = MediaFileServiceFactory.getService();

	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String lessonURI = request.getRequestURI().replaceFirst(request.getServletPath()+"/", "");
		
		Lesson lesson = lessonService.getLessonFromURI(lessonURI);
		List<Tag> tags = lessonService.getLessonTags(lesson.getId());
		List<Lesson> linkedLessons = lessonService.getLinkedLessons(lesson.getId());
		List<Link> moreInfoLinks = lessonService.getMoreInfoLinks(lesson.getId());
		List<Link> sourceLinks = lessonService.getSourceLinks(lesson.getId());
		List<MediaFile> medias = mediaFileService.getLessonMedias(lesson.getIdLessonMedia());
		
		request.setAttribute(Attribute.LESSON.toString(), lesson);
		request.setAttribute(Attribute.LIST_MEDIAFILE_LESSONFILES.toString(), medias);
		request.setAttribute(Attribute.LIST_TAG_LESSONTAGS.toString(), tags);
		request.setAttribute(Attribute.LIST_LESSON_LINKEDLESSONS.toString(), linkedLessons);
		request.setAttribute(Attribute.LIST_LINK_MOREINFO.toString(), moreInfoLinks);
		request.setAttribute(Attribute.LIST_LINK_SOURCES.toString(), sourceLinks);
				
	    request.getRequestDispatcher("/WEB-INF/views/lesson.jsp").forward(request, response);
	}

	@Override
	protected boolean isPrivateZone() {
		return false;
	}

}
