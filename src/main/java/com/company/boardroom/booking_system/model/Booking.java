package com.company.boardroom.booking_system.model;

import java.time.LocalDateTime;

/********************************************************************
 * The object which contains information about the booking request
 * per booking instance from the employee
 * 
 * @author Hari
 *
 ********************************************************************/
public class Booking {
	
	/**
	 * Unique Id to identify booking
	 */
	private int bookingId;
	
	/**
	 * The date of booking submission
	 */
	private LocalDateTime submissionDate;
	
	/**
	 * The name of employee
	 */
	private String employee;
	
	/**
	 * The start time of the desired booking
	 */
	private LocalDateTime bookedDateStart;
	
	/**
	 * The number of hours for the desired booking
	 */
	private long hours;
	
	/**
	 * The end time of the desired booking
	 */
	private LocalDateTime bookedDateEnd;
	
	
	/**
	 * @return the bookingId
	 */
	public int getBookingId() {
		return bookingId;
	}
	/**
	 * @param bookingId the bookingId to set
	 */
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	/**
	 * @return the submissionDate
	 */
	public LocalDateTime getSubmissionDate() {
		return submissionDate;
	}
	/**
	 * @param submissionDate the submissionDate to set
	 */
	public void setSubmissionDate(LocalDateTime submissionDate) {
		this.submissionDate = submissionDate;
	}
	/**
	 * @return the employee
	 */
	public String getEmployee() {
		return employee;
	}
	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	/**
	 * @return the bookedDateStart
	 */
	public LocalDateTime getBookedDateStart() {
		return bookedDateStart;
	}
	/**
	 * @param bookedDateStart the bookedDateStart to set
	 */
	public void setBookedDateStart(LocalDateTime bookedDateStart) {
		this.bookedDateStart = bookedDateStart;
	}
	
	/**
	 * @return the hours
	 */
	public long getHours() {
		return hours;
	}
	/**
	 * @param hours the hours to set
	 */
	public void setHours(long hours) {
		this.hours = hours;
	}
	/**
	 * @return the bookedDateEnd
	 */
	public LocalDateTime getBookedDateEnd() {
		return bookedDateEnd;
	}
	/**
	 * @param bookedDateEnd the bookedDateEnd to set
	 */
	public void setBookedDateEnd(LocalDateTime bookedDateEnd) {
		this.bookedDateEnd = bookedDateEnd;
	}
	
	

}
