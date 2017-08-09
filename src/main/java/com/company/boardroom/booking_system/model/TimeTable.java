package com.company.boardroom.booking_system.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/********************************************************************
 * The object which contains the successfully booked appointments.
 * 
 * @author Hari
 *
 ********************************************************************/
public class TimeTable {
	
	/**
	 * The day of bookings
	 */
	private LocalDate day;
	
	/**
	 * The start time of the working day
	 */
	private LocalDateTime startOfDay;
	
	/**
	 * The end time of the working day
	 */
	private LocalDateTime endOfDay;
	
	/**
	 * The list of confirmed bookings
	 */
	private List<Booking> confirmedBookings;
	
	/**
	 * @return the day
	 */
	public LocalDate getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(LocalDate day) {
		this.day = day;
	}
	/**
	 * @return the startOfDay
	 */
	public LocalDateTime getStartOfDay() {
		return startOfDay;
	}
	/**
	 * @param startOfDay the startOfDay to set
	 */
	public void setStartOfDay(LocalDateTime startOfDay) {
		this.startOfDay = startOfDay;
	}
	/**
	 * @return the endOfDay
	 */
	public LocalDateTime getEndOfDay() {
		return endOfDay;
	}
	/**
	 * @param endOfDay the endOfDay to set
	 */
	public void setEndOfDay(LocalDateTime endOfDay) {
		this.endOfDay = endOfDay;
	}
	/**
	 * @return the confirmedBookings
	 */
	public List<Booking> getConfirmedBookings() {
		if(null == confirmedBookings) {
			return new ArrayList<Booking>();
		}
		return confirmedBookings;
	}
	/**
	 * @param confirmedBookings the confirmedBookings to set
	 */
	public void setConfirmedBookings(List<Booking> confirmedBookings) {
		this.confirmedBookings = confirmedBookings;
	}
	

}
