package com.julvez.pfc.teachonsnap.manager.file.impl;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

import com.julvez.pfc.teachonsnap.manager.file.FileManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;

/**
 * Implementation of the FileManager, uses internal {@link LogManager} 
 * to log the errors.
 */

public class FileManagerImpl implements FileManager {

	
	/** Log manager providing logging capabilities */
	private LogManager logger;
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param logger Log manager providing logging capabilities
	 */
	public FileManagerImpl(LogManager logger) {
		if(logger == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.logger = logger;
	}
	
	@Override
	public boolean copyStream(InputStream inputStream, String path,	String fileName) {
		boolean copyOK = false;
		try {        
			File filePath = new File(path);
			File file = new File(path+fileName);
			//Create folders if needed
			Files.createDirectories(filePath.toPath());			
			
			logger.debug("Copying file: "+file.toPath());
			//copy
			Files.copy(inputStream, file.toPath());
			
			//close stream
			inputStream.close();
            copyOK = true;
		}
		catch (Throwable t) {
			logger.error(t, "Error copying file: " + path + fileName);
            copyOK = false;
            //delete if error
            delete(path, fileName);
		}
		
		return copyOK;
	}

	@Override
	public String getFileExtension(String fileName) {
		String extension = null;
		//Split by the dot and return file extension
		if(fileName != null){
			String[] matches = fileName.split("\\.");
			
			if(matches != null && matches.length > 0){
				extension = matches[matches.length - 1];
			}
		}
		return extension;
	}

	@Override
	public boolean delete(String path, String fileName) {
		boolean deleteOk = false;
		
		try {
			File filePath = new File(path);	
			File file = new File(path + fileName);
			//Delete file and path if exists
			Files.deleteIfExists(file.toPath());
			Files.deleteIfExists(filePath.toPath());
			deleteOk = true;
		} 
		catch (Throwable t) {
			logger.error(t, "Error deleting file: " + path + fileName);			
			deleteOk = false;
		}		
		
		return deleteOk;
	}
	
}
