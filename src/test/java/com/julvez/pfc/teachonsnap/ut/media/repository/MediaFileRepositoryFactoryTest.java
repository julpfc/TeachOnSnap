package com.julvez.pfc.teachonsnap.ut.media.repository;

import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepository;
import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepositoryFactory;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryFactoryTest;

public class MediaFileRepositoryFactoryTest extends RepositoryFactoryTest<MediaFileRepository> {

	@Override
	protected MediaFileRepository getTestRepository() {
		return MediaFileRepositoryFactory.getRepository();
	}
}
