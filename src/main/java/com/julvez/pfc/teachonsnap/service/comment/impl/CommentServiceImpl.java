package com.julvez.pfc.teachonsnap.service.comment.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.model.comment.Comment;
import com.julvez.pfc.teachonsnap.repository.comment.CommentRepository;
import com.julvez.pfc.teachonsnap.repository.comment.CommentRepositoryFactory;
import com.julvez.pfc.teachonsnap.service.comment.CommentService;
import com.julvez.pfc.teachonsnap.service.user.UserService;
import com.julvez.pfc.teachonsnap.service.user.UserServiceFactory;

public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository = CommentRepositoryFactory.getRepository();
	private UserService userService = UserServiceFactory.getService();

	@Override
	public List<Comment> getComments(int idLesson) {
		List<Comment> comments = new ArrayList<Comment>();
		
		List<Integer> ids = commentRepository.getCommentIDs(idLesson);
		
		for(int id:ids){
			comments.add(getComment(id));
			}
		
		return comments;
	}

	@Override
	public Comment getComment(int idComment) {
		Comment comment = commentRepository.getComment(idComment);
		comment.setUser(userService.getUser(comment.getIdUser()));
		
		return comment;
	}



}
