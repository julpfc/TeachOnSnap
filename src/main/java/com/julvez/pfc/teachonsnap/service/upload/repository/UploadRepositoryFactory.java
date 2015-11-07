package com.julvez.pfc.teachonsnap.service.upload.repository;

import com.julvez.pfc.teachonsnap.service.upload.repository.map.UploadRepositoryMap;



public class UploadRepositoryFactory {

	private static UploadRepository repo;
	
	public static UploadRepository getRepository(){
		if(repo==null){
			repo = new UploadRepositoryMap();
		}
		return repo;
	}
}
