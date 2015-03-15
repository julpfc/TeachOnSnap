package com.julvez.pfc.teachonsnap.manager.string.impl;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

public class StringManagerImplTest {
	
	private StringManager test = StringManagerFactory.getManager();

	@Test
	public void testIsEmpty() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testIsTrue() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGenerateURIname() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetKey() {
		int i = 1234;
		int j = 5678;
		String key = test.getKey(i,j);
		
		Assert.assertEquals("[1234][5678]", key);
	}

	@Test
	public void testGenerateMD5() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testIsNumeric() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testConvertToHTMLParagraph() {
		fail("Not yet implemented"); // TODO
	}

}
