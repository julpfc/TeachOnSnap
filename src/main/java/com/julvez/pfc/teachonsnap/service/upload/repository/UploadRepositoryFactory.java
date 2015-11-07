package com.julvez.pfc.teachonsnap.service.upload.repository;




public class UploadRepositoryFactory {

	private static UploadRepository repo;
	
	public static UploadRepository getRepository(){
		if(repo==null){
			repo = new UploadRepositoryMap();
		}
		return repo;
	}
}
