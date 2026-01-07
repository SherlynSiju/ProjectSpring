package com.tcs.controller;

import com.tcs.entity.Booking;
import com.tcs.exception.BookingDeletedException;
import com.tcs.exception.BookingNotFoundException;
import com.tcs.exception.RoomNotFoundException;
import com.tcs.exception.UserNotFoundException;
import com.tcs.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	@Autowired
    BookingService bookingService;


    // Book a room
    @PostMapping("/user/{userId}")
    public ResponseEntity<Booking> bookRoom(
            @RequestBody Booking booking,
            @PathVariable Long userId) throws UserNotFoundException, RoomNotFoundException {

        Booking createdBooking = bookingService.bookRoom(booking, userId);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    // Get all bookings
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    // Get booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id)
            throws BookingNotFoundException, BookingDeletedException {

        Booking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    // Update booking by ID
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(
            @PathVariable Long id,
            @RequestBody Booking booking) throws BookingNotFoundException {

        Booking updatedBooking = bookingService.updateBookingById(id, booking);
        return ResponseEntity.ok(updatedBooking);
    }

    // Delete booking by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id)
            throws BookingNotFoundException {

        String message = bookingService.deleteBookingById(id);
        return ResponseEntity.ok(message);
    }

    // Find bookings by booking type
    @GetMapping("/type/{bookingType}")
    public ResponseEntity<List<Booking>> getBookingsByType(@PathVariable String bookingType) {
        List<Booking> bookings = bookingService.findByBookingType(bookingType);
        return ResponseEntity.ok(bookings);
    }

    // Find bookings by check-in date
    @GetMapping("/checkin/{date}")
    public ResponseEntity<List<Booking>> getBookingsByCheckInDate(@PathVariable String date) {
        LocalDate checkInDate = LocalDate.parse(date);
        List<Booking> bookings = bookingService.findByCheckInDate(checkInDate);
        return ResponseEntity.ok(bookings);
    }

    // Find bookings by check-out date
    @GetMapping("/checkout/{date}")
    public ResponseEntity<List<Booking>> getBookingsByCheckOutDate(@PathVariable String date) {
        LocalDate checkOutDate = LocalDate.parse(date);
        List<Booking> bookings = bookingService.findByCheckOutDate(checkOutDate);
        return ResponseEntity.ok(bookings);
    }

    // Find bookings by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Booking>> getBookingsByStatus(@PathVariable String status) {
        List<Booking> bookings = bookingService.findByStatus(status);
        return ResponseEntity.ok(bookings);
    }
}