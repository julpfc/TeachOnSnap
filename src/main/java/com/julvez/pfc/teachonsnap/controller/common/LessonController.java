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
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.error.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.error.model.ErrorType;
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
import com.julvez.pfc.teachonsnap.user.model.User;

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
			HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {

		String[] params = requestManager.splitParamsFromControllerURI(request);
				
		if(params!=null && params.length>0){
			
			String lessonURI= params[0];
			
			Lesson lesson = lessonService.getLessonFromURI(lessonURI);
			
			if(lesson!=null){

				boolean follow = false;
				
				if(user != null){
					int idFollowLesson = requestManager.getNumericParameter(request, Parameter.FOLLOW_LESSON);
					
					if(idFollowLesson > 0){
						follow = true;
					
						Lesson followLesson = lessonService.getLesson(idFollowLesson);
						
						if(followLesson != null && followLesson.getId() == lesson.getId()){
							User modUser = userService.followLesson(user, followLesson);
							
							if(modUser != null){
								setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.USER_SAVED);
								user = modUser;
							}
							else{
								setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
							}		
							response.sendRedirect(request.getRequestURI());
						}
						else{
							response.sendError(HttpServletResponse.SC_NOT_FOUND);
						}
					}
					
					int idUnFollowLesson = requestManager.getNumericParameter(request, Parameter.UNFOLLOW_LESSON);
					
					if(idUnFollowLesson > 0){
						follow = true;
					
						Lesson unfollowLesson = lessonService.getLesson(idUnFollowLesson);
						
						if(unfollowLesson != null && unfollowLesson.getId() == lesson.getId()){
							User modUser = userService.unfollowLesson(user, unfollowLesson);
							
							if(modUser != null){
								setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.USER_SAVED);
								user = modUser;
							}
							else{
								setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
							}		
							response.sendRedirect(request.getRequestURI());
						}
						else{
							response.sendError(HttpServletResponse.SC_NOT_FOUND);
						}
					}
				}	
				if(!follow){
					int pageResult = 0;
					boolean hasNextPage = false;
					
					if(visit != null && !visit.isViewedLesson(lesson.getId())){
						visit = statsService.saveLesson(visit, lesson);
						if(visit != null){
							requestManager.setSessionAttribute(request, SessionAttribute.VISIT, visit);
						}
					}
					
					List<Tag> tags = tagService.getLessonTags(lesson.getId());
					List<Link> moreInfoLinks = linkService.getMoreInfoLinks(lesson.getId());
					List<Link> sourceLinks = linkService.getSourceLinks(lesson.getId());
					List<MediaFile> medias = mediaFileService.getLessonMedias(lesson.getIdLessonMedia());
					
					if(lesson.isTestAvailable()){
						LessonTest test = lessonTestService.getLessonTest(lesson);
						requestManager.setAttribute(request, Attribute.LESSONTEST_QUESTIONS, test);
						
						if(visit!= null && visit.getUser()!=null){
							UserTestRank testRank = statsService.getUserTestRank(test.getId(), visit.getUser().getId());			
							requestManager.setAttribute(request, Attribute.USERTESTRANK, testRank);
						}
						List<UserTestRank> testRanks = statsService.getTestRanks(test.getId());
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
					requestManager.setAttribute(request, Attribute.LIST_TAG, tags);
					requestManager.setAttribute(request, Attribute.LIST_LINK_MOREINFO, moreInfoLinks);
					requestManager.setAttribute(request, Attribute.LIST_LINK_SOURCES, sourceLinks);
					requestManager.setAttribute(request, Attribute.LIST_COMMENTS, comments);
					requestManager.setAttribute(request, Attribute.STRING_NEXTPAGE, nextPage);
					requestManager.setAttribute(request, Attribute.STRING_PREVPAGE, prevPage);
	
					request.getRequestDispatcher("/WEB-INF/views/lesson.jsp").forward(request, response);
				}
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
