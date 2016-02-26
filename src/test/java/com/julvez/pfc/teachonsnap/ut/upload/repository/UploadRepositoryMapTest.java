package com.julvez.pfc.teachonsnap.ut.upload.repository;

import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.upload.repository.UploadRepository;
import com.julvez.pfc.teachonsnap.upload.repository.UploadRepositoryMap;

public class UploadRepositoryMapTest extends UploadRepositoryTest {

	@Mock
	private LogManager logger;
	
	@Override
	protected UploadRepository getRepository() {	
		return new UploadRepositoryMap(logger);
	}

}
