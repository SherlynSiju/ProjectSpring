package com.tcs.service;

import com.tcs.entity.Room;
import com.tcs.repository.RoomRepository;
import com.tcs.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // Get all rooms
    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Get room by ID
    @Override
    public Room getRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(()-> new RuntimeException("Room with ID " + roomId + " not found"));
        
        return room;
    }

    // Update room by ID
    @Override
    public Room updateRoomById(Long roomId, Room room) {
        Room existingRoom = roomRepository.findById(roomId).orElseThrow(()-> new RuntimeException("Room with ID " + roomId + " not found"));
        
        
        // Update properties
        existingRoom.setRoomType(room.getRoomType());
        existingRoom.setPrice(room.getPrice());
        existingRoom.setAvaiableRooms(room.getAvaiableRooms());
        
        // Save the updated room
        return roomRepository.save(existingRoom);
    }

    // Delete room by ID
    @Override
    public String deleteRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(()-> new RuntimeException("Room with ID " + roomId + " not found"));
        
        
        // Delete the room
        roomRepository.deleteById(roomId);
        return "Room with ID " + roomId + " has been deleted successfully.";
    }

    // Find rooms by room type
    @Override
    public List<Room> findByRoomType(String roomType) {
        return roomRepository.findByRoomType(roomType);
    }
}