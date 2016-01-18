package com.julvez.pfc.teachonsnap.link.repository;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.link.model.Link;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;

/**
 * Repository implementation to access/modify data from a Database
 * <p>
 * {@link DBManager} is used to provide database access
 */
public class LinkRepositoryDB implements LinkRepository {

	/** Database manager providing access/modification capabilities */
	private DBManager dbm;
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param dbm Database manager providing access/modification capabilities
	 */
	public LinkRepositoryDB(DBManager dbm) {
		if(dbm == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.dbm = dbm;
	}

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
	public int getLinkID(String url) {
		int id = -1;
		Integer obj = dbm.getQueryResultUnique("SQL_LINK_GET_LINKID", Integer.class, url);
		if(obj!=null)
			id = obj;
		return id;
	}

	@Override
	public int createLink(String url, String desc) {
		return (int)dbm.insertQueryAndGetLastInserID("SQL_LINK_CREATE_LINK", url, desc);
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

	@Override
	public void removeLessonSources(int idLesson, ArrayList<Integer> removeLinkIDs) {
		for(int linkID:removeLinkIDs){
			dbm.updateQuery("SQL_LINK_DELETE_SOURCE", idLesson, linkID);
		}		
	}

	@Override
	public void removeLessonMoreInfos(int idLesson, ArrayList<Integer> removeLinkIDs) {
		for(int linkID:removeLinkIDs){
			dbm.updateQuery("SQL_LINK_DELETE_MOREINFO", idLesson, linkID);
		}		
	}

}
