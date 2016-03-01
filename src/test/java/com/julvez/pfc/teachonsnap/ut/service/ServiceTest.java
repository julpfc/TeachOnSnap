package com.julvez.pfc.teachonsnap.ut.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class ServiceTest<S> {

	protected final String NULL_STRING = null;
	protected final String BLANK_STRING = "     ";
	protected final String EMPTY_STRING = "";
	
	protected final int FIRST_RESULT = 0;
	protected final int SECOND_RESULT = 1;
	protected final int INVALID_RESULT = -1;
	
	protected S test;
	
	protected abstract S getService();
	
	@Before
	public void setUp() {
		test = getService();
	}
}
