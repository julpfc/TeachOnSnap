package com.julvez.pfc.teachonsnap.ut.manager;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class ManagerTest<M>{
	
	protected final String NULL_STRING = null;
	protected final String BLANK_STRING = "     ";
	protected final String EMPTY_STRING = "";

	protected M test;
	
	protected abstract M getManager();
	
	@Before
	public void setUp() {
		test = getManager();
	}
}
