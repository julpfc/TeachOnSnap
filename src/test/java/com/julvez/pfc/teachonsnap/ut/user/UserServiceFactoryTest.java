package com.julvez.pfc.teachonsnap.ut.user;

import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.ut.service.ServiceFactoryTest;

public class UserServiceFactoryTest extends ServiceFactoryTest<UserService> {

	@Override
	protected UserService getTestService() {
		return UserServiceFactory.getService();
	}
}
