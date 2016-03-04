package com.julvez.pfc.teachonsnap.ut.notify.mail;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.manager.mail.MailManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.notify.mail.NotifyServiceMail;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.ut.notify.NotifyServiceTest;

public class NotifyServiceMailTest extends NotifyServiceTest {

	@Mock
	private MailManager mailManager;
	
	@Mock
	private StringManager stringManager;
	
	@Mock	
	private URLService urlService;
	
	@Mock
	private TextService textService;
	
	@Mock
	private LangService langService;
	
	@Override
	protected NotifyService getService() {		
		return new NotifyServiceMail(mailManager, stringManager, urlService, textService, langService);
	}

	@Test
	public void testInfoUserString() {
		when(mailManager.sendHTML(eq(email), anyString(), anyString())).thenReturn(true);
		super.testInfoUserString();
		verify(mailManager).sendHTML(anyString(), anyString(), anyString());
	}

	@Test
	public void testInfoUserStringString() {
		when(mailManager.sendHTML(eq(email), anyString(), anyString())).thenReturn(true);
		super.testInfoUserStringString();
		verify(mailManager).sendHTML(anyString(), anyString(), anyString());
	}

	@Test
	public void testBroadcast() {
		when(mailManager.broadcastHTML(anyString(), anyString(), anyString())).thenReturn(true);
		super.testBroadcast();
		verify(mailManager).broadcastHTML(anyString(), anyString(), anyString());
	}
}
