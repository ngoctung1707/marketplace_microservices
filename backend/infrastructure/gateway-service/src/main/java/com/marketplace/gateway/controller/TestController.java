package com.marketplace.gateway.controller;

import com.marketplace.gateway.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final JwtService jwtService;

    @GetMapping("/test")
    public Map<String, String> test(
            @RequestHeader("Authorization") String authorizationHeader
    ) {

        String token = authorizationHeader.substring(7);

        Map<String, String> result = new HashMap<>();

        result.put("email", jwtService.extractUsername(token));
        result.put("userId", jwtService.extractUserId(token));
        result.put("role", jwtService.extractRole(token));

        return result;
    }
}