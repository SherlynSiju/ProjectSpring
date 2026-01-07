package com.tcs.client;

import com.tcs.config.FeignClientConfig;
import com.tcs.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "user-service",
    contextId = "userServiceClient",
    url = "http://localhost:9090",
   configuration = FeignClientConfig.class   
)
public interface UserClient {

    @GetMapping("/api/user/{id}")
    UserResponse getUserById(@PathVariable("id") Long id);
}