package com.julvez.pfc.teachonsnap.comment.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.comment.model.Comment;
import com.julvez.pfc.teachonsnap.comment.model.CommentPropertyName;

/**
 * Repository to access/modify data related to comments.
 * <p>
 * To be used only by the CommentService's implementation *
 */
public interface CommentRepository {
	
	/**
	 * Get a list of comment IDs from this lesson. If the lesson has more than the maximum
	 * number of comments allowed for a page {@link CommentPropertyName}, it will paginate them. 
	 * @param idLesson Lesson to get the comments from
	 * @param firstResult first comment from the pagination should start.
	 * @return a list of Comment IDs
	 */
	public List<Integer> getCommentIDs(int idLesson, int firstResult);

	/**
	 * Returns the comment object specified by its id
	 * @param idComment Comment id
	 * @return the comment specified
	 */
	public Comment getComment(int idComment);

	/**
	 * Creates a new comment at the lesson specified
	 * @param idLesson lesson where the comment will be created
	 * @param idUser comment's author
	 * @param commentBody comment body
	 * @return the new comment ID
	 */
	public int createComment(int idLesson, int idUser, String commentBody);

	/**
	 * Associates a comment with the parent comment which answers to
	 * @param idComment Answer comment
	 * @param idParentComment Parent comment
	 */
	public void saveCommentParent(int idComment, int idParentComment);

	/**
	 * Modify the comment's body with a new text
	 * @param idComment Comment which is modified
	 * @param commentBody New comment's body
	 */
	public void saveCommentBody(int idComment, String commentBody);

	/**
	 * Blocks a comment by an administrator user, who should specify a reason
	 * @param idComment Comment to be blocked
	 * @param idAdmin Administrator user who blocks the comment
	 * @param reason Reason to block this comment, specified by the administrator
	 */
	public void blockComment(int idComment, int idAdmin, String reason);

	/**
	 * Unblocks a previously blocked comment
	 * @param idComment Blocked comment to be unblocked
	 */
	public void unblockComment(int idComment);

}
