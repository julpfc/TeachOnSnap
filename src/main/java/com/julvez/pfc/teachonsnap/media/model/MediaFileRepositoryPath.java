package com.julvez.pfc.teachonsnap.media.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
* Entity. Describes a path to a media repository. 
* It contains the URI to the repository and specifies the file path separator. 
*/
@Entity
public class MediaFileRepositoryPath {
	
	/** Media file repository path identifier and primary key for the entity */
	@Id
	@Column (name="idMediaRepository")
	private short id;
	
	/** Repository's URI */
	private String URI;
	
	/** Repository's file path separator (File System dependant) */
	private String filePathSeparator;
		
	@Override
	public String toString() {
		return "MediaFileRepositoryPath [id=" + id + ", URI=" + URI
				+ ", filePathSeparator=" + filePathSeparator + "]";
	}

	/**
	 * @return Repository's id
	 */
	public short getId() {
		return id;
	}
	
	/**
	 * @return Repository's URI
	 */
	public String getURI() {
		return URI;
	}
	
	/**
	 * @return Repository's file path separator
	 */
	public String getFilePathSeparator() {
		return filePathSeparator;
	}
	
}