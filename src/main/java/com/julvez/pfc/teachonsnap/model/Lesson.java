package com.julvez.pfc.teachonsnap.model;

public class Lesson {

	private int id;
	private String title;
	
	@Override
	public String toString() {		
		return (new StringBuffer("Lesson ").append(id)
				.append(" [Title: '").append(title).append("']"))
				.toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
