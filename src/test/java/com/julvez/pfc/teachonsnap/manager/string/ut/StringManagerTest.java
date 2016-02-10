package com.julvez.pfc.teachonsnap.manager.string.ut;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.julvez.pfc.teachonsnap.manager.string.StringManager;

public abstract class StringManagerTest {

	private StringManager test;
	
	protected abstract StringManager getManager();
	
	@Before
	public void setUp() {
		test = getManager();
	}

	@Test
	public void testIsEmpty() {
		String notEmptyString = "notEmpty";
		String nullString = null;
		String blankString = "     ";
		String emptyString = "";
		
		Assert.assertFalse(test.isEmpty(notEmptyString));
		Assert.assertTrue(test.isEmpty(nullString));
		Assert.assertTrue(test.isEmpty(blankString));
		Assert.assertTrue(test.isEmpty(emptyString));		
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

	@Test
	public void testDecodeURL() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testEncodeURL() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testEscapeHTML() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testUnescapeHTML() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSplit() {
		fail("Not yet implemented"); // TODO
	}

}
