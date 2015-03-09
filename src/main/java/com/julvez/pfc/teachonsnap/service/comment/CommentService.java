package com.julvez.pfc.teachonsnap.service.comment;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.comment.Comment;

public interface CommentService {

	public List<Comment> getComments(int idLesson);
	public Comment getComment(int idComment);
	public Comment createComment(int idLesson, int idUser, String commentBody, int idParentComment);
	public void saveCommentBody(int idComment, int idUser, String commentBody);
	
}
