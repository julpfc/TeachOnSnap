package com.julvez.pfc.teachonsnap.ut.manager.string;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerTest;

public abstract class StringManagerTest extends ManagerTest<StringManager>{

	@Test
	public void testIsEmpty() {
		String notEmptyString = "notEmpty";
		
		Assert.assertFalse(test.isEmpty(notEmptyString));
		Assert.assertTrue(test.isEmpty(NULL_STRING));
		Assert.assertTrue(test.isEmpty(BLANK_STRING));
		Assert.assertTrue(test.isEmpty(EMPTY_STRING));		
	}

	@Test
	public void testIsTrue() {
		String stringTrUe = "TrUe";
		String stringTrue = "true";
		String stringOne = "1";
		String stringNegative = "-1";
		String stringInt = "548548";
		
		
		Assert.assertTrue(test.isTrue(stringTrUe));
		Assert.assertTrue(test.isTrue(stringTrue));
		Assert.assertTrue(test.isTrue(stringOne));
		Assert.assertFalse(test.isTrue(stringNegative));
		Assert.assertTrue(test.isTrue(stringInt));
		Assert.assertFalse(test.isTrue(NULL_STRING));
		Assert.assertFalse(test.isTrue(BLANK_STRING));
		Assert.assertFalse(test.isTrue(EMPTY_STRING));
	}

	@Test
	public void testGenerateURIname() {
		String onewordString = "word";
		String twoWordString = "word1 word2";
		String specialString = "Título de lección con Ñ";
		
		Assert.assertEquals(onewordString, test.generateURIname(onewordString));
		Assert.assertEquals("word1-word2", test.generateURIname(twoWordString));
		Assert.assertEquals("titulo-de-leccion-con-n", test.generateURIname(specialString));
							 
		Assert.assertNull(test.generateURIname(NULL_STRING));		
		Assert.assertNull(test.generateURIname(BLANK_STRING));
		Assert.assertNull(test.generateURIname(EMPTY_STRING));		
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
		String input = "Text? to gEnérate MD5 with!";
		
		Assert.assertEquals("7749353454a21d013d647906b9692d80", 
				test.generateMD5(input));
		
		Assert.assertNull(test.generateMD5(NULL_STRING));		
		Assert.assertNull(test.generateMD5(BLANK_STRING));
		Assert.assertNull(test.generateMD5(EMPTY_STRING));
	}

	@Test
	public void testIsNumeric() {
		String stringText = "text";
		String stringNumAndText = "1text";
		String stringOne = "1";
		String stringNegative = "-1";
		String stringInt = "548548";		
		
		Assert.assertFalse(test.isNumeric(stringText));
		Assert.assertFalse(test.isNumeric(stringNumAndText));
		Assert.assertTrue(test.isNumeric(stringOne));
		Assert.assertFalse(test.isNumeric(stringNegative));
		Assert.assertTrue(test.isNumeric(stringInt));
		Assert.assertFalse(test.isNumeric(NULL_STRING));
		Assert.assertFalse(test.isNumeric(BLANK_STRING));
		Assert.assertFalse(test.isNumeric(EMPTY_STRING));
	}

	@Test
	public void testConvertToHTMLParagraph() {
		String singleLine = "One line";
		String twoLinesR = "One line\rAnother line";
		String twoLinesN = "One line\nAnother line";
		String twoLinesRN = "One line\r\nAnother line";
		
		Assert.assertEquals("<p>One line</p>", 
							test.convertToHTMLParagraph(singleLine));
		Assert.assertEquals("<p>One line</p><p>Another line</p>",
							test.convertToHTMLParagraph(twoLinesR));
		Assert.assertEquals("<p>One line</p><p>Another line</p>",
							test.convertToHTMLParagraph(twoLinesN));
		Assert.assertEquals("<p>One line</p><p>Another line</p>",
							test.convertToHTMLParagraph(twoLinesRN));
		
		Assert.assertNull(test.convertToHTMLParagraph(NULL_STRING));		
		Assert.assertNull(test.convertToHTMLParagraph(BLANK_STRING));
		Assert.assertNull(test.convertToHTMLParagraph(EMPTY_STRING));
	}

	@Test
	public void testDecodeURL() {
		String encoded = "URL%20encoded";
		String decoded = "URL encoded";
		
		Assert.assertEquals(decoded, test.decodeURL(encoded));
		Assert.assertEquals(decoded, test.decodeURL(decoded));
		
		Assert.assertNull(test.decodeURL(NULL_STRING));		
		Assert.assertEquals(BLANK_STRING, test.decodeURL(BLANK_STRING));
		Assert.assertEquals(EMPTY_STRING, test.decodeURL(EMPTY_STRING));
	}

	@Test
	public void testEncodeURL() {
		String encoded = "URL+encoded";
		String decoded = "URL encoded";
		
		Assert.assertEquals("URL%2Bencoded", test.encodeURL(encoded));
		Assert.assertEquals(encoded, test.encodeURL(decoded));
		
		Assert.assertNull(test.encodeURL(NULL_STRING));		
		Assert.assertEquals("+++++", test.encodeURL(BLANK_STRING));
		Assert.assertEquals(EMPTY_STRING, test.encodeURL(EMPTY_STRING));
	}

	@Test
	public void testEscapeHTML() {
		String unescaped = "Lección ñoña!";
		String escaped = "Lecci&oacute;n &ntilde;o&ntilde;a!";
		
		Assert.assertEquals(escaped, test.escapeHTML(unescaped));
		Assert.assertEquals("Lecci&amp;oacute;n &amp;ntilde;o&amp;ntilde;a!", 
						test.escapeHTML(escaped));
		
		Assert.assertNull(test.escapeHTML(NULL_STRING));		
		Assert.assertEquals(BLANK_STRING, test.escapeHTML(BLANK_STRING));
		Assert.assertEquals(EMPTY_STRING, test.escapeHTML(EMPTY_STRING));
	}

	@Test
	public void testUnescapeHTML() {
		String unescaped = "Lección ñoña!";
		String escaped = "Lecci&oacute;n &ntilde;o&ntilde;a!";
		
		Assert.assertEquals(unescaped, test.unescapeHTML(unescaped));
		Assert.assertEquals(unescaped, test.unescapeHTML(escaped));
		
		Assert.assertNull(test.unescapeHTML(NULL_STRING));		
		Assert.assertEquals(BLANK_STRING, test.unescapeHTML(BLANK_STRING));
		Assert.assertEquals(EMPTY_STRING, test.unescapeHTML(EMPTY_STRING));
	}

	@Test
	public void testSplit() {
		String splittable = "One,two,three,four";
		List<String> listComma = new ArrayList<String>();
		listComma.add("one");
		listComma.add("two");
		listComma.add("three");
		listComma.add("four");
		
		List<String> listO = new ArrayList<String>();
		listO.add("one,tw");
		listO.add(",three,f");
		listO.add("ur");		
		
		Assert.assertEquals(listComma, test.split(splittable,","));
		Assert.assertEquals(listO, test.split(splittable,"o"));
		
		Assert.assertNull(test.split(NULL_STRING, NULL_STRING));		
		Assert.assertNull(test.split(BLANK_STRING, NULL_STRING));
		Assert.assertNull(test.split(EMPTY_STRING, NULL_STRING));
		
		Assert.assertNull(test.split(NULL_STRING, EMPTY_STRING));		
		Assert.assertNull(test.split(BLANK_STRING, EMPTY_STRING));
		Assert.assertNull(test.split(EMPTY_STRING, EMPTY_STRING));
	}
}
