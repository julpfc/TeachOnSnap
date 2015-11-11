package com.julvez.pfc.teachonsnap.manager.file.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import com.julvez.pfc.teachonsnap.manager.file.FileManager;

public class FileManagerImpl implements FileManager {

	@Override
	public boolean copyStream(InputStream inputStream, String path,	String fileName) {
		boolean copyOK = false;
		try {        
			File filePath = new File(path);
			File file = new File(path+fileName);
			Files.createDirectories(filePath.toPath());			
			
			System.out.println("FileManager.copy: "+file.toPath());
			Files.copy(inputStream, file.toPath());
			
			inputStream.close();
            copyOK = true;
		}
		catch (IOException e) {
            e.printStackTrace();
            copyOK = false;
            //TODO Si falla cargarse los directorios
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
	

}
