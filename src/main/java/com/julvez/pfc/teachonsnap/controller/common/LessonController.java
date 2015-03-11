package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.manager.request.Attribute;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.comment.Comment;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.link.Link;
import com.julvez.pfc.teachonsnap.model.media.MediaFile;
import com.julvez.pfc.teachonsnap.model.tag.Tag;
import com.julvez.pfc.teachonsnap.service.comment.CommentService;
import com.julvez.pfc.teachonsnap.service.comment.CommentServiceFactory;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.service.link.LinkService;
import com.julvez.pfc.teachonsnap.service.link.LinkServiceFactory;
import com.julvez.pfc.teachonsnap.service.media.MediaFileService;
import com.julvez.pfc.teachonsnap.service.media.MediaFileServiceFactory;
import com.julvez.pfc.teachonsnap.service.tag.TagService;
import com.julvez.pfc.teachonsnap.service.tag.TagServiceFactory;

public class LessonController extends CommonController {

	private static final long serialVersionUID = 7608540908435958036L;

	protected static final int MAX_COMMENTS_PAGE = 50;
	
	private LessonService lessonService = LessonServiceFactory.getService();
	private TagService tagService = TagServiceFactory.getService();
	private LinkService linkService = LinkServiceFactory.getService();
	private MediaFileService mediaFileService = MediaFileServiceFactory.getService();

	private CommentService commentService = CommentServiceFactory.getService();
	
	private StringManager stringManager = StringManagerFactory.getManager();

	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String[] params = requestManager.getControllerParams(request);

		String lessonURI = null;
		
		if(params!=null){
			lessonURI = params[0];
			
			Lesson lesson = lessonService.getLessonFromURI(lessonURI);
			
			if(lesson!=null){
				int pageResult = 0;
				boolean hasNextPage = false;
				
				List<Tag> tags = tagService.getLessonTags(lesson.getId());
				List<Lesson> linkedLessons = lessonService.getLinkedLessons(lesson.getId());
				List<Link> moreInfoLinks = linkService.getMoreInfoLinks(lesson.getId());
				List<Link> sourceLinks = linkService.getSourceLinks(lesson.getId());
				List<MediaFile> medias = mediaFileService.getLessonMedias(lesson.getIdLessonMedia());
				
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
				request.setAttribute(Attribute.LESSON.toString(), lesson);
				request.setAttribute(Attribute.LIST_MEDIAFILE_LESSONFILES.toString(), medias);
				request.setAttribute(Attribute.LIST_TAG_LESSONTAGS.toString(), tags);
				request.setAttribute(Attribute.LIST_LESSON_LINKEDLESSONS.toString(), linkedLessons);
				request.setAttribute(Attribute.LIST_LINK_MOREINFO.toString(), moreInfoLinks);
				request.setAttribute(Attribute.LIST_LINK_SOURCES.toString(), sourceLinks);
				request.setAttribute(Attribute.LIST_COMMENTS.toString(), comments);
				request.setAttribute(Attribute.STRING_NEXTPAGE.toString(), nextPage);
				request.setAttribute(Attribute.STRING_PREVPAGE.toString(), prevPage);

				request.getRequestDispatcher("/WEB-INF/views/lesson.jsp").forward(request, response);
			}
			else{
				//TODO hay URI pero no es válida o error en repo: 400 o 500
			}
		}
		else{
			//TODO sin lessonURI -> Mandar a error 404 o 400
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			//throw new ServletException("Hola soy una excepción de prueba");
		}
				
	}

	@Override
	protected boolean isPrivateZone() {
		return false;
	}

}
