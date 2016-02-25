package com.julvez.pfc.teachonsnap.ut.manager.log;

import org.junit.Test;
import org.mockito.Mockito;

import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerTest;


public abstract class LogManagerTest extends ManagerTest<LogManager> {

	@Test
	public void testDebug() {
		String message = "debug";	
		test.debug(message);
	}

	@Test
	public void testErrorString() {
		String message = "error";
		test.error(message);
	}

	@Test
	public void testErrorThrowableString() {
		String message = "exception";		
		Throwable t = Mockito.mock(Throwable.class);
		Mockito.when(t.getStackTrace()).thenReturn(new StackTraceElement[0]);

		test.error(t, message);
	}

	@Test
	public void testInfo() {
		String message = "info";
		test.info(message);
	}

	@Test
	public void testAddPrefix() {
		String prefix = "prefix";
		String message = "prefixTest";
		test.addPrefix(prefix);		
		test.info(message);
	}

	@Test
	public void testRemovePrefix() {
		String prefix = "prefix";
		String message = "noPrefixTest";

		test.addPrefix(prefix);		
		test.removePrefix();		
		test.info(message);
	}

	@Test
	public void testTimer() {
		String message = "timerTest";

		test.startTimer();		
		test.infoTime(message);
	}

	@Test
	public void testClearTimer() {
		String message = "clearTimerTest";
		test.startTimer();		
		test.clearTimer();
		test.info(message);
	}

}
