package com.julvez.pfc.teachonsnap.manager.file;

import com.julvez.pfc.teachonsnap.manager.file.impl.FileManagerImpl;

public class FileManagerFactory {

private static FileManager manager;
	
	public static FileManager getManager(){
		if(manager==null){
			manager = new FileManagerImpl();
		}
		return manager;
	}
}
