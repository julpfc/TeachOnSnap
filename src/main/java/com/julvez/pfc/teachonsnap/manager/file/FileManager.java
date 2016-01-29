package com.julvez.pfc.teachonsnap.manager.file;

import java.io.InputStream;

/** File manager providing access to the repository's file system */
public interface FileManager {

	/**
	 * Copies a stream into a file at the repository
	 * @param inputStream Input stream to be copied (closed after copy)
	 * @param path Path to the destination file (creates folders if necessary)
	 * @param fileName Destination file name
	 * @return true if the copy was successful
	 */
	public boolean copyStream(InputStream inputStream, String path, String fileName);

	/**
	 * Extracts file extension from file name
	 * @param fileName File name
	 * @return file extension if present, null otherwise
	 */
	public String getFileExtension(String fileName);

	/**
	 * Deletes a file and path from the repository.
	 * @param path File path
	 * @param fileName File name to be deleted
	 * @return true if deletion is successful
	 */
	public boolean delete(String path, String fileName);	
}
