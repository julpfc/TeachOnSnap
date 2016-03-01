package com.julvez.pfc.teachonsnap.ut.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class ServiceFactoryTest<S> {

	protected abstract S getTestService();
		
	@Test
	public void testGetService() {
		S service = getTestService();
		
		Assert.assertNotNull(service);
		Assert.assertSame(service, getTestService());		
	}	
}
