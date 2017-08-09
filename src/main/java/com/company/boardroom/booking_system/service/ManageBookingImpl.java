package com.company.boardroom.booking_system.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.company.boardroom.booking_system.model.Booking;
import com.company.boardroom.booking_system.model.BookingRequest;
import com.company.boardroom.booking_system.model.TimeTable;

/********************************************************************
 * This class provides the basic logical representation of the booking system.
 * The method executeBooking() initiates the processing of the booking request by first
 * reading the input text from a file, then invoking other methods to process
 * the booking requests and finally display the calendar of successful bookings
 * 
 * @author Hari
 *
 ********************************************************************/
public class ManageBookingImpl implements ManageBooking {

	/**
	 * This method initiates the booking processing by invoking other methods.
	 * Following are the steps involved: - read text input from file - parse into
	 * Booking objects - process the Booking objects to form TimeTable objects -
	 * print the TimeTable
	 * 
	 * @param none
	 * @return none
	 */
	@Override
	public void executeBooking() {
		Utils utils = new Utils();
		List<String> lines = utils.parseTextInput();
		BookingRequest request = getBookings(lines);
		List<TimeTable> timeTables = processBooking(request);
		utils.print(timeTables);
	}

	/**
	 * This method is used to form the BookingRequest object containing the list of
	 * input bookings.
	 * 
	 * @param List<String>
	 *            : the parsed lines from the text input
	 * @return BookingRequest : The object containing the booking requests
	 */
	private BookingRequest getBookings(List<String> lines) {
		BookingRequest request = new BookingRequest();
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		String startOfDay = null;
		String endOfDay = null;
		/*
		 * Getting the first line to save the start and end
		 */
		String firstLine = (String) lines.stream().findFirst().get();
		Utils utils = new Utils();

		ArrayList<String> firstLineList = utils.splitLine(firstLine);
		startOfDay = firstLineList.get(0);
		endOfDay = firstLineList.get(1);

		/*
		 * iterating through rest of the lines to form the individual parameters.
		 */
		for (int i = 1; i < lines.size(); i++) {
			Booking booking = new Booking();
			ArrayList<String> eachLine = utils.splitLine(lines.get(i));
			i++;
			eachLine.addAll(utils.splitLine(lines.get(i)));

			booking.setBookingId(i);
			String submissionDate = String
					.join(Constants.SPACE, eachLine.get(0), eachLine.get(1));
			booking.setSubmissionDate(utils.parseDateWithSecond(submissionDate));
			booking.setEmployee(eachLine.get(2));
			String bookedDateStart = String
					.join(Constants.SPACE, eachLine.get(3), eachLine.get(4));
			booking.setBookedDateStart(utils.parseDate(bookedDateStart));
			booking.setHours(new Long(eachLine.get(5)));
			booking.setBookedDateEnd(booking.getBookedDateStart()
					.plusHours(booking.getHours()));

			bookings.add(booking);
		}

		request.setBookings(bookings);
		request.setStart(startOfDay);
		request.setEnd(endOfDay);
		return request;
	}

	/**
	 * This method processes the booking request elements in the BookingRequest
	 * object to form a list of TimeTable objects. First the booking request is
	 * sorted based on submissionDate. Then one by one, the booking is checked
	 * whether a valid booking, and correspondingly the TimeTable is set. If the
	 * booking request is found to be invalid, then the booking is ignored.
	 * 
	 * @param request
	 *            : The booking request object with the bookings
	 * @return List<TimeTable> : The list of TimeTable object with the successful
	 *         bookings
	 */
	private List<TimeTable> processBooking(BookingRequest request) {

		List<TimeTable> timeTables = new ArrayList<TimeTable>();
		List<Booking> bookings = request.getBookings();
		final String start = request.getStart();
		final String end = request.getEnd();
		/*
		 * Map to store each booking corresponding to the booking Id
		 */
		final Map<Integer, Booking> bookingMap = new HashMap<Integer, Booking>();
		/*
		 * Map to store the submission dates of booking request based on Id
		 */
		Map<Integer, LocalDateTime> submissionMap = new HashMap<Integer, LocalDateTime>();

		for (Booking booking : bookings) {
			bookingMap.put(booking.getBookingId(), booking);
			submissionMap.put(booking.getBookingId(), booking.getSubmissionDate());
		}

		/*
		 * Sort the submission dates in ascending order
		 */
		Map<Integer, LocalDateTime> sortedSubmissionMap = submissionMap.entrySet().stream()
				.sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
						(oldValue, newValue) -> oldValue, LinkedHashMap::new));

		/*
		 * iterating through each booking in the order of submission and processing
		 */
		for (Map.Entry<Integer, LocalDateTime> entry : sortedSubmissionMap.entrySet()) {
			TimeTable tt = null;
			/*
			 * retrieving the booking corresponding to the current submission
			 */
			Booking booking = (Booking) bookingMap.get(entry.getKey());
			LocalDateTime bookingStart = booking.getBookedDateStart();
			LocalDateTime bookingEnd = bookingStart.plusHours(booking.getHours());
			LocalDateTime startOfDay = bookingStart
					.withHour(new Integer(start.substring(0, 2)))
					.withMinute(new Integer(start.substring(2, 4)));
			LocalDateTime endOfDay = bookingStart
					.withHour(new Integer(end.substring(0, 2)))
					.withMinute(new Integer(end.substring(2, 4)));

			/*
			 * First checking if the timetable object for the desired day is already
			 * created. If present, use that object to add the current booking
			 */
			if (null != timeTables && !timeTables.isEmpty()) {
				// tt = timeTables.stream().filter(t ->
				// t.getDay().isEqual(bookingStart.toLocalDate())).findAny().get();
				for (TimeTable t : timeTables) {
					if (t.getDay().isEqual(bookingStart.toLocalDate())) {
						tt = t;
						break;
					}
				}
			}

			/*
			 * If the desired day has already a timetable created, then first check whether
			 * the current booking is valid and if yes, add it to the confirmed bookings If
			 * otherwise, a new timetable is created for the day and the booking is added to
			 * the confirmed bookings only if it is valid
			 */
			if (null != tt) {
				if (checkValidBooking(booking, tt)) {
					tt.getConfirmedBookings().add(booking);
				}
			} else {
				tt = new TimeTable();
				tt.setDay(bookingStart.toLocalDate());
				tt.setStartOfDay(startOfDay);
				tt.setEndOfDay(endOfDay);
				if (!bookingStart.isBefore(startOfDay) && !bookingStart.isAfter(endOfDay)
						&& !bookingEnd.isBefore(startOfDay) && !bookingEnd.isAfter(endOfDay)
						&& !bookingEnd.isBefore(bookingStart)) {
					List<Booking> confirmedBookings = tt.getConfirmedBookings();
					confirmedBookings.add(booking);
					tt.setConfirmedBookings(confirmedBookings);
					timeTables.add(tt);
				}
			}
		}

		/*
		 * Finally, sort the timetable based on day
		 */
		timeTables.sort((TimeTable t1, TimeTable t2) -> t1.getDay().compareTo(t2.getDay()));

		return timeTables;
	}

	/**
	 * This method checks for a given booking whether it is valid for the given
	 * timetable which may already have other bookings for the same day. A true
	 * value is returned only if it is found to be a valid booking request, else,
	 * false is returned. Following conditions are checked: 
	 * - start of booking should not be before start of day 
	 * - start of booking should not be after end of day 
	 * - end of booking should not be before start of day 
	 * - end of booking should not be after end of day 
	 * - end of booking should not be before start of booking 
	 * - start of booking should not be at the start of a previous valid booking 
	 * - end of booking should not be at the end of a previous valid booking
	 * - start of booking should not lie in between start and end of a previous valid booking 
	 * - end of booking should not lie in between start and end of a previous valid booking
	 * 
	 * @param booking
	 *            : The booking request
	 * @param tt
	 *            : The TimeTable for desired day
	 * @return boolean : true if valid, false if invalid
	 */
	private boolean checkValidBooking(Booking booking, TimeTable tt) {

		List<Booking> confirmedBookings = tt.getConfirmedBookings();
		if (booking.getBookedDateStart().isBefore(tt.getStartOfDay())) {
			return false;
		} else if (booking.getBookedDateStart().isAfter(tt.getEndOfDay())) {
			return false;
		} else if (booking.getBookedDateEnd().isBefore(tt.getStartOfDay())) {
			return false;
		} else if (booking.getBookedDateEnd().isAfter(tt.getEndOfDay())) {
			return false;
		} else if (booking.getBookedDateStart().isAfter(booking.getBookedDateEnd())) {
			return false;
		} else if (confirmedBookings.stream()
				.filter(cb -> (booking.getBookedDateStart().isEqual(cb.getBookedDateStart()))
						|| (booking.getBookedDateEnd().isEqual(cb.getBookedDateEnd()))
						|| (booking.getBookedDateStart().isAfter(cb.getBookedDateStart())
								&& booking.getBookedDateStart().isBefore(cb.getBookedDateEnd()))
						|| (booking.getBookedDateEnd().isAfter(cb.getBookedDateStart())
								&& booking.getBookedDateEnd().isBefore(cb.getBookedDateEnd())))
				.findAny().isPresent()) {
			return false;
		} else {
			return true;
		}
	}

}
