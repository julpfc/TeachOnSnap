package com.julvez.pfc.teachonsnap.ut.usergroup;

import com.julvez.pfc.teachonsnap.usergroup.UserGroupService;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupServiceFactory;
import com.julvez.pfc.teachonsnap.ut.service.ServiceFactoryTest;

public class UserGroupServiceFactoryTest extends ServiceFactoryTest<UserGroupService> {

	@Override
	protected UserGroupService getTestService() {
		return UserGroupServiceFactory.getService();
	}
}
