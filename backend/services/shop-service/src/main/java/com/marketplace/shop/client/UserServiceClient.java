package com.marketplace.shop.client;

import com.marketplace.shop.client.dto.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/api/v1/users/{authUserId}")
    UserProfileResponse getUserByAuthId(
            @PathVariable("authUserId") UUID authUserId);

}
