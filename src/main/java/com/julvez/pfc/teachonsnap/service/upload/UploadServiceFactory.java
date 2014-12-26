package com.julvez.pfc.teachonsnap.service.upload;

import com.julvez.pfc.teachonsnap.service.upload.impl.UploadServiceImpl;

public class UploadServiceFactory {

	private static UploadService service;
	
	public static UploadService getService(){
		if(service==null){
			service = new UploadServiceImpl();
		}
		return service;
	}
}
