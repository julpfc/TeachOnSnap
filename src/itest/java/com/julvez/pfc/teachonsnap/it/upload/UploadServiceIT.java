package com.julvez.pfc.teachonsnap.it.upload;

import com.julvez.pfc.teachonsnap.upload.UploadService;
import com.julvez.pfc.teachonsnap.upload.UploadServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.ut.upload.UploadServiceTest;

public class UploadServiceIT extends UploadServiceTest {

	@Override
	protected UploadService getService() {		
		return UploadServiceFactory.getService();
	}

	@Override
	public void testGetTemporaryFile() {
		User user = new User();
		user.setId(idUser);
		test.removeTemporaryFiles(user);
		super.testGetTemporaryFile();
	}
}
