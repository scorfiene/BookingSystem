# BookingSystem
A simple system to process booking requests in form of text and represent successful booking via a calender

Welcome to the Boardroom Booking System. The purpose of this
application is to book the boardroom between working hours per 
day in specific durations as per requested by the employees.
The final approved schedule is displayed once the processing is 
complete.

INPUT
-----
For taking the input from employees, the requests from employees
are taken in the form of text input through a text file.
The first line of the input text represents the company
office hours, in 24-hour clock format, and the remainder 
of the input represents individual booking requests. 
Each booking request is in the following format.

[request submission time, in the format YYYY-MM-DD HH:MM:SS] [ARCH:employee id]
[meeting start time, in the format YYYY-MM-DD HH:MM] [ARCH:meeting duration in hours]

Example:

0900 1730
2017-03-17 10:17:06 EMP001
2017-03-21 09:00 2
2017-03-16 12:34:56 EMP002
2017-03-21 09:00 2
2017-03-16 09:28:23 EMP003
2017-03-22 14:00 2
2017-03-17 11:23:45 EMP004
2017-03-22 16:00 1
2017-03-15 17:29:12 EMP005
2017-03-21 16:00 3

The file textInput.txt which resides in /src/main/resources/ folder
contains the text for input.
This file is read, parsed and then processed for scheduling the
boardroom.

OUTPUT
------
The output provides a successful booking calendar as output, 
with bookings being grouped chronologically by day. 
For the sample input displayed above, the system provides the following
output:

2017-03-21
09:00 11:00 EMP002
2017-03-22
14:00 16:00 EMP003
16:00 17:00 EMP004

CONSTRAINTS
-----------
The constraints are:
No part of a meeting may fall outside office hours.
Meetings may not overlap.
The booking submission system only allows one submission at a time, so submission times are
guaranteed to be unique.
Bookings must be processed in the chronological order in which they were submitted.
The ordering of booking submissions in the supplied input is not guaranteed.

HOW TO RUN
-----------

The project has Junit test file called TestBookingManager.java
which has the test method testRun() which can be used to initiate the
processing of the request file textInput.txt.

Or execute
mvn clean package on the root folder to initiate the build with tests


PS: This is in the initual stage and there are lot of scope to 
add features like exception handling, customizing the input and output,
handling more files, having a message delivered for 
successful and unsuccessful bookings, etc.
