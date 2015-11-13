package com.julvez.pfc.teachonsnap.link.repository;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.link.model.Link;

public interface LinkRepository {

	public List<Integer> getMoreInfoLinkIDs(int idLesson);

	public List<Integer> getSourceLinkIDs(int idLesson);

	public Link getLink(int idLink);

	public int getLinkID(String link);

	public int createLink(String url, String desc);

	public void addLessonSources(int idLesson, ArrayList<Integer> sourceLinkIDs);

	public void addLessonMoreInfos(int idLesson, ArrayList<Integer> moreInfoLinkIDs);

	public void removeLessonSources(int idLesson, ArrayList<Integer> removeLinkIDs);

	public void removeLessonMoreInfos(int idLesson, ArrayList<Integer> removeLinkIDs);

}
