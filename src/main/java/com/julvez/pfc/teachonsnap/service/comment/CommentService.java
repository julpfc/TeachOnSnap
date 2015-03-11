package com.julvez.pfc.teachonsnap.service.comment;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.comment.Comment;
import com.julvez.pfc.teachonsnap.model.user.User;

public interface CommentService {

	public List<Comment> getComments(int idLesson, int firstResult);
	public Comment getComment(int idComment);
	public Comment createComment(int idLesson, int idUser, String commentBody, int idParentComment);
	public void saveCommentBody(int idComment, int idUser, String commentBody);
	public void blockComment(int idComment, User admin, String reason);
	public void unblockComment(int idComment, User admin);
	
}
