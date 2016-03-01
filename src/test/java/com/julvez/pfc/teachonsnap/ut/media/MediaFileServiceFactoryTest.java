package com.julvez.pfc.teachonsnap.ut.media;

import com.julvez.pfc.teachonsnap.media.MediaFileService;
import com.julvez.pfc.teachonsnap.media.MediaFileServiceFactory;
import com.julvez.pfc.teachonsnap.ut.service.ServiceFactoryTest;

public class MediaFileServiceFactoryTest extends ServiceFactoryTest<MediaFileService> {

	@Override
	protected MediaFileService getTestService() {
		return MediaFileServiceFactory.getService();
	}

}
