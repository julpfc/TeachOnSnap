package com.julvez.pfc.teachonsnap.ut.upload;

import com.julvez.pfc.teachonsnap.upload.UploadService;
import com.julvez.pfc.teachonsnap.upload.UploadServiceFactory;
import com.julvez.pfc.teachonsnap.ut.service.ServiceFactoryTest;

public class UploadServiceFactoryTest extends ServiceFactoryTest<UploadService> {

	@Override
	protected UploadService getTestService() {
		return UploadServiceFactory.getService();
	}
}
