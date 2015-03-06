package com.julvez.pfc.teachonsnap.repository.tag;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.model.tag.Tag;

public interface TagRepository {

	public Tag getTag(int idTag);
	
	public List<Integer> getLessonIDsFromTag(String tag, int firstResult);
	
	public List<Integer> getLessonTagIDs(int idLesson);
	
	public List<Object[]> getCloudTags();
	
	public List<Object[]> getAuthorCloudTags();
	
	public void addLessonTags(int idLesson, ArrayList<Integer> tagIDs);

	public int getTagID(String tag);

	public int createTag(String tag);

}