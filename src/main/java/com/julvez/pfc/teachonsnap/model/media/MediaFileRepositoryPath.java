package com.julvez.pfc.teachonsnap.model.media;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MediaFileRepositoryPath {
	
	@Id
	@Column (name="idMediaRepository")
	private short id;
	private String URI;
	private String filePathSeparator;
		
	@Override
	public String toString() {
		return "MediaFileRepositoryPath [id=" + id + ", URI=" + URI
				+ ", filePathSeparator=" + filePathSeparator + "]";
	}
	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public String getURI() {
		return URI;
	}
	public void setURI(String uRI) {
		URI = uRI;
	}
	public String getFilePathSeparator() {
		return filePathSeparator;
	}
	public void setFilePathSeparator(String filePathSeparator) {
		this.filePathSeparator = filePathSeparator;
	}
	
}