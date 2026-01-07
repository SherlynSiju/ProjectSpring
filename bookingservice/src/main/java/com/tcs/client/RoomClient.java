package com.tcs.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcs.dto.RoomResponse;

@FeignClient(
    name = "room-service",
    contextId = "roomServiceClient",
    url = "http://localhost:9091"
)
public interface RoomClient {

    @GetMapping("/api/rooms/{id}")
    RoomResponse getRoomById(@PathVariable Long id);
}