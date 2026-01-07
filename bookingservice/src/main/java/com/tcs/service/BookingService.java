package com.tcs.service;

import java.time.LocalDate;
import java.util.List;

import com.tcs.entity.Booking;
import com.tcs.exception.BookingDeletedException;
import com.tcs.exception.BookingNotFoundException;
import com.tcs.exception.RoomNotFoundException;
import com.tcs.exception.UserNotFoundException;

public interface BookingService {

	public Booking bookRoom(Booking booking, Long userId) throws UserNotFoundException, RoomNotFoundException;
	public List<Booking> getAllBookings();
	public Booking getBookingById(Long bookingId) throws BookingNotFoundException, BookingDeletedException;
	public Booking updateBookingById(Long bookingId, Booking booking) throws BookingNotFoundException;
	public String deleteBookingById(Long bookingId) throws BookingNotFoundException;
	
//	public List<Booking> findByCustomerName(String customerName);
	public List<Booking> findByBookingType(String bookingType);
	public List<Booking> findByCheckInDate(LocalDate checkInDate);
	public List<Booking> findByCheckOutDate(LocalDate checkOutDate);
	public List<Booking> findByStatus(String status);
	
	
}