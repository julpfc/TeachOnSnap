package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.test.LessonTestService;
import com.julvez.pfc.teachonsnap.lesson.test.LessonTestServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.test.model.LessonTest;
import com.julvez.pfc.teachonsnap.link.LinkService;
import com.julvez.pfc.teachonsnap.link.LinkServiceFactory;
import com.julvez.pfc.teachonsnap.link.model.Link;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.media.MediaFileService;
import com.julvez.pfc.teachonsnap.media.MediaFileServiceFactory;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.TagServiceFactory;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.user.model.User;

public class PreviewLessonController extends CommonController {

	private static final long serialVersionUID = 7608540908435958036L;

	private LessonService lessonService = LessonServiceFactory.getService();
	private TagService tagService = TagServiceFactory.getService();
	private LinkService linkService = LinkServiceFactory.getService();
	private MediaFileService mediaFileService = MediaFileServiceFactory.getService();
	private LessonTestService lessonTestService = LessonTestServiceFactory.getService();
	
	private StringManager stringManager = StringManagerFactory.getManager();
	

	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {

		String[] params = requestManager.splitParamsFromControllerURI(request);
				
		if(params!=null && params.length>0 && stringManager.isNumeric(params[0])){
			int idLesson = Integer.parseInt(params[0]);
			
			Lesson lesson = lessonService.getLesson(idLesson);
			
			if(lesson!=null){
				List<Tag> tags = tagService.getLessonTags(lesson.getId());
				List<Link> moreInfoLinks = linkService.getMoreInfoLinks(lesson.getId());
				List<Link> sourceLinks = linkService.getSourceLinks(lesson.getId());
				List<MediaFile> medias = mediaFileService.getLessonMedias(lesson.getIdLessonMedia());
				
				if(lesson.isTestAvailable()){
					LessonTest test = lessonTestService.getLessonTest(lesson);
					requestManager.setAttribute(request, Attribute.LESSONTEST_QUESTIONS, test);
					
					if(visit!= null && visit.getUser()!=null){
						UserTestRank testRank = visitService.getUserTestRank(test.getId(), visit.getUser().getId());			
						requestManager.setAttribute(request, Attribute.USERTESTRANK, testRank);
					}
					List<UserTestRank> testRanks = visitService.getTestRanks(test.getId());
					requestManager.setAttribute(request, Attribute.LIST_USERTESTRANKS, testRanks);
				}
				
				requestManager.setAttribute(request, Attribute.LESSON, lesson);
				requestManager.setAttribute(request, Attribute.LIST_MEDIAFILE_LESSONFILES, medias);
				requestManager.setAttribute(request, Attribute.LIST_TAG, tags);
				requestManager.setAttribute(request, Attribute.LIST_LINK_MOREINFO, moreInfoLinks);
				requestManager.setAttribute(request, Attribute.LIST_LINK_SOURCES, sourceLinks);

				request.getRequestDispatcher("/WEB-INF/views/lesson.jsp").forward(request, response);
			}
			else{
				//hay URI pero no es válida o error en repo 404
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
		else{
			//Sin lessonURI -> Mandar a error 400
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
				
	}

	@Override
	protected boolean isPrivateZone() {
		return false;
	}

}
