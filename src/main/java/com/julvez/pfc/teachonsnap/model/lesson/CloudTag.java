package com.julvez.pfc.teachonsnap.model.lesson;

import com.julvez.pfc.teachonsnap.model.user.User;

public class CloudTag {
	
	private String tag;
	private String URL;
	private short weight;

	public CloudTag(Tag tag,short weight){
		this.tag=tag.getTag();
		this.URL = tag.getURL();
		this.weight=weight;
	}
	
	public CloudTag(User author,short weight){
		this.tag=author.getFullName();
		this.URL = author.getURL();
		this.weight=weight;
	}
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public short getWeight() {
		return weight;
	}
	public void setWeight(short weight) {
		this.weight = weight;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	@Override
	public String toString() {
		return "CloudTag [tag=" + tag + ", URL=" + URL + ", weight=" + weight
				+ "]";
	}
	
	
		
	
	
}
