package com.julvez.pfc.teachonsnap.upload.model;

import java.io.InputStream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.julvez.pfc.teachonsnap.media.model.MediaType;

/**
 * Domain entity describing a file metadata to be uploaded to the application. Contains a
 * file name, the file size, the file type, a MediaType describing the supported media type
 * withing the application and a input stream pionting to the file content.
 */
@JsonIgnoreProperties({"content","mediaType"})
public class FileMetadata {

	/** File's name */
	private String fileName;
	/** File's size in bytes */
	private String fileSize;
	/** File's content type */
	private String fileType;	
	
	/** Application's media type */
	private MediaType mediaType;
	 
	/** File content's input stream */
	private InputStream content;

	@Override
	public String toString() {
		return "FileMetadata [fileName=" + fileName + ", fileSize=" + fileSize
				+ ", fileType=" + fileType + "]";
	}

	/**
	 * @return file's name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets/modifies the file's name
	 * @param fileName new file's name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return file's size
	 */
	public String getFileSize() {
		return fileSize;
	}

	/**
	 * Sets/modifies the file's name
	 * @param fileSize new file's size
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return file's content-type
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * Sets/modifies the file's content-type
	 * @param fileType
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return file content's input stream
	 */
	public InputStream getContent() {
		return content;
	}

	/**
	 * Sets/modifies the file content's input stream
	 * @param content
	 */
	public void setContent(InputStream content) {
		this.content = content;
	}

	/**
	 * @return file's MediaType
	 */
	public MediaType getMediaType() {
		return mediaType;
	}

	/**
	 * Sets/modifies the file's MediaType
	 * @param mediaType application's MediaType
	 */
	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}	 
}
