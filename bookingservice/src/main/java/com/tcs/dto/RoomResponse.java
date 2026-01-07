package com.tcs.dto;

public class RoomResponse {

	private Long roomId;
	private String roomType;
	private Double price;
	private int avaiableRooms;
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