package com.julvez.pfc.teachonsnap.comment.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.comment.CommentService;
import com.julvez.pfc.teachonsnap.comment.model.Comment;
import com.julvez.pfc.teachonsnap.comment.repository.CommentRepository;
import com.julvez.pfc.teachonsnap.comment.repository.CommentRepositoryFactory;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;

public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository = CommentRepositoryFactory.getRepository();
	private UserService userService = UserServiceFactory.getService();
	
	@Override
	public List<Comment> getComments(int idLesson, int firstResult) {
		List<Comment> comments = new ArrayList<Comment>();
		
		List<Integer> ids = commentRepository.getCommentIDs(idLesson, firstResult);
		
		for(int id:ids){
			comments.add(getComment(id));
			}
		
		return comments;
	}

	@Override
	public Comment getComment(int idComment) {
		Comment comment = null;
		if(idComment>0){
			comment = commentRepository.getComment(idComment);			
			comment.setUser(userService.getUser(comment.getIdUser()));
		}
		
		return comment;
	}

	@Override
	public Comment createComment(int idLesson, int idUser, String commentBody, int idParentComment) {
		Comment comment = null;
		if(idLesson>0 && idUser>0 && commentBody!=null){
			int idComment = -1;			
			idComment = commentRepository.createComment(idLesson, idUser, commentBody);
			
			if(idComment>0 && idParentComment>0){
				Comment parentComment = getComment(idParentComment);
				
				if(parentComment!=null){
					idParentComment = parentComment.isResponse()?parentComment.getIdParentComment():idParentComment;
					commentRepository.saveCommentParent(idComment, idParentComment);						
				}
			}
			else if(idComment>0){
				comment = getComment(idComment);
			}
			else{
				comment = null;
			}
		}
		return comment;
	}

	@Override
	public void saveCommentBody(int idComment, int idUser, String commentBody) {
		Comment comment = getComment(idComment);
		
		if(comment!=null && comment.getIdUser()==idUser){
			commentRepository.saveCommentBody(idComment,commentBody);
		}		
	}

	@Override
	public void blockComment(int idComment, User admin, String reason) {
		Comment comment = getComment(idComment);
		
		if(comment!=null && !comment.isBanned() && admin!=null && admin.isAdmin()){
			commentRepository.blockComment(idComment, admin.getId(), reason);			
		}
		
	}

	@Override
	public void unblockComment(int idComment, User admin) {
		Comment comment = getComment(idComment);
		
		if(comment!=null && comment.isBanned() && admin!=null && admin.isAdmin()){
			commentRepository.unblockComment(idComment);			
		}
	}



}
