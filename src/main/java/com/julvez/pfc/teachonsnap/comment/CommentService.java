package com.julvez.pfc.teachonsnap.comment;

import java.util.List;

import com.julvez.pfc.teachonsnap.comment.model.Comment;
import com.julvez.pfc.teachonsnap.user.model.User;

public interface CommentService {

	public List<Comment> getComments(int idLesson, int firstResult);
	public Comment getComment(int idComment);
	public Comment createComment(int idLesson, int idUser, String commentBody, int idParentComment);
	public void saveCommentBody(int idComment, int idUser, String commentBody);
	public void blockComment(int idComment, User admin, String reason);
	public void unblockComment(int idComment, User admin);

	public boolean notifyComment(int idLesson, Comment comment);
	public boolean notifyParentComment(int idLesson, Comment comment, int idParentComment);
}
