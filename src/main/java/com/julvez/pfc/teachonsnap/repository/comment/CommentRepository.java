package com.julvez.pfc.teachonsnap.repository.comment;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.comment.Comment;

public interface CommentRepository {

	public List<Integer> getCommentIDs(int idLesson, int firstResult);

	public Comment getComment(int idComment);

	public int createComment(int idLesson, int idUser, String commentBody);

	public void saveCommentParent(int idComment, int idParentComment);

	public void saveCommentBody(int idComment, String commentBody);

	public void blockComment(int idComment, int idAdmin, String reason);

	public void unblockComment(int idComment);

}
