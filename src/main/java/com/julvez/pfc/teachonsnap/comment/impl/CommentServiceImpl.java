package com.julvez.pfc.teachonsnap.comment.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.julvez.pfc.teachonsnap.comment.CommentService;
import com.julvez.pfc.teachonsnap.comment.model.Comment;
import com.julvez.pfc.teachonsnap.comment.model.CommentMessageKey;
import com.julvez.pfc.teachonsnap.comment.repository.CommentRepository;
import com.julvez.pfc.teachonsnap.comment.repository.CommentRepositoryFactory;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.notify.NotifyServiceFactory;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.text.TextServiceFactory;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;


/**
 * Implementation of the CommentService interface, uses an internal CommentRepository 
 * to access the Comments related data.
 * 
 * It also uses User, Lesson, Text and Notify services.
 *
 */
public class CommentServiceImpl implements CommentService {
	
	private CommentRepository commentRepository = CommentRepositoryFactory.getRepository();
	
	private UserService userService = UserServiceFactory.getService();
	private LessonService lessonService = LessonServiceFactory.getService();
	private TextService textService = TextServiceFactory.getService();
	private NotifyService notifyService = NotifyServiceFactory.getService();
	
	
	@Override
	public List<Comment> getComments(int idLesson, int firstResult) {
		List<Comment> comments = Collections.emptyList();
		
		// If is a valid lesson id
		if(idLesson > 0){		
		
			// get list of comments ids from repository
			List<Integer> ids = commentRepository.getCommentIDs(idLesson, firstResult);
			
			// if no repo error
			if(ids != null){
				comments = new ArrayList<Comment>();

				// Fill in a list of Comment from the list of IDs
				for(int id:ids){
					comments.add(getComment(id));
				}
			}
		}		
		return comments;
	}

	@Override
	public Comment getComment(int idComment) {
		Comment comment = null;
		
		// If is a valid Comment id
		if(idComment > 0){
			// get Comment from repository
			comment = commentRepository.getComment(idComment);
			
			// if no repo error
			if(comment != null){
				// set Comment's author
				comment.setUser(userService.getUser(comment.getIdUser()));
			}
		}		
		return comment;
	}

	@Override
	public Comment createComment(int idLesson, int idUser, String commentBody, int idParentComment) {
		Comment comment = null;

		//check params
		if(idLesson > 0 && idUser > 0 && commentBody != null){
			int idComment = -1;			
			
			//create comment in the repository
			idComment = commentRepository.createComment(idLesson, idUser, commentBody);
			
			//if no repo error and this comment is an answer to a previous comment
			if(idComment>0 && idParentComment>0){
				Comment parentComment = getComment(idParentComment);
				
				//if it's a valid parent comment
				if(parentComment!=null){
					//handle nested comments
					idParentComment = parentComment.isResponse()?parentComment.getIdParentComment():idParentComment;
					
					//save parent comment in repo
					commentRepository.saveCommentParent(idComment, idParentComment);
					comment = getComment(idComment);
					
					//notify new answer for prevoiuos comment
					notifyParentComment(idLesson, comment, idParentComment);
				}
			}
			else if(idComment>0){
				//if no repo error
				comment = getComment(idComment);
			}
			else{
				//if repo error
				comment = null;
			}
			//notify new comment
			notifyComment(idLesson, comment);
		}
		return comment;
	}

	@Override
	public void saveCommentBody(int idComment, int idUser, String commentBody) {
		Comment comment = getComment(idComment);
		
		//Save comment body only if valid comment and user is modifying is the same comment's author
		if(comment != null && comment.getIdUser() == idUser){
			commentRepository.saveCommentBody(idComment,commentBody);
		}		
	}

	@Override
	public void blockComment(int idComment, User admin, String reason) {
		Comment comment = getComment(idComment);
		
		//Block only if valid and unblocked comment, and admin is a valid administrator
		if(comment != null && !comment.isBanned() && admin != null && admin.isAdmin()){
			commentRepository.blockComment(idComment, admin.getId(), reason);			
		}
		
	}

	@Override
	public void unblockComment(int idComment, User admin) {
		Comment comment = getComment(idComment);
		
		//Unblock only if valid and blocked comment, and admin is a valid administrator
		if(comment!=null && comment.isBanned() && admin!=null && admin.isAdmin()){
			commentRepository.unblockComment(idComment);			
		}
	}

	@Override
	public boolean notifyComment(int idLesson, Comment comment) {		
		boolean success = false;
		Lesson lesson = lessonService.getLesson(idLesson);
		
		//if valid comment and lesson
		if(comment != null && lesson != null){
			User author = userService.getUser(comment.getIdUser());
			String url = lesson.getURL();

			//get lesson followers
			List<User> followers = userService.getLessonFollowers(lesson);
			
			//for each follower
			for(User follower:followers){
				if(follower.getId()!= author.getId()){ //Ignore notification for the comment's author
					//Get localized notification texts and notify
					String subject = textService.getLocalizedText(follower.getLanguage(),CommentMessageKey.NEW_COMMENT_SUBJECT, lesson.getTitle());
					String message = textService.getLocalizedText(follower.getLanguage(),CommentMessageKey.NEW_COMMENT_MESSAGE, url, author.getFullName(), lesson.getTitle());
					notifyService.info(follower, subject, message);
				}
			}
		}
		return success;		
	}

	@Override
	public boolean notifyParentComment(int idLesson, Comment comment, int idParentComment) {
		boolean success = false;
		Lesson lesson = lessonService.getLesson(idLesson);
		Comment parentComment = getComment(idParentComment);
		
		if(comment != null && lesson != null && parentComment != null){
			User author = userService.getUser(comment.getIdUser());
			User parentAuthor = userService.getUser(parentComment.getIdUser());
			
			String url = lesson.getURL();

			if(parentAuthor.getId() != author.getId()){ //Ignore notification for the comment's author
				//Get localized notification texts and notify
				String subject = textService.getLocalizedText(parentAuthor.getLanguage(),CommentMessageKey.REPLY_COMMENT_SUBJECT, lesson.getTitle());
				String message = textService.getLocalizedText(parentAuthor.getLanguage(),CommentMessageKey.REPLY_COMMENT_MESSAGE, url, author.getFullName(), lesson.getTitle());
				notifyService.info(parentAuthor, subject, message);				
			}		
		}		
		return success;		
	}

}
