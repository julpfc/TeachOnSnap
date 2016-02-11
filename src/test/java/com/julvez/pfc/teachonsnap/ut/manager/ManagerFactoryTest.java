package com.julvez.pfc.teachonsnap.ut.manager;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class ManagerFactoryTest<M>{

	protected abstract M getTestManager();
	
	@Test
	public void testGetManager() {
		M manager;
		
		manager = getTestManager();
		
		Assert.assertNotNull(manager);
		Assert.assertSame(manager, getTestManager());		
	}
}
