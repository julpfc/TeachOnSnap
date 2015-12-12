package com.julvez.pfc.teachonsnap.tag.repository;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.tag.model.Tag;

public interface TagRepository {

	public Tag getTag(int idTag);
	
	public List<Integer> getLessonIDsFromTag(String tag, int firstResult);
	
	public List<Integer> getLessonTagIDs(int idLesson);
	
	public List<Object[]> getTagUseCloudTags();
	
	public List<Object[]> getAuthorCloudTags();
	
	public void addLessonTags(int idLesson, ArrayList<Integer> tagIDs);

	public int getTagID(String tag);

	public int createTag(String tag);

	public void removeLessonTags(int idLesson, ArrayList<Integer> removeTagIDs);

	public List<Integer> searchTag(String searchQuery, int firstResult);

	public List<Integer> getTags(int firstResult);

	public List<Integer> getTagSearchCloudTags();

}
