package com.julvez.pfc.teachonsnap.media.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class MediaFile {
	
	@Id
	@Column (name="idMediaFile")
	private int id;
	private int idLessonMedia;
	private short idMediaRepository;
	private String filename;
	private String mimetype;
	@Enumerated(EnumType.STRING)
	private MediaType mediaType;

	@Override
	public String toString() {
		return "MediaFile [id=" + id + ", idLessonMedia=" + idLessonMedia
				+ ", idMediaRepository=" + idMediaRepository + ", filename="
				+ filename + ", mimetype=" + mimetype + ", mediaType="
				+ mediaType + "]";
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
	
	public String getURL(){
		return "/resources/media/"+idLessonMedia+"/"+id+"/"+filename;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}
	


}
