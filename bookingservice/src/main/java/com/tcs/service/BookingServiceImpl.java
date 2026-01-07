package com.tcs.service;

import com.tcs.client.RoomClient;
import com.tcs.client.UserClient;
import com.tcs.dto.RoomResponse;
import com.tcs.dto.UserResponse;
import com.tcs.entity.Booking;
import com.tcs.exception.BookingDeletedException;
import com.tcs.exception.BookingNotFoundException;
import com.tcs.exception.RoomNotFoundException;
import com.tcs.exception.UserNotFoundException;
import com.tcs.repository.BookingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
    private BookingRepository bookingRepository;

	@Autowired
	private UserClient userClient;
	
	@Autowired
	private RoomClient roomClient;
	
    // Book a room
    @Override
    public Booking bookRoom(Booking booking, Long userId) throws UserNotFoundException, RoomNotFoundException{
    	
    	// Fetch user details using Feign Client
        UserResponse user = userClient.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        
        // Fetch room availability using Feign Client
        RoomResponse room = roomClient.getRoomById(booking.getRoomId());
        if (room == null) {
            throw new RoomNotFoundException("Room not found with ID: " + booking.getRoomId());
        }
        
        
        // Check room availability between check-in and check-out dates
        if (!isRoomAvailable(room, booking.getCheckInDate(), booking.getCheckOutDate())) {
            throw new RoomNotFoundException("Room is not available between " + booking.getCheckInDate() + " and " + booking.getCheckOutDate());
        }

        

        // Set additional booking information
        booking.setCustomerName(booking.getCustomerName());  
        booking.setEmail(booking.getEmail());  
        booking.setRoomId(booking.getRoomId());
        booking.setCheckInDate(booking.getCheckInDate());
        booking.setCheckOutDate(booking.getCheckOutDate());
        booking.setBookingType(booking.getBookingType());
        // Calculate fare based on room type and booking type (add more logic as per the business rules)
        double fare = calculateFare(room, booking);
        booking.setFare(fare);  // Set the calculated fare
        booking.setStatus("BOOKED");
        booking.setIsDeleted(false);
        booking.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));  // Set current date and time

        // Save the booking in the database
        return bookingRepository.save(booking);
    }

    // Method to check if the room is available in the given date range
    private boolean isRoomAvailable(RoomResponse room,
            LocalDate checkInDate,
            LocalDate checkOutDate) {
    	// Implement logic to check if the room is available for the given date range
        // For example, you can query the database or use any other service to validate availability
    	int overlappingBookings =
    				bookingRepository.countByRoomIdAndCheckInDateBeforeAndCheckOutDateAfter(
    						room.getRoomId(), checkOutDate, checkInDate
    						);

    	// If there are no overlapping bookings, the room is available
    	return overlappingBookings < room.getAvaiableRooms(); // 20
    }


    // Method to calculate the fare (business logic can be extended here)
    private double calculateFare(RoomResponse room, Booking booking) {
        // Simple fare calculation based on room type and booking type
        double baseFare = room.getPrice();
        double bookingTypeMultiplier = booking.getBookingType().equals("Premium") ? 1.2 : 1.0;
        return baseFare * bookingTypeMultiplier;
    }
    

    // Get all bookings
    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Get booking by ID
    @Override
    public Booking getBookingById(Long bookingId) throws BookingNotFoundException, BookingDeletedException {
    	
    	Booking booking = bookingRepository.findById(bookingId)
        		.orElseThrow(()-> new BookingNotFoundException("Booking with ID " + bookingId + " not found"));
        if(!booking.getIsDeleted())
        	return booking;
        else
        	throw new BookingDeletedException("Booking already cancelled");
        
    }

    // Update booking by ID
    @Override
    public Booking updateBookingById(Long bookingId, Booking booking) throws BookingNotFoundException {
        Booking existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new BookingNotFoundException("Booking with ID " + bookingId + " not found"));

        // Update fields
        existingBooking.setCustomerName(booking.getCustomerName());
        existingBooking.setEmail(booking.getEmail());
        existingBooking.setRoomId(booking.getRoomId());
        existingBooking.setBookingType(booking.getBookingType());
        existingBooking.setCheckInDate(booking.getCheckInDate());
        existingBooking.setCheckOutDate(booking.getCheckOutDate());
        RoomResponse room = roomClient.getRoomById(booking.getRoomId());
        double fare = calculateFare(room, booking);
        existingBooking.setFare(fare);
        existingBooking.setStatus("BOOKED");
        existingBooking.setIsDeleted(false);
        existingBooking.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        return bookingRepository.save(existingBooking);
    }

    // Delete booking by ID
    @Override
    public String deleteBookingById(Long bookingId) throws BookingNotFoundException {
       
        Booking booking = bookingRepository.findById(bookingId)
        		.orElseThrow(()-> new BookingNotFoundException("Booking with ID " + bookingId + " not found"));
        
        booking.setIsDeleted(true);
        return "Booking with ID " + bookingId + " has been deleted successfully.";
    }

    // Find bookings by customer name
//    @Override
//    public List<Booking> findByCustomerName(String customerName) {
//        return bookingRepository.findByCustomerName(customerName);
//    }

    // Find bookings by booking type
    @Override
    public List<Booking> findByBookingType(String bookingType) {
        return bookingRepository.findByBookingType(bookingType);
    }

    // Find bookings by check-in date
    @Override
    public List<Booking> findByCheckInDate(LocalDate checkInDate) {
        return bookingRepository.findByCheckInDate(checkInDate);
    }

    // Find bookings by check-out date
    @Override
    public List<Booking> findByCheckOutDate(LocalDate checkOutDate) {
        return bookingRepository.findByCheckOutDate(checkOutDate);
    }

    // Find bookings by status
    @Override
    public List<Booking> findByStatus(String status) {
        return bookingRepository.findByStatus(status);
    }

	
}