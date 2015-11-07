package com.julvez.pfc.teachonsnap.upload;

import com.julvez.pfc.teachonsnap.upload.impl.UploadServiceImpl;

public class UploadServiceFactory {

	private static UploadService service;
	
	public static UploadService getService(){
		if(service==null){
			service = new UploadServiceImpl();
		}
		return service;
	}
}
