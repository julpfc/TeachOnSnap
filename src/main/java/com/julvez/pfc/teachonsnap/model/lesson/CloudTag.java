package com.julvez.pfc.teachonsnap.model.lesson;

public class CloudTag {
	
	private Tag tag;
	private short weight;

	public CloudTag(Tag tag,short weight){
		this.tag=tag;
		this.weight=weight;
	}
	
	public Tag getTag() {
		return tag;
	}
	public void setTag(Tag tag) {
		this.tag = tag;
	}
	public short getWeight() {
		return weight;
	}
	public void setWeight(short weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return "CloudTag [tag=" + tag + ", weight=" + weight + "]";
	}
	
		
	
	
}
