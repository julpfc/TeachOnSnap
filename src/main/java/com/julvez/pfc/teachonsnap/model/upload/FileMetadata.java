package com.julvez.pfc.teachonsnap.model.upload;

import java.io.InputStream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.julvez.pfc.teachonsnap.model.media.MediaType;

@JsonIgnoreProperties({"content","mediaType"})
public class FileMetadata {

	private String fileName;
	private String fileSize;
	private String fileType;	
	
	private MediaType mediaType;
	 
	private InputStream content;

	@Override
	public String toString() {
		return "FileMetadata [fileName=" + fileName + ", fileSize=" + fileSize
				+ ", fileType=" + fileType + "]";
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public InputStream getContent() {
		return content;
	}

	public void setContent(InputStream content) {
		this.content = content;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	} 
	 
}
