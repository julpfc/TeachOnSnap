package com.julvez.pfc.teachonsnap.ut.upload.repository;

import com.julvez.pfc.teachonsnap.upload.repository.UploadRepository;
import com.julvez.pfc.teachonsnap.upload.repository.UploadRepositoryFactory;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryFactoryTest;

public class UploadRepositoryFactoryTest extends RepositoryFactoryTest<UploadRepository> {

	@Override
	protected UploadRepository getTestRepository() {
		return UploadRepositoryFactory.getRepository();
	}	
}
