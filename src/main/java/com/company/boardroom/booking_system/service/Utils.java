package com.company.boardroom.booking_system.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import com.company.boardroom.booking_system.model.TimeTable;

/********************************************************************
 * This class contains commonly used operations that assist in processing the
 * booking request. These include parsing text, date, etc. and also printing
 * 
 * @author Hari
 *
 ********************************************************************/
public class Utils {

	/**
	 * This method parses the string date to yyyy-MM-dd HH:mm:ss format
	 * 
	 * @param date
	 *            : String input
	 * @return LocalDateTime : parsed date with time
	 */
	public LocalDateTime parseDateWithSecond(String date) {
		DateTimeFormatter format = DateTimeFormatter
				.ofPattern(Constants.DATE_FORMAT_YYYYMMDDHHMMSS);
		LocalDateTime dateTime = LocalDateTime.parse(date, format);
		return dateTime;
	}

	/**
	 * This method parses the string date to yyyy-MM-dd HH:mm format
	 * 
	 * @param date
	 *            : String input
	 * @return LocalDateTime : parsed date with time
	 */
	public LocalDateTime parseDate(String date) {
		DateTimeFormatter format = DateTimeFormatter
				.ofPattern(Constants.DATE_FORMAT_YYYYMMDDHHMM);
		LocalDateTime dateTime = LocalDateTime.parse(date, format);
		return dateTime;
	}

	/**
	 * This method reads the text file from resources and parses the text line by
	 * line to form a list of String
	 * 
	 * @param date
	 *            : none
	 * @return List<String> : parsed lines
	 */
	public List<String> parseTextInput() {
		List<String> lines = new ArrayList<String>();

		try (BufferedReader br = Files
				.newBufferedReader(Paths.get(Constants.RESOURCE_PATH))) {
			lines = (ArrayList<String>) br.lines().collect(Collectors.toList());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return lines;
	}

	/**
	 * This method splits the line based on the delimiter into ArrayList of words
	 * 
	 * @param line
	 *            : the line to parse
	 * @return ArrayList<String> : the words listed after parsing
	 */
	public ArrayList<String> splitLine(String line) {
		StringTokenizer token = new StringTokenizer(line, Constants.SPACE);
		ArrayList<String> list = new ArrayList<String>(line.length());
		while (token.hasMoreElements()) {
			list.add(token.nextToken());
		}
		return list;
	}

	/**
	 * This method prints the timetable in desired format
	 * 
	 * @param timeTables
	 *            : The successfully booked timetable
	 * @return none
	 */
	public void print(List<TimeTable> timeTables) {
		timeTables.stream().forEach(tt -> {
			System.out.println(tt.getDay());
			tt.getConfirmedBookings().forEach(cb -> {
				System.out.print(cb.getBookedDateStart().toLocalTime());
				System.out.print(Constants.SPACE);
				System.out.print(cb.getBookedDateEnd().toLocalTime());
				System.out.print(Constants.SPACE);
				System.out.println(cb.getEmployee());
			});
		});
	}

}
