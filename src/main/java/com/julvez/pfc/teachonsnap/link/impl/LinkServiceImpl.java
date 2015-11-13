package com.julvez.pfc.teachonsnap.link.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.link.LinkService;
import com.julvez.pfc.teachonsnap.link.model.Link;
import com.julvez.pfc.teachonsnap.link.repository.LinkRepository;
import com.julvez.pfc.teachonsnap.link.repository.LinkRepositoryFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

public class LinkServiceImpl implements LinkService {

	LinkRepository linkRepository = LinkRepositoryFactory.getRepository();
	LessonService lessonService = LessonServiceFactory.getService();
	private StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	public List<Link> getMoreInfoLinks(int idLesson) {
		List<Link> links = new ArrayList<Link>();
		
		List<Integer> ids = linkRepository.getMoreInfoLinkIDs(idLesson);
		
		for(int id:ids){
			links.add(getLink(id));
			}
		
		return links;	
	}

	@Override
	public List<Link> getSourceLinks(int idLesson) {
		List<Link> links = new ArrayList<Link>();
		
		List<Integer> ids = linkRepository.getSourceLinkIDs(idLesson);
		
		for(int id:ids){
			links.add(getLink(id));
			}
		
		return links;
	}

	@Override
	public void addLessonSources(Lesson lesson, List<String> sources) {
		ArrayList<Integer> linkIDs = new ArrayList<Integer>();
	
		if(lesson!=null && lesson.getId()>0 && sources!=null){

			for(String link:sources){
				int linkID = 0;
				if(!link.startsWith("http")){
					link = "http://" + link;
				}
				linkID = linkRepository.getLinkID(link);
				if(linkID>0){
					linkIDs.add(linkID);
				}
				else{
					
					linkID = createLink(link);	
					if(linkID>0){
						linkIDs.add(linkID);
					}
				}
			}
			if(linkIDs.size()>0){
				linkRepository.addLessonSources(lesson.getId(), linkIDs);
				lesson = lessonService.getLesson(lesson.getId());
			}
		}
	}

	@Override
	public int createLink(String url) {
		int id = -1;
		if(!url.startsWith("http")){
			url = "http://" + url;
		}
		try{
			URL link = new URL(url);
			String host = link.getHost();
			id = linkRepository.createLink(url,host);
		}
		catch(Throwable t){
			t.printStackTrace();
		}
		return id; 
		 
	}

	@Override
	public void addLessonMoreInfo(Lesson lesson, List<String> moreInfos) {
		ArrayList<Integer> linkIDs = new ArrayList<Integer>();
		
		if(lesson!=null && lesson.getId()>0 && moreInfos!=null){

			for(String link:moreInfos){
				if(!link.startsWith("http")){
					link = "http://" + link;
				}
				int linkID = 0;
				linkID = linkRepository.getLinkID(link);
				if(linkID>0){
					linkIDs.add(linkID);
				}
				else{
					
					linkID = createLink(link);	
					if(linkID>0){
						linkIDs.add(linkID);
					}
				}
			}
			if(linkIDs.size()>0){
				linkRepository.addLessonMoreInfos(lesson.getId(), linkIDs);
				lesson = lessonService.getLesson(lesson.getId());
			}
		}
	}


	@Override
	public Link getLink(int idLink) {
		Link link = null;
		
		if(idLink>0){
			link = linkRepository.getLink(idLink);
			String md5 = stringManager.generateMD5(link.getURL());
			link.setMD5(md5);
		}
		return link;
	}

	@Override
	public boolean saveLessonMoreInfo(Lesson lesson, List<Link> oldLinks, List<String> newLinks) {
		boolean modified = false;
		
		if(lesson!=null && lesson.getId()>0){
		
			ArrayList<Integer> removeLinkIDs = getLinkIDsToRemove(oldLinks, newLinks);
		
			if(removeLinkIDs.size()>0){
				removeLessonMoreInfos(lesson, removeLinkIDs);
				modified = true;
			}
			
			if(newLinks != null && newLinks.size() > 0){
				addLessonMoreInfo(lesson, newLinks);
				modified = true;
			}
		}
		
		return modified;
	}

	@Override
	public boolean saveLessonSources(Lesson lesson, List<Link> oldLinks, List<String> newLinks) {
		boolean modified = false;
		
		if(lesson!=null && lesson.getId()>0){
		
			ArrayList<Integer> removeLinkIDs = getLinkIDsToRemove(oldLinks, newLinks);
		
			if(removeLinkIDs.size()>0){
				removeLessonSources(lesson, removeLinkIDs);
				modified = true;
			}
			
			if(newLinks != null && newLinks.size() > 0){
				addLessonSources(lesson, newLinks);
				modified = true;
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
	
	private ArrayList<Integer> getLinkIDsToRemove(List<Link> oldLinks, List<String> newLinks){
		ArrayList<Integer> removeLinkIDs = new ArrayList<Integer>();

		if(newLinks == null){
			newLinks = new ArrayList<String>();
		}

		List<String> correctedLinks = new ArrayList<String>();
		for(String newLink:newLinks){
			if(!newLink.startsWith("http")){
				newLink = "http://" + newLink;
			}
			correctedLinks.add(newLink);
		}
		newLinks = correctedLinks;
		
		for(Link link:oldLinks){
			if(!newLinks.contains(link.getURL())){
				removeLinkIDs.add(link.getId());
			}
		}
		
		return removeLinkIDs;
	}


}
