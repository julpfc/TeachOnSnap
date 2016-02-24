package com.julvez.pfc.teachonsnap.ut.repository;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class RepositoryFactoryTest<R> {

	protected abstract R getTestRepository();
		
	@Test
	public void testGetRepository() {
		R repo = getTestRepository();
		
		Assert.assertNotNull(repo);
		Assert.assertSame(repo, getTestRepository());		
	}	
}
