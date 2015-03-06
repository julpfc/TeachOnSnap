package com.julvez.pfc.teachonsnap.service.comment;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.comment.Comment;

public interface CommentService {

	public List<Comment> getComments(int idLesson);
	public Comment getComment(int idComment);
}