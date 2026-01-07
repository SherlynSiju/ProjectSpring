package com.tcs.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	public List<Booking> findByCustomerName(String customerName);
	public List<Booking> findByBookingType(String bookingType);
	public List<Booking> findByCheckInDate(LocalDate checkInDate);
	public List<Booking> findByCheckOutDate(LocalDate checkOutDate);
	public List<Booking> findByStatus(String status);
	
	public int countByRoomIdAndCheckInDateBeforeAndCheckOutDateAfter(Long roomId, LocalDate checkOutDate, LocalDate checkInDate);

}