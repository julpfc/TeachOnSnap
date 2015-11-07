package com.julvez.pfc.teachonsnap.media;

import com.julvez.pfc.teachonsnap.media.impl.MediaFileServiceImpl;

public class MediaFileServiceFactory {

	private static MediaFileService service;
	
	public static MediaFileService getService(){
		if(service==null){
			service = new MediaFileServiceImpl();
		}
		return service;
	}
}
