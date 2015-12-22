package com.julvez.pfc.teachonsnap.media.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class MediaFile {
	
	@Id
	@Column (name="idMediaFile")
	private int id;
	private int idLessonMedia;
	private short idMediaRepository;
	private String filename;
	private int filesize;
	private String mimetype;
	@Enumerated(EnumType.STRING)
	private MediaType mediaType;

	@Transient
	private String url;
	
	@Override
	public String toString() {
		return "MediaFile [id=" + id + ", idLessonMedia=" + idLessonMedia
				+ ", idMediaRepository=" + idMediaRepository + ", filename="
				+ filename + ", filesize=" + filesize +", mimetype=" 
				+ mimetype + ", mediaType="	+ mediaType + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdLessonMedia() {
		return idLessonMedia;
	}
	public void setIdLessonMedia(int idLessonMedia) {
		this.idLessonMedia = idLessonMedia;
	}
	public short getIdMediaRepository() {
		return idMediaRepository;
	}
	public void setIdMediaRepository(short idMediaRepository) {
		this.idMediaRepository = idMediaRepository;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	
	public void setURLs(String url){
		this.url = url;
	}
	
	public String getURL(){
		return url + idLessonMedia + "/" + id +"/" + filename;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}
	public int getFilesize() {
		return filesize;
	}
	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}
	


}
