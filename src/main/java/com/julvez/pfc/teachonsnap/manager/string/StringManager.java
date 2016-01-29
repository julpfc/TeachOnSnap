package com.julvez.pfc.teachonsnap.manager.string;

import java.util.List;

/** String manager providing string manipulation utilities */
public interface StringManager {

	/**
	 * Checks if the string is empty
	 * @param string to check if it's empty
	 * @return true if the string is null, empty or blank
	 */
	public boolean isEmpty(String string);

	/**
	 * Checks if the string contains a true value
	 * @param string to check if it's true
	 * @return true if the string contains the text "true" 
	 * (ignoring case) or a positive integer (>0).
	 */
	public boolean isTrue(String string);
	
	/**
	 * Generates a string without any special characters,
	 * so it's a URL-valid string.
	 * @param source to generate from
	 * @return a string without special characters, null if error
	 */
	public String generateURIname(String source);

	/**
	 * Generates a string key from the specified objects
	 * @param objects to generate a key from
	 * @return a string key
	 */
	public String getKey(Object... objects);
	
	/**
	 * Generates the MD5 hash from the input string.
	 * @param input to generate the hash from
	 * @return MD5 hash for this string, null if error
	 */
	public String generateMD5(String input);

	/**
	 * Checks if the string only contains numeric digits
	 * @param string to check if it's numeric
	 * @return true if the string only contains digits 0-9
	 */
	public boolean isNumeric(String string);

	/**
	 * Converts lines into HTML paragraphs <p\>
	 * @param string to be converted
	 * @return converted string, null if error
	 */
	public String convertToHTMLParagraph(String string);

	/**
	 * Decodes the encoded string from URL encode
	 * @param urlEncoded encoded URL
	 * @return the decoded string, null if error
	 */
	public String decodeURL(String urlEncoded);
	
	/**
	 * Encodes the string into URL encode
	 * @param url to be encoded
	 * @return the encoded string, null if error
	 */
	public String encodeURL(String url);

	/**
	 * Escapes the special characters into HTML entities.
	 * @param string to be escaped
	 * @return the escaped string, null if error
	 */
	public String escapeHTML(String string);

	/**
	 * Unescapes HTML entities from the string 
	 * @param string to be unescaped
	 * @return the unscaped string, null if error
	 */
	public String unescapeHTML(String string);

	/**
	 * Splits a string into a list of pieces using
	 * the splitter string.
	 * @param string to be splitted
	 * @param splitter to split by
	 * @return the list of pieces
	 */
	public List<String> split(String string, String splitter);

}
