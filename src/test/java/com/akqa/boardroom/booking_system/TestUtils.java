package com.akqa.boardroom.booking_system;

import static org.junit.Assert.*;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.company.boardroom.booking_system.service.Utils;

import junit.framework.Assert;

public class TestUtils {

	@Test
	public void testParse() {
		List<String> list = new ArrayList<String>();
		Utils u = new Utils();
		list = u.parseTextInput();
		assertNotNull(list);
		assertFalse(list.isEmpty());
	}
	
	@Test
	public void testParseDateWithSecond() {
		String date = "2011-08-06 16:00:52";
		Utils u = new Utils();
		assertNotNull(u.parseDateWithSecond(date));
	}
	
	@Test
	public void testParseDate() {
		String date = "2011-08-06 06:02";
		Utils u = new Utils();
		assertNotNull(u.parseDate(date));
	}
	
	@Test
	public void testSplit() {
		ArrayList<String> list = new ArrayList<String>();
		String text = "2011-08-06 16:00:52";
		Utils u = new Utils();
		list = u.splitLine(text);
		assertNotNull(list);
		assertFalse(list.isEmpty());
		assertEquals(2, list.size());
	}
	
	@Test
	public void testInvalidParseDateWithSecond() {
		String date = "2011-08-06 16:00";
		Utils u = new Utils();
		try {
			u.parseDateWithSecond(date);
		} catch (DateTimeParseException dtpe) {
			return;
		}
		Assert.fail("Supposed to throw DateTimeParseException");
	}
	
	@Test
	public void testInvalidParseDate() {
		String date = "2011-08-006:02";
		Utils u = new Utils();
		try {
			u.parseDate(date);
		} catch (DateTimeParseException dtpe) {
			return;
		}
		Assert.fail("Supposed to throw DateTimeParseException");
	}
}
