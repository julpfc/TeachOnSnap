package com.julvez.pfc.teachonsnap.ut.manager.log.log4j2;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.log4j2.LogManagerLog4j2;
import com.julvez.pfc.teachonsnap.ut.manager.log.LogManagerTest;

public class LogManagerLog4j2Test extends LogManagerTest {

	static Appender app;
	
	@Override
	protected LogManager getManager() {
		return new LogManagerLog4j2();
	}
	
	@BeforeClass
	public static void setUpClass(){
		Logger logger = (Logger) org.apache.logging.log4j.LogManager.getLogger(LogManagerTest.class.getName());
		app = Mockito.mock(Appender.class); 
		Mockito.when(app.getName()).thenReturn("MockAppender");
		Mockito.when(app.isStarted()).thenReturn(true);
		Mockito.when(app.isStopped()).thenReturn(false);
		logger.addAppender(app);
	}
	
	@Test
	public void testDebug() {
		super.testDebug();
		ArgumentCaptor<LogEvent> captor = ArgumentCaptor.forClass(LogEvent.class);
		Mockito.verify(app, Mockito.atLeastOnce()).append(captor.capture());
		Assert.assertEquals("debug", captor.getValue().getMessage().getFormattedMessage());
		Assert.assertEquals("DEBUG", captor.getValue().getLevel().name());
	}

	@Override
	public void testErrorString() {
		super.testErrorString();
		ArgumentCaptor<LogEvent> captor = ArgumentCaptor.forClass(LogEvent.class);
		Mockito.verify(app, Mockito.atLeastOnce()).append(captor.capture());
		Assert.assertEquals("error", captor.getValue().getMessage().getFormattedMessage());
		Assert.assertEquals("ERROR", captor.getValue().getLevel().name());
	}

	@Override
	public void testErrorThrowableString() {
		super.testErrorThrowableString();
		ArgumentCaptor<LogEvent> captor = ArgumentCaptor.forClass(LogEvent.class);
		Mockito.verify(app, Mockito.atLeastOnce()).append(captor.capture());
		Assert.assertEquals("exception", captor.getValue().getMessage().getFormattedMessage());
		Assert.assertEquals("ERROR", captor.getValue().getLevel().name());
	}

	@Override
	public void testInfo() {
		super.testInfo();
		ArgumentCaptor<LogEvent> captor = ArgumentCaptor.forClass(LogEvent.class);
		Mockito.verify(app, Mockito.atLeastOnce()).append(captor.capture());
		Assert.assertEquals("info", captor.getValue().getMessage().getFormattedMessage());
		Assert.assertEquals("INFO", captor.getValue().getLevel().name());
	}

	@Override
	public void testAddPrefix() {
		super.testAddPrefix();
		ArgumentCaptor<LogEvent> captor = ArgumentCaptor.forClass(LogEvent.class);
		Mockito.verify(app, Mockito.atLeastOnce()).append(captor.capture());
		Assert.assertEquals("prefixTest", captor.getValue().getMessage().getFormattedMessage());
		Assert.assertEquals("INFO", captor.getValue().getLevel().name());
	}

	@Override
	public void testRemovePrefix() {
		super.testRemovePrefix();
		ArgumentCaptor<LogEvent> captor = ArgumentCaptor.forClass(LogEvent.class);
		Mockito.verify(app, Mockito.atLeastOnce()).append(captor.capture());
		Assert.assertEquals("noPrefixTest", captor.getValue().getMessage().getFormattedMessage());
		Assert.assertEquals("INFO", captor.getValue().getLevel().name());
	}

	@Override
	public void testTimer() {
		super.testTimer();
		ArgumentCaptor<LogEvent> captor = ArgumentCaptor.forClass(LogEvent.class);
		Mockito.verify(app, Mockito.atLeastOnce()).append(captor.capture());
		Assert.assertEquals("[00:00:000] timerTest", captor.getValue().getMessage().getFormattedMessage());
		Assert.assertEquals("INFO", captor.getValue().getLevel().name());
	}

	@Override
	public void testClearTimer() {
		super.testClearTimer();
		ArgumentCaptor<LogEvent> captor = ArgumentCaptor.forClass(LogEvent.class);
		Mockito.verify(app, Mockito.atLeastOnce()).append(captor.capture());
		Assert.assertEquals("clearTimerTest", captor.getValue().getMessage().getFormattedMessage());
		Assert.assertEquals("INFO", captor.getValue().getLevel().name());
	}
}
