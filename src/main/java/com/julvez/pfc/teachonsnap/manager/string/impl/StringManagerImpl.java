package com.julvez.pfc.teachonsnap.manager.string.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.text.Normalizer.Form;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.julvez.pfc.teachonsnap.manager.string.StringManager;


public class StringManagerImpl implements StringManager {

	@Override
	public boolean isEmpty(String string) {
		return StringUtils.isBlank(string);		
	}

	@Override
	public boolean isTrue(String string) {
		boolean isTrue = false;
		
		if(!isEmpty(string)){
			try {
				int i = Integer.parseInt(string);
				isTrue = i>0;
			} catch (Exception e) {
				isTrue = Boolean.parseBoolean(string);
			}
		}
		return isTrue;
	}

	@Override
	public String generateURIname(String source) {
		String result = null;
		
		if(source!=null){
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
				md = MessageDigest.getInstance("MD5");
			
				md.update(input.getBytes());
				
				byte byteData[] = md.digest();
				
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < byteData.length; i++) {
					sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
				}
				output = sb.toString();
			}
			catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
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
			StringBuffer sb = new StringBuffer();
			sb.append("<p>").append(string.replaceAll("\\r\\n|\\r|\\n", "</p><p>")).append("</p>");
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
		System.out.println(urlEncoded);		
		try {
			decoded = URLDecoder.decode(urlEncoded, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
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
}
