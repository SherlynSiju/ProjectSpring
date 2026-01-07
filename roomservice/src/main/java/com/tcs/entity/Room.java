package com.tcs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roomId;
	private String roomType;
	private Double price;
	private int avaiableRooms;
	public Room() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Room(String roomType, Double price, int avaiableRooms) {
		super();
		this.roomType = roomType;
		this.price = price;
		this.avaiableRooms = avaiableRooms;
	}
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public int getAvaiableRooms() {
		return avaiableRooms;
	}
	public void setAvaiableRooms(int avaiableRooms) {
		this.avaiableRooms = avaiableRooms;
	}
	
	
}