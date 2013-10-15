package com.droidbrew.travelkeeper.spec.spike;

import static junit.framework.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DateSpec {
	
	@Test
	public void javaDateIsInMilliseconds(){
		Date start_date = new Date(12000000L);
		assertTrue(start_date.getTime() == 12000000L); 
	}
	
	@Test
	public void javaDateToDay(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(12000000L));
		int day = cal.get(Calendar.DAY_OF_MONTH);
		assertEquals(1, day);
	}
	
	@Test
	public void truncateDateToEndOfDay(){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(12000000L);
	    cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
	    cal.set(Calendar.MINUTE,      cal.getMaximum(Calendar.MINUTE));
	    cal.set(Calendar.SECOND,      cal.getMaximum(Calendar.SECOND));
	    cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
	    assertTrue(cal.getTime().getTime() > 12000000L);
	}
	
	@Test
	public void truncateDateToStartOfDay(){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(12000000L);
	    cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
	    cal.set(Calendar.MINUTE,      cal.getMinimum(Calendar.MINUTE));
	    cal.set(Calendar.SECOND,      cal.getMinimum(Calendar.SECOND));
	    cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
	    assertTrue(cal.getTime().getTime() < 12000000L);
	}
	
}
