package com.julvez.pfc.teachonsnap.manager.file.impl;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

import com.julvez.pfc.teachonsnap.manager.file.FileManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;

public class FileManagerImpl implements FileManager {

	private LogManager logger = LogManagerFactory.getManager();
	
	@Override
	public boolean copyStream(InputStream inputStream, String path,	String fileName) {
		boolean copyOK = false;
		try {        
			File filePath = new File(path);
			File file = new File(path+fileName);
			Files.createDirectories(filePath.toPath());			
			
			logger.debug("Copiando fichero: "+file.toPath());
			Files.copy(inputStream, file.toPath());
			
			inputStream.close();
            copyOK = true;
		}
		catch (Throwable t) {
			logger.error(t, "Error copiando fichero: " + path + fileName);
            copyOK = false;
            delete(path, fileName);
		}
		
		return copyOK;
	}

	@Override
	public String getFileExtension(String fileName) {
		String extension = null;
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
			Files.deleteIfExists(file.toPath());
			Files.deleteIfExists(filePath.toPath());
			deleteOk = true;
		} 
		catch (Throwable t) {
			logger.error(t, "Error eliminando fichero: " + path + fileName);			
			deleteOk = false;
		}		
		
		return deleteOk;
	}
	

}
