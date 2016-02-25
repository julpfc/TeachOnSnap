package com.julvez.pfc.teachonsnap.ut.manager.request;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.ErrorBean;
import com.julvez.pfc.teachonsnap.controller.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.controller.model.ErrorType;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerTest;

public abstract class RequestManagerTest extends ManagerTest<RequestManager> {

	@Mock
	protected HttpServletRequest request;
	
	@Test
	public void testGetSessionAttributeHttpServletRequestEnumOfQClassOfT() {
		HttpSession session = Mockito.mock(HttpSession.class);
		Mockito.when(request.getSession(true)).thenReturn(session);
		
		Assert.assertNull(test.getSessionAttribute(null, null, null));
		Assert.assertNull(test.getSessionAttribute(request, null, null));
		Assert.assertNull(test.getSessionAttribute(request, SessionAttribute.ERROR, null));		
		Assert.assertNull(test.getSessionAttribute(request, SessionAttribute.ERROR, ErrorBean.class));
		
		ErrorBean error = new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.NONE);
		Mockito.when(session.getAttribute(SessionAttribute.ERROR.toString())).thenReturn(error);
		
		Assert.assertSame(error, test.getSessionAttribute(request, SessionAttribute.ERROR, ErrorBean.class));		
	}

	@Test
	public void testGetSessionAttributeHttpServletRequestEnumOfQ() {
		HttpSession session = Mockito.mock(HttpSession.class);
		Mockito.when(request.getSession(true)).thenReturn(session);
		
		Assert.assertNull(test.getSessionAttribute(null, null));
		Assert.assertNull(test.getSessionAttribute(request, null));
		
		Mockito.when(session.getAttribute(SessionAttribute.LAST_PAGE.toString())).thenReturn("test");
		
		Assert.assertSame("test", test.getSessionAttribute(request, SessionAttribute.LAST_PAGE));
	}

	@Test
	public void testSetSessionAttribute() {
		HttpSession session = Mockito.mock(HttpSession.class);
		Mockito.when(request.getSession(true)).thenReturn(session);
		
		test.setSessionAttribute(request, SessionAttribute.LAST_PAGE, null);
		Mockito.verify(session, Mockito.never()).setAttribute(Mockito.anyString(), Mockito.anyObject());

		test.setSessionAttribute(request, null, null);
		Mockito.verify(session, Mockito.never()).setAttribute(Mockito.anyString(), Mockito.anyObject());

		test.setSessionAttribute(null, null, null);
		Mockito.verify(session, Mockito.never()).setAttribute(Mockito.anyString(), Mockito.anyObject());
		
		test.setSessionAttribute(request, SessionAttribute.LAST_PAGE, "test");
		Mockito.verify(session, Mockito.atLeastOnce())
			.setAttribute(Mockito.eq(SessionAttribute.LAST_PAGE.toString()), Mockito.anyObject());		
	}

	
	@Test
	public void testGetRequestLanguage() {
		Locale localeES = new Locale("es");
		Locale localeEN = new Locale("en");
		Locale localeEMPTY = new Locale(EMPTY_STRING);
		
		Mockito.when(request.getLocale()).thenReturn(localeES);
		Assert.assertEquals("es", test.getRequestLanguage(request));

		Mockito.when(request.getLocale()).thenReturn(localeEN);
		Assert.assertEquals("en", test.getRequestLanguage(request));

		Mockito.when(request.getLocale()).thenReturn(localeEMPTY);
		Assert.assertNull(test.getRequestLanguage(request));

		Assert.assertNull(test.getRequestLanguage(null));
	}

	@Test
	public void testGetIP() {
		Mockito.when(request.getRemoteAddr()).thenReturn("127.0.0.1");
		Assert.assertEquals("127.0.0.1", test.getIP(request));

		Mockito.when(request.getRemoteAddr()).thenReturn(EMPTY_STRING);
		Assert.assertNull(test.getIP(request));
		
		Mockito.when(request.getRemoteAddr()).thenReturn(BLANK_STRING);
		Assert.assertNull(test.getIP(request));
		
		Mockito.when(request.getRemoteAddr()).thenReturn(NULL_STRING);
		Assert.assertNull(test.getIP(request));

		Assert.assertNull(test.getIP(null));
	}

	@Test
	public void testGetParameter() {
		Assert.assertNull(test.getParameter(null, null));
		Assert.assertNull(test.getParameter(request, null));
		
		Mockito.when(request.getParameter(Parameter.FIRST_NAME.toString())).thenReturn("test");		
		Assert.assertSame("test", test.getParameter(request, Parameter.FIRST_NAME));
		
		Mockito.when(request.getParameter(Parameter.FIRST_NAME.toString())).thenReturn(NULL_STRING);		
		Assert.assertNull(test.getParameter(request, Parameter.FIRST_NAME));
		
		Mockito.when(request.getParameter(Parameter.FIRST_NAME.toString())).thenReturn(EMPTY_STRING);		
		Assert.assertNull(test.getParameter(request, Parameter.FIRST_NAME));
		
		Mockito.when(request.getParameter(Parameter.FIRST_NAME.toString())).thenReturn(BLANK_STRING);		
		Assert.assertNull(test.getParameter(request, Parameter.FIRST_NAME));
	}

	@Test
	public void testGetBooleanParameter() {
		Assert.assertFalse(test.getBooleanParameter(null, null));
		Assert.assertFalse(test.getBooleanParameter(request, null));
		
		Mockito.when(request.getParameter(Parameter.LOGOUT.toString())).thenReturn("true");		
		Assert.assertTrue(test.getBooleanParameter(request, Parameter.LOGOUT));
		
		Mockito.when(request.getParameter(Parameter.LOGOUT.toString())).thenReturn(NULL_STRING);		
		Assert.assertFalse(test.getBooleanParameter(request, Parameter.LOGOUT));
		
		Mockito.when(request.getParameter(Parameter.LOGOUT.toString())).thenReturn(EMPTY_STRING);		
		Assert.assertFalse(test.getBooleanParameter(request, Parameter.LOGOUT));
		
		Mockito.when(request.getParameter(Parameter.LOGOUT.toString())).thenReturn(BLANK_STRING);		
		Assert.assertFalse(test.getBooleanParameter(request, Parameter.LOGOUT));
	}

	@Test
	public void testGetNumericParameter() {
		Assert.assertEquals(-1, test.getNumericParameter(null, null));
		Assert.assertEquals(-1, test.getNumericParameter(request, null));
		
		Mockito.when(request.getParameter(Parameter.LESSON_NEW_LANGUAGE.toString())).thenReturn("101");		
		Assert.assertEquals(101, test.getNumericParameter(request, Parameter.LESSON_NEW_LANGUAGE));
		
		Mockito.when(request.getParameter(Parameter.LESSON_NEW_LANGUAGE.toString())).thenReturn(NULL_STRING);		
		Assert.assertEquals(-1, test.getNumericParameter(request, Parameter.LESSON_NEW_LANGUAGE));
		
		Mockito.when(request.getParameter(Parameter.LESSON_NEW_LANGUAGE.toString())).thenReturn(EMPTY_STRING);		
		Assert.assertEquals(-1, test.getNumericParameter(request, Parameter.LESSON_NEW_LANGUAGE));
		
		Mockito.when(request.getParameter(Parameter.LESSON_NEW_LANGUAGE.toString())).thenReturn(BLANK_STRING);		
		Assert.assertEquals(-1, test.getNumericParameter(request, Parameter.LESSON_NEW_LANGUAGE));
	}

	@Test
	public void testGetParameterList() {
		String[] params = new String[]{"param1","param2"};
		Map<String,String[]> paramMap = new HashMap<String, String[]>();

		Assert.assertNull(test.getParameterList(null, null));
		Assert.assertNull(test.getParameterList(request, null));

		Mockito.when(request.getParameterMap()).thenReturn(paramMap);		
		
		paramMap.put(Parameter.LESSON_NEW_TAGS.toString(),params);
		int i = 0;
		for(String s:test.getParameterList(request, Parameter.LESSON_NEW_TAGS)){
			Assert.assertEquals(params[i++],s);
		}
				
		paramMap.put(Parameter.LESSON_NEW_TAGS.toString(), null);		
		Assert.assertNull(test.getParameterList(request, Parameter.LESSON_NEW_TAGS));
	}

	@Test
	public void testGetBlankParameter() {
		Assert.assertNull(test.getBlankParameter(null, null));
		Assert.assertNull(test.getBlankParameter(request, null));
		
		Mockito.when(request.getParameter(Parameter.FIRST_NAME.toString())).thenReturn("test");		
		Assert.assertSame("test", test.getBlankParameter(request, Parameter.FIRST_NAME));

		Mockito.when(request.getParameter(Parameter.FIRST_NAME.toString())).thenReturn(BLANK_STRING);		
		Assert.assertSame(BLANK_STRING, test.getBlankParameter(request, Parameter.FIRST_NAME));
		
		Mockito.when(request.getParameter(Parameter.FIRST_NAME.toString())).thenReturn(NULL_STRING);		
		Assert.assertNull(test.getBlankParameter(request, Parameter.FIRST_NAME));
		
		Mockito.when(request.getParameter(Parameter.FIRST_NAME.toString())).thenReturn(EMPTY_STRING);		
		Assert.assertSame(EMPTY_STRING, test.getBlankParameter(request, Parameter.FIRST_NAME));		
	}

	@Test
	public void testGetAttribute() {
		Assert.assertNull(test.getAttribute(null, null, null));
		Assert.assertNull(test.getAttribute(request, null, null));
		Assert.assertNull(test.getAttribute(request, Attribute.STRING_BACKPAGE, null));		
		Assert.assertNull(test.getAttribute(request, Attribute.STRING_BACKPAGE, String.class));
		
		Mockito.when(request.getAttribute(Attribute.STRING_BACKPAGE.toString())).thenReturn("test");
		
		Assert.assertSame("test", test.getAttribute(request, Attribute.STRING_BACKPAGE, String.class));
	}

	@Test
	public void testSetAttribute() {
		test.setAttribute(request, Attribute.STRING_BACKPAGE, null);
		Mockito.verify(request, Mockito.never()).setAttribute(Mockito.anyString(), Mockito.anyObject());

		test.setAttribute(request, null, null);
		Mockito.verify(request, Mockito.never()).setAttribute(Mockito.anyString(), Mockito.anyObject());

		test.setAttribute(null, null, null);
		Mockito.verify(request, Mockito.never()).setAttribute(Mockito.anyString(), Mockito.anyObject());
		
		test.setAttribute(request, Attribute.STRING_BACKPAGE, "test");
		Mockito.verify(request, Mockito.atLeastOnce())
			.setAttribute(Mockito.eq(Attribute.STRING_BACKPAGE.toString()), Mockito.anyObject());	
	}

	@Test
	public void testGetPartFilename() {
		Part part = Mockito.mock(Part.class);
		
		Mockito.when(part.getHeader(Mockito.anyString())).thenReturn("attachment; filename=\"NAME.txt\"");
		Assert.assertEquals("NAME.txt", test.getPartFilename(part));

		Mockito.when(part.getHeader(Mockito.anyString())).thenReturn(EMPTY_STRING);
		Assert.assertNull(test.getPartFilename(part));
		
		Mockito.when(part.getHeader(Mockito.anyString())).thenReturn(BLANK_STRING);
		Assert.assertNull(test.getPartFilename(part));
		
		Mockito.when(part.getHeader(Mockito.anyString())).thenReturn(NULL_STRING);
		Assert.assertNull(test.getPartFilename(part));

		Assert.assertNull(test.getPartFilename(null));
	}

	@Test
	public void testSetFileMetadataHeaders() {
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);		
		
		test.setFileMetadataHeaders(response, "", "");
		Mockito.verify(response, Mockito.atLeastOnce()).setContentType(Mockito.anyString());
		Mockito.verify(response, Mockito.atLeastOnce()).setCharacterEncoding(Mockito.anyString());
		Mockito.verify(response, Mockito.atLeastOnce()).setHeader(Mockito.anyString(), Mockito.anyString());
	}

	@Test
	public void testDownloadFile() {
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		InputStream input = Mockito.mock(InputStream.class);
		ServletOutputStream output = Mockito.mock(ServletOutputStream.class);
		
		test.downloadFile(null, null, null, null);
		Mockito.verify(response, Mockito.never()).setContentType(Mockito.anyString());

		test.downloadFile(response, null, null, null);
		Mockito.verify(response, Mockito.never()).setContentType(Mockito.anyString());
		
		test.downloadFile(response, "", null, null);
		Mockito.verify(response, Mockito.never()).setContentType(Mockito.anyString());
		
		test.downloadFile(response, "", "", null);
		Mockito.verify(response, Mockito.never()).setContentType(Mockito.anyString());
		
		
		IOException io = new IOException();
		try {
			Mockito.when(response.getOutputStream()).thenReturn(output);
			Mockito.doThrow(io).when(output).write((byte[])Mockito.any(), Mockito.anyInt(), Mockito.anyInt());
			test.downloadFile(response, "", "", input);
		} catch (IOException e) {
			Assert.assertEquals(io, e);
		}
		
		try {
			Mockito.when(response.getOutputStream()).thenReturn(output);	
			Mockito.when(input.read((byte[])Mockito.any())).thenReturn(1);
			test.downloadFile(response, "", "", input);
			Mockito.verify(output, Mockito.atLeastOnce()).write((byte[])Mockito.any(), Mockito.anyInt(), Mockito.anyInt());
			Mockito.verify(output, Mockito.atLeastOnce()).close();
			Mockito.verify(input, Mockito.atLeastOnce()).close();
			
		} catch (IOException e) {
			Assert.fail(e.getMessage());
		}
		
	}

	@Test
	public void testGetSessionID() {
		HttpSession session = Mockito.mock(HttpSession.class);
		Mockito.when(request.getSession(true)).thenReturn(session);
		
		Assert.assertNull(test.getSessionID(null));		
		
		Mockito.when(session.getId()).thenReturn("01234567890123456789");
		
		Assert.assertEquals("0123456789", test.getSessionID(request));
	}
	
	@Test
	public void testSplitParamsFromControllerURI() {		
		String[] params = new String[]{"param1","param2"};
		
		ServletContext sc = Mockito.mock(ServletContext.class);
		Mockito.when(request.getServletContext()).thenReturn(sc);
		
		Mockito.when(sc.getContextPath()).thenReturn("");
		Mockito.when(request.getServletPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/");
		Assert.assertNull(test.splitParamsFromControllerURI(request));

		Mockito.when(sc.getContextPath()).thenReturn("");
		Mockito.when(request.getServletPath()).thenReturn("/servlet/mapping");
		Mockito.when(request.getRequestURI()).thenReturn("/servlet/mapping/param1/param2");

		int i = 0;
		for(String s:test.splitParamsFromControllerURI(request)){
			Assert.assertEquals(params[i++],s);
		}
		Mockito.when(sc.getContextPath()).thenReturn("/context");
		Mockito.when(request.getServletPath()).thenReturn("/servlet/mapping");
		Mockito.when(request.getRequestURI()).thenReturn("/context/servlet/mapping/param1/param2");
		i = 0;
		for(String s:test.splitParamsFromControllerURI(request)){
			Assert.assertEquals(params[i++],s);
		}
		
		Mockito.when(sc.getContextPath()).thenReturn("/context");
		Mockito.when(request.getServletPath()).thenReturn("");
		Mockito.when(request.getRequestURI()).thenReturn("/context/param1/param2/");
		i = 0;
		for(String s:test.splitParamsFromControllerURI(request)){
			Assert.assertEquals(params[i++],s);
		}
		
		Assert.assertNull(test.splitParamsFromControllerURI(null));
	}
}
