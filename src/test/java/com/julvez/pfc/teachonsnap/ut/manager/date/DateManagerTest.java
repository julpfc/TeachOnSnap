package com.julvez.pfc.teachonsnap.ut.manager.date;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.julvez.pfc.teachonsnap.manager.date.DateManager;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerTest;

public abstract class DateManagerTest extends ManagerTest<DateManager>{
		
	@Test
	public void testGetCurrentDate() {
		Date date = new Date();
		String testDate = test.getCurrentDate();

		SimpleDateFormat formatter = new SimpleDateFormat(DateManager.DEFAULT_DATE_FORMAT);
		String formatDate = formatter.format(date);
		
		Assert.assertNotNull(testDate);
		Assert.assertEquals(formatDate, testDate);
	}

	@Test
	public void testGetCurrentDateString() {
		String format = "dd/MM/yyyy HH:mm [ss]";
		Date date = new Date();
		String testDate = test.getCurrentDate(DateManager.DEFAULT_DATE_FORMAT);
		String testDate2 = test.getCurrentDate(format);

		SimpleDateFormat formatter = new SimpleDateFormat(DateManager.DEFAULT_DATE_FORMAT);
		String formatDate = formatter.format(date);		
		SimpleDateFormat formatter2 = new SimpleDateFormat(format);
		String formatDate2 = formatter2.format(date);
		
		Assert.assertNotNull(testDate);
		Assert.assertEquals(formatDate, testDate);
		Assert.assertEquals(formatDate2, testDate2);
	}

}