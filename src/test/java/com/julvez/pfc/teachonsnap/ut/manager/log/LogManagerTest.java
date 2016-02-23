package com.julvez.pfc.teachonsnap.ut.manager.log;

import java.io.PrintStream;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerTest;

public abstract class LogManagerTest extends ManagerTest<LogManager> {

	static PrintStream out;
	
	@BeforeClass
	public static void setUpClass() {		
		out = Mockito.spy(System.out);
		System.setOut(out);
	}

	@Test
	public void testDebug() {
		String message = "debug";
		String messageAndCR = message + "\r\n";

		test.debug(message);
	
		Mockito.verify(out, Mockito.atLeastOnce()).write(messageAndCR.getBytes(),0, messageAndCR.getBytes().length);		
	}

	@Test
	public void testErrorString() {
		String message = "error";
		String messageAndCR = message + "\r\n";

		test.error(message);
		
		Mockito.verify(out, Mockito.atLeastOnce()).write(messageAndCR.getBytes(),0, messageAndCR.getBytes().length);
	}

	@Test
	public void testErrorThrowableString() {
		String message = "exception";		
		Throwable t = Mockito.mock(Throwable.class);
		Mockito.when(t.getStackTrace()).thenReturn(new StackTraceElement[0]);

		test.error(t, message);
		
		Mockito.verify(out, Mockito.atLeastOnce()).write((byte[])Mockito.anyObject(),Mockito.anyInt(), Mockito.anyInt());
	}

	@Test
	public void testInfo() {
		String message = "info";
		String messageAndCR = message + "\r\n";

		test.info(message);
		
		Mockito.verify(out, Mockito.atLeastOnce()).write(messageAndCR.getBytes(),0, messageAndCR.getBytes().length);
	}

	@Test
	public void testAddPrefix() {
		String prefix = "prefix";
		String message = "prefixTest";
		String messageAndCR = message + "\r\n";

		test.addPrefix(prefix);		
		test.info(message);
		
		Mockito.verify(out, Mockito.atLeastOnce()).write(messageAndCR.getBytes(),0, messageAndCR.getBytes().length);
	}

	@Test
	public void testRemovePrefix() {
		String prefix = "prefix";
		String message = "noPrefixTest";
		String messageAndCR = message + "\r\n";

		test.addPrefix(prefix);		
		test.info(message);
		test.removePrefix();		
		Mockito.verify(out, Mockito.atLeastOnce()).write(messageAndCR.getBytes(),0, messageAndCR.getBytes().length);
	}

	@Test
	public void testTimer() {
		String message = "timerTest";
		String messageAndCR = "[00:00:000] " + message + "\r\n";

		test.startTimer();		
		test.infoTime(message);
		
		Mockito.verify(out, Mockito.atLeastOnce()).write((byte[])Mockito.anyObject(),Mockito.eq(0), Mockito.eq(messageAndCR.getBytes().length));
	}

	@Test
	public void testClearTimer() {
		String message = "clearTimerTest";
		String messageAndCR = message + "\r\n";

		test.startTimer();		
		test.clearTimer();
		test.info(message);
		Mockito.verify(out, Mockito.atLeastOnce()).write(messageAndCR.getBytes(),0, messageAndCR.getBytes().length);
	}

}
