package com.julvez.pfc.teachonsnap.comment;

import java.util.List;

import com.julvez.pfc.teachonsnap.comment.model.Comment;
import com.julvez.pfc.teachonsnap.comment.model.CommentPropertyName;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Provides the functionality to work with lesson's comments.
 */
public interface CommentService {

	/**
	 * Returns a list of comments from the specified lesson (idLesson). If the lesson has more than the maximum
	 * number of comments allowed for a page {@link CommentPropertyName}, it will paginate them. 
	 * @param idLesson a lesson id to look for comments
	 * @param firstResult first comment from the pagination should start.
	 * @return comments list for this lesson, pagination starts at the firstResult element	 * 
	 * @see Lesson
	 */
	public List<Comment> getComments(int idLesson, int firstResult);
	
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
	 * @param idParentComment Parent comment this comment answers to, if present (idParentComment>0)
	 * @return the new comment created
	 * @see Comment
	 * @see Lesson
	 * @see User
	 */
	public Comment createComment(int idLesson, int idUser, String commentBody, int idParentComment);
	
	/**
	 * Saves the modified body of a comment
	 * @param idComment Comment to be modified
	 * @param idUser User who pretends to modify the comment
	 * @param commentBody Comment body
	 * @see User
	 * @see Comment
	 */
	public void saveCommentBody(int idComment, int idUser, String commentBody);
	
	/**
	 * Blocks a comment, only by an administrator user, who should specify a reason
	 * @param idComment Comment to be blocked
	 * @param admin Administrator user who blocks the comment
	 * @param reason Reason to block this comment, specified by the administrator
	 * @see Comment
	 */
	public void blockComment(int idComment, User admin, String reason);
	
	/**
	 * Unblocks a previously blocked comment, can only be called by an administrator user
	 * @param idComment Blocked comment to be unblocked
	 * @param admin Adminsitrator user who unblock the comment
	 * @see Comment
	 */
	public void unblockComment(int idComment, User admin);

	/**
	 * Notifies a new comment on a lesson to users subscribed/following this lesson
	 * @param idLesson Lesson which receives a new comment
	 * @param comment
	 * @return true if all followers(users) are notified correctly
	 * @see Lesson
	 */
	public boolean notifyComment(int idLesson, Comment comment);
	
	/**
	 * Notifies a user than his comment has a new answer
	 * @param idLesson Lesson where the comments where made
	 * @param comment Answer to the parent comment
	 * @param idParentComment Parent comment, which author will be notified
	 * @return true if the parent comment's author is notified correctly
	 * @see Lesson
	 */
	public boolean notifyParentComment(int idLesson, Comment comment, int idParentComment);
}
