package com.julvez.pfc.teachonsnap.service.link.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.link.Link;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.service.link.LinkService;
import com.julvez.pfc.teachonsnap.service.link.repository.LinkRepository;
import com.julvez.pfc.teachonsnap.service.link.repository.LinkRepositoryFactory;

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
	public Lesson addLessonSources(Lesson lesson, List<String> sources) {
		ArrayList<Integer> linkIDs = new ArrayList<Integer>();
		Lesson ret = null;
		
		if(lesson!=null && lesson.getId()>0 && sources!=null){

			for(String link:sources){
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
				linkRepository.addLessonSources(lesson.getId(), linkIDs);
				lesson = lessonService.getLesson(lesson.getId());
			}
			ret = lesson; 
		}
		return ret;
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
	public Lesson addLessonMoreInfo(Lesson lesson, List<String> moreInfos) {
		ArrayList<Integer> linkIDs = new ArrayList<Integer>();
		Lesson ret = null;
		
		if(lesson!=null && lesson.getId()>0 && moreInfos!=null){

			for(String link:moreInfos){
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
			ret = lesson; 
		}
		return ret;
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
	

}
