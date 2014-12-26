package com.julvez.pfc.teachonsnap.repository.upload;

import com.julvez.pfc.teachonsnap.repository.upload.map.UploadRepositoryMap;



public class UploadRepositoryFactory {

	private static UploadRepository repo;
	
	public static UploadRepository getRepository(){
		if(repo==null){
			repo = new UploadRepositoryMap();
		}
		return repo;
	}
}
