package com.julvez.pfc.teachonsnap.media.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
* Entity. Describes a media file of a lesson. 
* It contains the file metadata (name, size, mimetype, url), the MediaType, 
* the repository id where is stored and the lesson media id it belongs to.
* @see MediaType 
*/
@Entity
public class MediaFile {
	
	/** Media file identifier and primary key for the entity */
	@Id
	@Column (name="idMediaFile")
	private int id;
	
	/** Lesson media id */
	private int idLessonMedia;
	
	/** Repository's id where the file is stored */
	private short idMediaRepository;
	
	/** File's name */
	private String filename;
	
	/** File's size */ 
	private int filesize;
	
	/** File's mime type */
	private String mimetype;
	
	/** File's MediaType */
	@Enumerated(EnumType.STRING)
	private MediaType mediaType;

	/** URL to the file at the repository */
	@Transient
	private String url;
	
	@Override
	public String toString() {
		return "MediaFile [id=" + id + ", idLessonMedia=" + idLessonMedia
				+ ", idMediaRepository=" + idMediaRepository + ", filename="
				+ filename + ", filesize=" + filesize +", mimetype=" 
				+ mimetype + ", mediaType="	+ mediaType + "]";
	}
	
	
	/**
	 * @return MediaFile's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return Lesson media's id
	 */
	public int getIdLessonMedia() {
		return idLessonMedia;
	}

	/**
	 * @return Repository's id where the file is stored
	 */
	public short getIdMediaRepository() {
		return idMediaRepository;
	}

	/**
	 * @return File's name
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @return File's mime type
	 */
	public String getMimetype() {
		return mimetype;
	}

	/**
	 * Sets the absolute URL to the file
	 * @param url pinting to the media file at the repository
	 */
	public void setURLs(String url){
		this.url = url;
	}
	
	/**
	 * @return Absolute URL to the file
	 */
	public String getURL(){
		return url + idLessonMedia + "/" + id +"/" + filename;
	}

	/**
	 * @return File's MediaType
	 */
	public MediaType getMediaType() {
		return mediaType;
	}

	/**
	 * @return File's size
	 */
	public int getFilesize() {
		return filesize;
	}

}
