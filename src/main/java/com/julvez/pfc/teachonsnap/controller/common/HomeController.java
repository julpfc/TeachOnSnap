package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.tag.CloudTag;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.service.lesson.LessonPropertyName;
import com.julvez.pfc.teachonsnap.service.tag.TagService;
import com.julvez.pfc.teachonsnap.service.tag.TagServiceFactory;
import com.julvez.pfc.teachonsnap.service.url.Attribute;
import com.julvez.pfc.teachonsnap.service.url.ControllerURI;

public class HomeController extends CommonController {

	private static final long serialVersionUID = 1L;

	private LessonService lessonService = LessonServiceFactory.getService();
	private TagService tagService = TagServiceFactory.getService();

	private final int MAX_RESULTS_PAGE = properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO TOP de lessons (más vistas, activas)
		
		List<Lesson> lastLessons = lessonService.getLastLessons(0);
				
		if(lastLessons.size()>MAX_RESULTS_PAGE){			
			lastLessons.remove(MAX_RESULTS_PAGE);		
			
			String nextPage = ControllerURI.LESSONS_BY_LAST.toString() + MAX_RESULTS_PAGE;
			
			requestManager.setAttribute(request, Attribute.STRING_NEXTPAGE, nextPage);
		}
		
		List<CloudTag> cloudTags = tagService.getCloudTags();
		
		List<CloudTag> authorCloudTags = tagService.getAuthorCloudTags();
		requestManager.setAttribute(request, Attribute.LIST_CLOUDTAG_AUTHOR, authorCloudTags);
		
		requestManager.setAttribute(request, Attribute.LIST_LESSON, lastLessons);
		requestManager.setAttribute(request, Attribute.LIST_CLOUDTAG, cloudTags);
		
		request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);	 

	}

	@Override
	protected boolean isPrivateZone() {		
		return false;
	}

}
