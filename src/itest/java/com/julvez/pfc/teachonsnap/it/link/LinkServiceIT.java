package com.julvez.pfc.teachonsnap.it.link;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.link.LinkService;
import com.julvez.pfc.teachonsnap.link.LinkServiceFactory;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.ut.link.LinkServiceTest;

public class LinkServiceIT extends LinkServiceTest {

	private DBManager dbm = DBManagerFactory.getManager();
	private CacheManager cache = CacheManagerFactory.getManager();
	
	@Override
	protected LinkService getService() {
		return LinkServiceFactory.getService();
	}
	
	@Test
	public void testAddLessonMoreInfo() {
		cache.clearCache("getMoreInfoLinkIDs");
		dbm.updateQuery("SQL_IT_LINK_TRUNCATE_LESSONMOREINFO");
		try{ super.testAddLessonMoreInfo();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_LINK_TRUNCATE_LESSONMOREINFO");
	}


	@Override
	public void testGetMoreInfoLinks() {
		cache.clearCache("getMoreInfoLinkIDs");
		dbm.updateQuery("SQL_IT_LINK_TRUNCATE_LESSONMOREINFO");
		dbm.updateQuery("SQL_LINK_SAVE_MOREINFO", idLesson, idLink);
		try{ super.testGetMoreInfoLinks();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_LINK_TRUNCATE_LESSONMOREINFO");
	}

	@Override
	public void testGetSourceLinks() {
		cache.clearCache("getSourceLinkIDs");
		dbm.updateQuery("SQL_IT_LINK_TRUNCATE_LESSONSOURCE");
		dbm.updateQuery("SQL_LINK_SAVE_SOURCE", idLesson, idLink);
		try{ super.testGetSourceLinks();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_LINK_TRUNCATE_LESSONSOURCE");
	}

	@Override
	public void testAddLessonSources() {
		cache.clearCache("getSourceLinkIDs");
		dbm.updateQuery("SQL_IT_LINK_TRUNCATE_LESSONSOURCE");
		try{ super.testAddLessonSources();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_LINK_TRUNCATE_LESSONSOURCE");
	}

	@Override
	public void testCreateLink() {
		try{ super.testCreateLink();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_LINK_DELETE_LINK");
		dbm.updateQuery("SQL_IT_LINK_RESET_LINK_ID");		
	}

	@Override
	public void testSaveLessonMoreInfo() {
		cache.clearCache("getMoreInfoLinkIDs");
		dbm.updateQuery("SQL_IT_LINK_TRUNCATE_LESSONMOREINFO");
		try{ super.testSaveLessonMoreInfo();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_LINK_TRUNCATE_LESSONMOREINFO");
	}

	@Override
	public void testSaveLessonSources() {
		cache.clearCache("getSourceLinkIDs");
		dbm.updateQuery("SQL_IT_LINK_TRUNCATE_LESSONSOURCE");
		try{ super.testSaveLessonSources();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_LINK_TRUNCATE_LESSONSOURCE");
	}

	@Override
	public void testRemoveLessonSources() {
		cache.clearCache("getSourceLinkIDs");
		dbm.updateQuery("SQL_IT_LINK_TRUNCATE_LESSONSOURCE");
		dbm.updateQuery("SQL_LINK_SAVE_SOURCE", idLesson, idLink);
		try{ super.testRemoveLessonSources();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_LINK_TRUNCATE_LESSONSOURCE");
	}

	@Override
	public void testRemoveLessonMoreInfos() {
		cache.clearCache("getMoreInfoLinkIDs");
		dbm.updateQuery("SQL_IT_LINK_TRUNCATE_LESSONMOREINFO");
		dbm.updateQuery("SQL_LINK_SAVE_MOREINFO", idLesson, idLink);
		try{ super.testRemoveLessonMoreInfos();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_LINK_TRUNCATE_LESSONMOREINFO");
	}
}
