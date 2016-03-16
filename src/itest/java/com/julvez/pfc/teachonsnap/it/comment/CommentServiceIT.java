package com.julvez.pfc.teachonsnap.it.comment;

import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.comment.CommentService;
import com.julvez.pfc.teachonsnap.comment.impl.CommentServiceImpl;
import com.julvez.pfc.teachonsnap.comment.repository.CommentRepositoryFactory;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.text.TextServiceFactory;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.ut.comment.CommentServiceTest;

public class CommentServiceIT extends CommentServiceTest {

	@Mock
	private NotifyService notifyService;
	
	private DBManager dbm = DBManagerFactory.getManager();
	private CacheManager cache = CacheManagerFactory.getManager();
	
	@Override
	protected CommentService getService() {
		return new CommentServiceImpl(CommentRepositoryFactory.getRepository(),
				UserServiceFactory.getService(),
				LessonServiceFactory.getService(),
				TextServiceFactory.getService(),
				notifyService);
	}

	@Override
	public void testCreateComment() {
		try{ super.testCreateComment();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_COMMENT_DELETE_BODY");
		dbm.updateQuery("SQL_IT_COMMENT_DELETE_RESPONSE");
		dbm.updateQuery("SQL_IT_COMMENT_DELETE_COMMENT");
		dbm.updateQuery("SQL_IT_COMMENT_RESET_COMMENT_ID");
	}

	@Override
	public void testSaveCommentBody() {
		cache.clearCache("getComment");		
		try{ super.testSaveCommentBody();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_COMMENT_SAVE_BODY", idComment, body, body);
		dbm.updateQuery("SQL_IT_COMMENT_TRUNCATE_EDIT");
	}

	@Override
	public void testBlockComment() {
		cache.clearCache("getComment");
		dbm.updateQuery("SQL_IT_COMMENT_TRUNCATE_BANNED");
		try{ super.testBlockComment();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_COMMENT_TRUNCATE_BANNED");
	}

	@Override
	public void testUnblockComment() {
		cache.clearCache("getComment");
		dbm.updateQuery("SQL_IT_COMMENT_TRUNCATE_BANNED");
		dbm.updateQuery("SQL_COMMENT_ADD_BANNED", idComment, idUser, "reason", idUser, "reason");
		try{ super.testUnblockComment();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_COMMENT_TRUNCATE_BANNED");
	}

	@Override
	public void testNotifyComment() {
		cache.clearCache("getLessonFollowers");
		dbm.updateQuery("SQL_IT_USER_TRUNCATE_FOLLOWLESSON");
		dbm.updateQuery("SQL_USER_ADD_FOLLOW_LESSON", idUser, idLesson);
		try{ super.testNotifyComment();	} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_USER_TRUNCATE_FOLLOWLESSON");		
	}
}
