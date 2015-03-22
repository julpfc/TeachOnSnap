package com.julvez.pfc.teachonsnap.repository.link.db;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.model.link.Link;
import com.julvez.pfc.teachonsnap.repository.link.LinkRepository;

public class LinkRepositoryDB implements LinkRepository {

	private DBManager dbm = DBManagerFactory.getDBManager();
	
	@Override
	public List<Integer> getMoreInfoLinkIDs(int idLesson) {
		return dbm.getQueryResultList("SQL_LINK_GET_MOREINFOLINKIDS", Integer.class, idLesson);
	}
	
	@Override
	public List<Integer> getSourceLinkIDs(int idLesson) {
		return dbm.getQueryResultList("SQL_LINK_GET_SOURCELINKIDS", Integer.class, idLesson);
	}

	@Override
	public Link getLink(int idLink) {
		return dbm.getQueryResultUnique("SQL_LINK_GET_LINK", Link.class, idLink);
	}
	
	@Override
	public int getLinkID(String link) {
		int id = -1;
		Integer obj = dbm.getQueryResultUnique("SQL_LINK_GET_LINKID", Integer.class, link);
		if(obj!=null)
			id = obj;
		return id;
	}

	@Override
	public int createLink(String url, String desc) {
		return (int)dbm.updateQuery("SQL_LINK_CREATE_LINK", url, desc);
	}

	@Override
	public void addLessonSources(int idLesson, ArrayList<Integer> sourceLinkIDs) {
		for(int linkID:sourceLinkIDs){
			dbm.updateQuery("SQL_LINK_SAVE_SOURCE", idLesson,linkID);
		}			
	}

	@Override
	public void addLessonMoreInfos(int idLesson, ArrayList<Integer> moreInfoLinkIDs) {
		for(int linkID:moreInfoLinkIDs){
			dbm.updateQuery("SQL_LINK_SAVE_MOREINFO", idLesson,linkID);
		}			
		
	}

}
