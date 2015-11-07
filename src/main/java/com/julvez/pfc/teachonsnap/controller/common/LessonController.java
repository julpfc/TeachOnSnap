package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.comment.CommentService;
import com.julvez.pfc.teachonsnap.comment.CommentServiceFactory;
import com.julvez.pfc.teachonsnap.comment.model.Comment;
import com.julvez.pfc.teachonsnap.comment.model.CommentPropertyName;
import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.test.LessonTestService;
import com.julvez.pfc.teachonsnap.lesson.test.LessonTestServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.test.model.LessonTest;
import com.julvez.pfc.teachonsnap.link.LinkService;
import com.julvez.pfc.teachonsnap.link.LinkServiceFactory;
import com.julvez.pfc.teachonsnap.link.model.Link;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
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

public class LessonController extends CommonController {

	private static final long serialVersionUID = 7608540908435958036L;

	private LessonService lessonService = LessonServiceFactory.getService();
	private TagService tagService = TagServiceFactory.getService();
	private LinkService linkService = LinkServiceFactory.getService();
	private MediaFileService mediaFileService = MediaFileServiceFactory.getService();
	private CommentService commentService = CommentServiceFactory.getService();
	private LessonTestService lessonTestService = LessonTestServiceFactory.getService();
	
	private StringManager stringManager = StringManagerFactory.getManager();
	private PropertyManager properties = PropertyManagerFactory.getManager();

	protected final int MAX_COMMENTS_PAGE = properties.getNumericProperty(CommentPropertyName.MAX_PAGE_COMMENTS);

	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String[] params = requestManager.splitParamsFromControllerURI(request);

		String lessonURI = null;
		
		if(params!=null){
			lessonURI = params[0];
			
			Lesson lesson = lessonService.getLessonFromURI(lessonURI);
			
			if(lesson!=null){
				int pageResult = 0;
				boolean hasNextPage = false;
				
				Visit visit = requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class);
				
				if(visit != null && !visit.isViewedLesson(lesson.getId())){
					visit = visitService.saveLesson(visit, lesson);
				}
				
				//TODO actualizar session con visit?
				
				List<Tag> tags = tagService.getLessonTags(lesson.getId());
				List<Lesson> linkedLessons = lessonService.getLinkedLessons(lesson.getId());
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
				
				if(params.length > 1 && stringManager.isNumeric(params[1])){
					pageResult = Integer.parseInt(params[1]);
				}
				List<Comment> comments = commentService.getComments(lesson.getId(), pageResult);
				
				if(comments.size()>MAX_COMMENTS_PAGE){
					hasNextPage = true;
					comments.remove(MAX_COMMENTS_PAGE);
				}
				
				String nextPage = null;
				if(hasNextPage){
					nextPage = request.getServletPath()+"/"+lessonURI+"/"+(pageResult+MAX_COMMENTS_PAGE);
				}
				
				String prevPage = null;
				if(pageResult>0){
					prevPage = request.getServletPath()+"/"+lessonURI;
					if(pageResult>MAX_COMMENTS_PAGE){
						prevPage = prevPage + "/" + (pageResult-MAX_COMMENTS_PAGE);
					}
				}
				
				requestManager.setAttribute(request, Attribute.LESSON, lesson);
				requestManager.setAttribute(request, Attribute.LIST_MEDIAFILE_LESSONFILES, medias);
				requestManager.setAttribute(request, Attribute.LIST_TAG_LESSONTAGS, tags);
				requestManager.setAttribute(request, Attribute.LIST_LESSON_LINKEDLESSONS, linkedLessons);
				requestManager.setAttribute(request, Attribute.LIST_LINK_MOREINFO, moreInfoLinks);
				requestManager.setAttribute(request, Attribute.LIST_LINK_SOURCES, sourceLinks);
				requestManager.setAttribute(request, Attribute.LIST_COMMENTS, comments);
				requestManager.setAttribute(request, Attribute.STRING_NEXTPAGE, nextPage);
				requestManager.setAttribute(request, Attribute.STRING_PREVPAGE, prevPage);

				request.getRequestDispatcher("/WEB-INF/views/lesson.jsp").forward(request, response);
			}
			else{
				//TODO hay URI pero no es válida o error en repo: 400 o 500
			}
		}
		else{
			//Sin lessonURI -> Mandar a error 404
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
				
	}

	@Override
	protected boolean isPrivateZone() {
		return false;
	}

}
