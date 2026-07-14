package com.marketplace.auth.client;

import com.marketplace.auth.client.dto.CreateUserProfileRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping("/api/v1/users")
    void createProfile(@RequestBody CreateUserProfileRequest request);

}