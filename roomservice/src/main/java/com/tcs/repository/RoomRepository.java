package com.tcs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

	public List<Room> findByRoomType(String roomType);
}