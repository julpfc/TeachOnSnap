package com.julvez.pfc.teachonsnap.model.lesson;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VideoFile {
	
	@Id
	@Column (name="idVideoFile")
	private int id;
	private int idLessonVideo;
	private short idVideoRepository;
	private String filename;
	private String mimetype;

		
	@Override
	public String toString() {
		return "VideoFile [id=" + id + ", idLessonVideo=" + idLessonVideo
				+ ", idVideoRepository=" + idVideoRepository + ", filename="
				+ filename + ", mimetype=" + mimetype + "]";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdLessonVideo() {
		return idLessonVideo;
	}
	public void setIdLessonVideo(int idLessonVideo) {
		this.idLessonVideo = idLessonVideo;
	}
	public short getIdVideoRepository() {
		return idVideoRepository;
	}
	public void setIdVideoRepository(short idVideoRepository) {
		this.idVideoRepository = idVideoRepository;
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
		return "/resources/video/"+idLessonVideo+"/"+id+"/"+filename;
	}
	


}
