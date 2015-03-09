package com.julvez.pfc.teachonsnap.repository.comment.db;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.model.comment.Comment;
import com.julvez.pfc.teachonsnap.repository.comment.CommentRepository;

public class CommentRepositoryDB implements CommentRepository {

	private DBManager dbm = DBManagerFactory.getDBManager();
	
	@Override
	public List<Integer> getCommentIDs(int idLesson) {
		@SuppressWarnings("unchecked")
		List<Integer> ids =  (List<Integer>) dbm.getQueryResultList("SQL_COMMENT_GET_COMMENTIDS", null, idLesson);
						
		return ids;
	}

	@Override
	public Comment getComment(int idComment) {
		return (Comment) dbm.getQueryResultUnique("SQL_COMMENT_GET_COMMENT", Comment.class, idComment);
	}

	@Override
	public int createComment(int idLesson, int idUser, String commentBody) {
		int idComment = -1;
		Object session = dbm.beginTransaction();
		
		idComment = createComment(session, idLesson, idUser);
		
		if(idComment>0){
			if(saveCommentBody(session, idComment, commentBody)<0){
				idComment = -1;
			}				
		}
		
		dbm.endTransaction(idComment>0, session);
				
		return idComment;
	}

	private int createComment(Object session, int idLesson, int idUser) {
		return (int)dbm.updateQuery_NoCommit(session, "SQL_COMMENT_CREATE_COMMENT", idLesson, idUser);
	}
	
	
	private int saveCommentBody(Object session, int idComment, String commentBody) {
		return (int)dbm.updateQuery_NoCommit(session, "SQL_COMMENT_SAVE_BODY", idComment,commentBody,commentBody);
		
	}

	@Override
	public void saveCommentParent(int idComment, int idParentComment) {
		dbm.updateQuery("SQL_COMMENT_SAVE_PARENT", idComment, idParentComment, idParentComment);		
	}

	@Override
	public void saveCommentBody(int idComment, String commentBody) {
		Object session = dbm.beginTransaction();
		saveCommentBody(session, idComment, commentBody);
		saveCommentEditDate(session, idComment);
		dbm.endTransaction(true, session);		
	}

	private void saveCommentEditDate(Object session, int idComment) {
		dbm.updateQuery_NoCommit(session, "SQL_COMMENT_SAVE_DATEEDIT", idComment);		
	}

}
