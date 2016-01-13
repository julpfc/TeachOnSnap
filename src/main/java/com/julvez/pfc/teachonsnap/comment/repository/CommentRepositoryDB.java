package com.julvez.pfc.teachonsnap.comment.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.comment.model.Comment;
import com.julvez.pfc.teachonsnap.comment.model.CommentPropertyName;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;

/**
 * Repository implementation to access/modify data from a Database
 * <p>
 * {@link DBManager} is used to provide database access
 */
public class CommentRepositoryDB implements CommentRepository {
	
	/** Database manager providing access/modification capabilities */
	private DBManager dbm;
		
	/** String manager providing string manipulation utilities */
	private StringManager stringManager;
	
	/** Property manager providing access to properties files */
	private PropertyManager properties;
			
	/**
	 * Constructor requires all parameters not to be null
	 * @param dbm Database manager providing access/modification capabilities
	 * @param stringManager String manager providing string manipulation utilities
	 * @param properties Property manager providing access to properties files
	 */
	public CommentRepositoryDB(DBManager dbm, StringManager stringManager, PropertyManager properties) {
		if(dbm == null || stringManager == null || properties == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.dbm = dbm;
		this.stringManager = stringManager;
		this.properties = properties;
	}

	@Override
	public List<Integer> getCommentIDs(int idLesson, int firstResult) {
		//Get pagination limit for comments (application properties file)
		long maxResults = properties.getNumericProperty(CommentPropertyName.MAX_PAGE_COMMENTS);
		return dbm.getQueryResultList("SQL_COMMENT_GET_COMMENTIDS", Integer.class, idLesson, firstResult, maxResults + 1);
	}

	@Override
	public Comment getComment(int idComment) {
		Comment comment = dbm.getQueryResultUnique("SQL_COMMENT_GET_COMMENT", Comment.class, idComment);
		
		if(comment!=null){
			//escape some characters into HTML entities and convert new lines to HTML paragraphs
			comment.setBody(stringManager.convertToHTMLParagraph(stringManager.escapeHTML(comment.getBody())));
		}
		return comment;
	}

	@Override
	public int createComment(int idLesson, int idUser, String commentBody) {
		int idComment = -1;
		//Begin a transaction on the database
		Object session = dbm.beginTransaction();
		
		//Create comment
		idComment = createComment(session, idLesson, idUser);
		
		if(idComment>0){
			//if success save comment's body
			if(saveCommentBody(session, idComment, commentBody)<0){
				idComment = -1;
			}				
		}
		//if success commit, else rollback
		dbm.endTransaction(idComment>0, session);
				
		return idComment;
	}

	/**
	 * Creates a new comment on the database. Requires a database
	 * transaction within a session.
	 * @param session Open transaction at the database
	 * @param idLesson Lesson where the comment is created
	 * @param idUser Comment's author
	 * @return new comment identifier (-1 if error)
	 */
	private int createComment(Object session, int idLesson, int idUser) {
		return (int)dbm.insertQueryAndGetLastInserID_NoCommit(session, "SQL_COMMENT_CREATE_COMMENT", idLesson, idUser);
	}
	
	
	/**
	 * Update the body of a comment. Requires a database
	 * transaction within a session.
	 * @param session Open transaction at the database
	 * @param idComment Comment to be updated
	 * @param commentBody New comment body
	 * @return rows affected
	 */
	private int saveCommentBody(Object session, int idComment, String commentBody) {
		return (int)dbm.updateQuery_NoCommit(session, "SQL_COMMENT_SAVE_BODY", idComment,commentBody,commentBody);		
	}

	@Override
	public void saveCommentParent(int idComment, int idParentComment) {
		dbm.updateQuery("SQL_COMMENT_SAVE_PARENT", idComment, idParentComment, idParentComment);		
	}

	@Override
	public void saveCommentBody(int idComment, String commentBody) {
		//Begin database transaction
		Object session = dbm.beginTransaction();
		//Save comment's body
		saveCommentBody(session, idComment, commentBody);
		//Update last modification date
		saveCommentEditDate(session, idComment);
		//Commit
		dbm.endTransaction(true, session);		
	}

	/**
	 * Update the last modification date for comment. Requires a database
	 * transaction within a session.
	 * @param session Open transaction at the database
	 * @param idComment Comment to be updated
	 */
	private void saveCommentEditDate(Object session, int idComment) {
		dbm.updateQuery_NoCommit(session, "SQL_COMMENT_SAVE_DATEEDIT", idComment);		
	}

	@Override
	public void blockComment(int idComment, int idAdmin, String reason) {
		dbm.updateQuery("SQL_COMMENT_ADD_BANNED", idComment, idAdmin, reason, idAdmin, reason);		
	}

	@Override
	public void unblockComment(int idComment) {
		dbm.updateQuery("SQL_COMMENT_REMOVE_BANNED", idComment);		
	}

}
