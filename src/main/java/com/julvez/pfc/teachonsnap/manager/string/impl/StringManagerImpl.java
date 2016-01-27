package com.julvez.pfc.teachonsnap.manager.string.impl;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;

/**
 * Implementation of the StringManager, uses an internal {@link LogManager} 
 * to log the errors.
 */

public class StringManagerImpl implements StringManager {

	/** Log manager providing logging capabilities */
	private LogManager logger;
	
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param logger Log manager providing logging capabilities
	 */
	public StringManagerImpl(LogManager logger) {
		if(logger == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.logger = logger;
	}

	@Override
	public boolean isEmpty(String string) {
		return StringUtils.isBlank(string);		
	}

	@Override
	public boolean isTrue(String string) {
		boolean isTrue = false;
		
		if(!isEmpty(string)){
			try {
				//check if it's a positive integer
				int i = Integer.parseInt(string);
				isTrue = i>0;
			} catch (Exception e) {
				//check if it's a "true" string (ignoring case)
				isTrue = Boolean.parseBoolean(string);
			}
		}
		return isTrue;
	}

	@Override
	public String generateURIname(String source) {
		String result = null;
		
		if(source!=null){
			//replace or remove special characters
			result = Normalizer.normalize(source.toLowerCase(), Form.NFD)
			        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
			        .replaceAll("[^\\p{Alnum}]+", "-")
			        .replaceAll("[^a-z0-9]+$", "")
			        .replaceAll("^[^a-z0-9]+", "");
		}
		
		return result;
	}

	
	@Override
	public String getKey(Object...objects){
		//Create a string key "[obj.toString()] ..."
		String format = new String(new char[objects.length])
        .replace("\0", "[%s]");
		return String.format(format, objects);
	}

	@Override
	public String generateMD5(String input) {
		String output = null;
		
		if(!input.isEmpty()){
			MessageDigest md;
		
			try {
				//get converter
				md = MessageDigest.getInstance("MD5");
			
				//get converted array
				md.update(input.getBytes());				
				byte byteData[] = md.digest();
				
				//convert to string
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < byteData.length; i++) {
					sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
				}
				output = sb.toString();
			}
			catch (Throwable t) {
				logger.error(t, "Error generating MD5: " + input);
			}
		} 
		return output;
	}

	@Override
	public boolean isNumeric(String string) {
		return !isEmpty(string) && StringUtils.isNumeric(string);
	}

	@Override
	public String convertToHTMLParagraph(String string) {
		String ret = null;
		if(!isEmpty(string)){
			//replace lines by HTML paragraphs <p>'s
			StringBuffer sb = new StringBuffer();
			sb.append("<p>").append(string.replaceAll("\\r\\n|\\r|\\n", "</p><p>")).append("</p>");
			
			//Remove last duplicated <p>'s
			int lastP = sb.lastIndexOf("<p></p>");
			if(lastP>0){
				sb.delete(lastP,lastP+7);
			}
			ret = sb.toString();
		}
		return ret;
	}

	@Override
	public String decodeURL(String urlEncoded) {
		String decoded = null;				
		try {
			//decode URL
			decoded = URLDecoder.decode(urlEncoded, "UTF-8");
		} catch (Throwable t) {
			logger.error(t, "Error decoding URLencoded: " + urlEncoded);
		}
		return decoded;
	}

	@Override
	public String escapeHTML(String string) {
		return StringEscapeUtils.escapeHtml4(string);
	}
	
	@Override
	public String unescapeHTML(String string) {
		return StringEscapeUtils.unescapeHtml4(string);
	}

	@Override
	public List<String> split(String string, String splitter) {
		List<String> list = null;
		
		if(!isEmpty(string)){
			String[] params = null;
			
			//split if the splitter is present on the string
			if(string.contains(splitter)){
				params = string.split(splitter);
			}
			else params = new String[]{string};
			
			//convert array to List
			if(params != null){
				list = new ArrayList<String>();
				for(String param:params){
					list.add(param.toLowerCase().trim());
				}
			}			
		}		
		
		return list;
	}

	@Override
	public String encodeURL(String url) {
		String encoded = null;				
		try {
			//encode URL
			encoded = URLEncoder.encode(url, "UTF-8");
		} catch (Throwable t) {
			logger.error(t, "Error coding URLencoded: " + url);
		}
		return encoded;

	}
}
