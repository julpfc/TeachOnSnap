package com.julvez.pfc.teachonsnap.ut.text.impl;

import java.util.Locale;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import com.julvez.pfc.teachonsnap.manager.text.TextManager;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.text.impl.TextServiceImpl;
import com.julvez.pfc.teachonsnap.ut.text.TextServiceTest;

public class TextServiceImplTest extends TextServiceTest {

	@Mock
	private TextManager textManager;
	
	@Override
	protected TextService getService() {		
		return new TextServiceImpl(textManager);
	}
	
	@Test
	public void testGetLocalizedTextLanguageEnumOfQ() {		
		when(textManager.getLocalizedText(any(Locale.class), eq(TestMessageKey.TEST.toString()), anyString(), any(String[].class)))
			.thenReturn("prueba", "test", "test");
		when(textManager.getLocalizedText(any(Locale.class), eq(TestMessageKey.TEST_PARAM.toString()), anyString(), any(String[].class)))
			.thenReturn("prueba {0}", "test {0}", "test {0}");
		super.testGetLocalizedTextLanguageEnumOfQ();
		verify(textManager, times(6)).getLocalizedText(any(Locale.class), anyString(), anyString(), any(String[].class));
	}

	@Test
	public void testGetLocalizedTextLanguageEnumOfQStringArray() {
		when(textManager.getLocalizedText(any(Locale.class), eq(TestMessageKey.TEST.toString()), anyString(), any(String[].class)))
		.thenReturn("prueba", "prueba", "test", "test", "test", "test");
		when(textManager.getLocalizedText(any(Locale.class), eq(TestMessageKey.TEST_PARAM.toString()), anyString(), any(String[].class)))
			.thenReturn("prueba test", "prueba null", "test test", "test null", "test test", "test null");

		super.testGetLocalizedTextLanguageEnumOfQStringArray();
		
		verify(textManager, times(12)).getLocalizedText(any(Locale.class), anyString(), anyString(), any(String[].class));
	}
}
