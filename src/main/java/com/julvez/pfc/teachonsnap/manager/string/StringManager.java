package com.julvez.pfc.teachonsnap.manager.string;

import java.util.List;

public interface StringManager {

	public boolean isEmpty(String string);

	public boolean isTrue(String string);
	
	public String generateURIname(String source);

	public String getKey(Object... objects);
	
	public String generateMD5(String input);

	/**
	 * Verifica si una cadena contiene sólo dígitos numéricos
	 * @param string
	 * @return true si la cadena sólo contiene dígitos de 0-9
	 */
	public boolean isNumeric(String string);

	public String convertToHTMLParagraph(String string);

	public String decodeURL(String urlEncoded);

	public String escapeHTML(String string);

	public String unescapeHTML(String string);

	public List<String> split(String string, String splitter);
}
