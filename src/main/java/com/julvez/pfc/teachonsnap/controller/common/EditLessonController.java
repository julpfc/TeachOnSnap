package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.test.LessonTest;
import com.julvez.pfc.teachonsnap.model.link.Link;
import com.julvez.pfc.teachonsnap.model.media.MediaFile;
import com.julvez.pfc.teachonsnap.model.page.Page;
import com.julvez.pfc.teachonsnap.model.tag.Tag;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.model.visit.Visit;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.service.lesson.test.LessonTestService;
import com.julvez.pfc.teachonsnap.service.lesson.test.LessonTestServiceFactory;
import com.julvez.pfc.teachonsnap.service.link.LinkService;
import com.julvez.pfc.teachonsnap.service.link.LinkServiceFactory;
import com.julvez.pfc.teachonsnap.service.media.MediaFileService;
import com.julvez.pfc.teachonsnap.service.media.MediaFileServiceFactory;
import com.julvez.pfc.teachonsnap.service.page.PageService;
import com.julvez.pfc.teachonsnap.service.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.service.tag.TagService;
import com.julvez.pfc.teachonsnap.service.tag.TagServiceFactory;
import com.julvez.pfc.teachonsnap.service.url.Attribute;
import com.julvez.pfc.teachonsnap.service.url.SessionAttribute;

public class EditLessonController extends CommonController {

	private static final long serialVersionUID = 7608540908435958036L;
	
	private LessonService lessonService = LessonServiceFactory.getService();
	private TagService tagService = TagServiceFactory.getService();
	private LinkService linkService = LinkServiceFactory.getService();
	private LessonTestService lessonTestService = LessonTestServiceFactory.getService();
	private MediaFileService mediaFileService = MediaFileServiceFactory.getService();
	private PageService pageService = PageServiceFactory.getService();

	private StringManager stringManager = StringManagerFactory.getManager();

	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params!=null && params.length>0 && stringManager.isNumeric(params[0])){
			
			int idLesson = Integer.parseInt(params[0]);
			
			Lesson lesson = lessonService.getLesson(idLesson);
			
			if(lesson!= null){
				
				User user = null;
				Visit visit = requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class);
				if(visit!=null) user = visit.getUser();
				
				if(user.isAdmin() || (user.isAuthor() && user.getId() == lesson.getIdUser())){
					
					//TODO POST data
					
					List<Tag> tags = tagService.getLessonTags(lesson.getId());
					List<Lesson> linkedLessons = lessonService.getLinkedLessons(lesson.getId());
					List<Link> moreInfoLinks = linkService.getMoreInfoLinks(lesson.getId());
					List<Link> sourceLinks = linkService.getSourceLinks(lesson.getId());
					List<MediaFile> medias = mediaFileService.getLessonMedias(lesson.getIdLessonMedia());
					
					LessonTest test = lessonTestService.getLessonTest(lesson);

					List<Page> pageStack = pageService.getEditLessonPageStack(lesson);
					request.setAttribute(Attribute.LIST_PAGE_STACK.toString(), pageStack);
									
					request.setAttribute(Attribute.LESSON.toString(), lesson);
					request.setAttribute(Attribute.LIST_MEDIAFILE_LESSONFILES.toString(), medias);
					request.setAttribute(Attribute.LIST_TAG_LESSONTAGS.toString(), tags);
					request.setAttribute(Attribute.LIST_LESSON_LINKEDLESSONS.toString(), linkedLessons);
					request.setAttribute(Attribute.LIST_LINK_MOREINFO.toString(), moreInfoLinks);
					request.setAttribute(Attribute.LIST_LINK_SOURCES.toString(), sourceLinks);
					request.setAttribute(Attribute.LESSONTEST_QUESTIONS.toString(), test);
					
					
					//TODO Editar Linked lesson?
					
					request.getRequestDispatcher("/WEB-INF/views/editLesson.jsp").forward(request, response);
				}
				else{
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
				}
			}
			else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}			
		}
		else{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
		
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}

}
