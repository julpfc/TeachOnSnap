package com.julvez.pfc.teachonsnap.link.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.link.LinkService;
import com.julvez.pfc.teachonsnap.link.model.Link;
import com.julvez.pfc.teachonsnap.link.repository.LinkRepository;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;

/**
 * Implementation of the LinkService interface, uses an internal {@link LinkRepository} 
 * to access/modify the links related data.
 */
public class LinkServiceImpl implements LinkService {
	
	/** Repository than provides data access/modification */
	private LinkRepository linkRepository;
	
	/** Log manager providing logging capabilities */
	private LogManager logger;
	
	/** String manager providing string manipulation utilities */
	private StringManager stringManager;
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param linkRepository Repository than provides data access/modification
	 * @param logger Log manager providing logging capabilities
	 * @param stringManager String manager providing string manipulation utilities
	 */
	public LinkServiceImpl(LinkRepository linkRepository,
			LogManager logger, StringManager stringManager) {
		
		if(linkRepository == null || logger == null || stringManager == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.linkRepository = linkRepository;
		this.logger = logger;
		this.stringManager = stringManager;
	}

	@Override
	public List<Link> getMoreInfoLinks(int idLesson) {
		List<Link> links = Collections.emptyList();
		
		//Get list of ids from repo
		List<Integer> ids = linkRepository.getMoreInfoLinkIDs(idLesson);
		
		if(ids != null){
			links = new ArrayList<Link>();
			//Get Links from ids
			for(int id:ids){
				links.add(getLink(id));
			}
		}
		return links;	
	}

	@Override
	public List<Link> getSourceLinks(int idLesson) {
		List<Link> links = Collections.emptyList();
		
		//Gets list of ids from repo
		List<Integer> ids = linkRepository.getSourceLinkIDs(idLesson);
		
		if(ids != null){
			links = new ArrayList<Link>();
			//Get Links from ids
			for(int id:ids){
				links.add(getLink(id));
			}
		}
		return links;
	}

	@Override
	public void addLessonSources(Lesson lesson, List<String> sources) {
	
		if(lesson!=null && lesson.getId()>0 && sources!=null){
			ArrayList<Integer> linkIDs = new ArrayList<Integer>();

			//for each new url
			for(String link:sources){
				int linkID = 0;
				link = normalizeURL(link);
								
				//check if link already exists
				linkID = linkRepository.getLinkID(link);
				if(linkID>0){
					linkIDs.add(linkID);
				}
				else{
					//create link if not
					linkID = createLink(link);	
					if(linkID>0){
						linkIDs.add(linkID);
					}
				}
			}
			//Add links to the lesson
			if(linkIDs.size()>0){
				linkRepository.addLessonSources(lesson.getId(), linkIDs);				
			}
		}
	}	

	@Override
	public int createLink(String url) {
		int id = -1;
		
		if(!stringManager.isEmpty(url)){
			try{
				url = normalizeURL(url);
				//try to get the URL
				URL link = new URL(url);
				String host = link.getHost();
				//Create a new link
				id = linkRepository.createLink(url,host);
			}
			catch(Throwable t){
				logger.error(t,"Error, malformed URL: " + url);			
			}
		}
		return id;		 
	}

	@Override
	public void addLessonMoreInfo(Lesson lesson, List<String> moreInfos) {
		if(lesson!=null && lesson.getId()>0 && moreInfos!=null){
			ArrayList<Integer> linkIDs = new ArrayList<Integer>();

			//for each new url
			for(String link:moreInfos){
				link = normalizeURL(link);
				
				//check if link already exists
				int linkID = 0;
				linkID = linkRepository.getLinkID(link);
				if(linkID>0){
					linkIDs.add(linkID);
				}
				else{
					//create link if not
					linkID = createLink(link);	
					if(linkID>0){
						linkIDs.add(linkID);
					}
				}
			}
			//Add links to the lesson
			if(linkIDs.size()>0){
				linkRepository.addLessonMoreInfos(lesson.getId(), linkIDs);				
			}
		}
	}

	@Override
	public Link getLink(int idLink) {
		Link link = null;
		
		if(idLink>0){
			//get link from id
			link = linkRepository.getLink(idLink);
			if(link != null){
				//Generata MD5 hash from URL
				String md5 = stringManager.generateMD5(link.getURL());
				link.setMD5(md5);
			}
		}
		return link;
	}

	@Override
	public boolean saveLessonMoreInfo(Lesson lesson, List<Link> oldLinks, List<String> newLinks) {
		boolean modified = false;
		
		if(lesson!=null && lesson.getId()>0){
		
			newLinks = normalizeURLs(newLinks);
			
			//Get links to remove
			ArrayList<Integer> removeLinkIDs = getLinkIDsToRemove(oldLinks, newLinks);
		
			//Remove links
			if(removeLinkIDs.size()>0){
				removeLessonMoreInfos(lesson, removeLinkIDs);
				modified = true;
			}
			
			if(newLinks != null && newLinks.size() > 0){
				//Ignore already added links
				for(Link link:oldLinks){
					if(newLinks.contains(link.getURL())){
						newLinks.remove(link.getURL());
					}
				}
				//Add new links
				if(newLinks.size() > 0){
					addLessonMoreInfo(lesson, newLinks);
					modified = true;
				}				
			}
		}
		
		return modified;
	}

	@Override
	public boolean saveLessonSources(Lesson lesson, List<Link> oldLinks, List<String> newLinks) {
		boolean modified = false;
		
		if(lesson!=null && lesson.getId()>0){
			
			newLinks = normalizeURLs(newLinks);
		
			//Get links to remove
			ArrayList<Integer> removeLinkIDs = getLinkIDsToRemove(oldLinks, newLinks);
		
			//Remove links
			if(removeLinkIDs.size()>0){
				removeLessonSources(lesson, removeLinkIDs);
				modified = true;
			}
			
			if(newLinks != null && newLinks.size() > 0){
				//Ignore already added links
				for(Link link:oldLinks){
					if(newLinks.contains(link.getURL())){
						newLinks.remove(link.getURL());
					}
				}
				//Add new links
				if(newLinks.size() > 0){
					addLessonSources(lesson, newLinks);
					modified = true;
				}				
			}
		}
		
		return modified;
	}
	
	@Override
	public void removeLessonSources(Lesson lesson, ArrayList<Integer> removeLinkIDs) {
		if(lesson != null && lesson.getId()>0 && removeLinkIDs != null && removeLinkIDs.size() > 0){
			linkRepository.removeLessonSources(lesson.getId(), removeLinkIDs);
		}		
	}

	@Override
	public void removeLessonMoreInfos(Lesson lesson, ArrayList<Integer> removeLinkIDs) {
		if(lesson != null && lesson.getId()>0 && removeLinkIDs != null && removeLinkIDs.size() > 0){
			linkRepository.removeLessonMoreInfos(lesson.getId(), removeLinkIDs);
		}		
	}
	
	/**
	 * Gets ids to be removed from the lesson.
	 * @param oldLinks current lesson links
	 * @param newLinks to be added
	 * @return link ids from oldlinks not presents on newLinks
	 */
	private ArrayList<Integer> getLinkIDsToRemove(List<Link> oldLinks, List<String> newLinks){
		ArrayList<Integer> removeLinkIDs = new ArrayList<Integer>();

		for(Link link:oldLinks){
			if(!newLinks.contains(link.getURL())){
				removeLinkIDs.add(link.getId());
			}
		}		
		return removeLinkIDs;
	}
	
	/**
	 * Normalizes a list of URLs
	 * @param urls to be normalized
	 * @return list of normalized URLs
	 */
	private List<String> normalizeURLs(List<String> urls){
		List<String> correctedLinks = new ArrayList<String>();
		if(urls != null){			
			//for each URL: normalize and add to the array
			for(String newLink:urls){
				newLink = normalizeURL(newLink);
				correctedLinks.add(newLink);
			}			
		}
		return correctedLinks;
	}

	/**
	 * Completes the URL protocol if missing and converts to lower case
	 * @param url to normalize
	 * @return Normalized URL
	 */
	private String normalizeURL(String url) {
		if(!url.startsWith("http")){
			url = "http://" + url;
		}
		url.toLowerCase();
		return url;
	}

}
