package com.julvez.pfc.teachonsnap.ut.tag;

import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.TagServiceFactory;
import com.julvez.pfc.teachonsnap.ut.service.ServiceFactoryTest;

public class TagServiceFactoryTest extends ServiceFactoryTest<TagService> {

	@Override
	protected TagService getTestService() {
		return TagServiceFactory.getService();
	}

}
