package com.akqa.boardroom.booking_system;

import org.junit.Test;

import com.company.boardroom.booking_system.service.ManageBooking;
import com.company.boardroom.booking_system.service.ManageBookingImpl;

public class TestBooking {
	
	@Test
	public void testRun() {
		ManageBooking mb = new ManageBookingImpl();
		mb.executeBooking();
	}
	
	
}
