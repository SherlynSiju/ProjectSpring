package com.tcs.service;

import java.util.List;

import com.tcs.entity.Room;

public interface RoomService {

	public List<Room> getAllRooms();
	public Room getRoomById(Long roomId);
	public Room updateRoomById(Long roomId, Room room);
	public String deleteRoomById(Long id);
	
	public List<Room> findByRoomType(String roomType);
}