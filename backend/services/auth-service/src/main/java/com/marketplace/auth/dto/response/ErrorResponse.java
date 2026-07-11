package com.marketplace.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.Map;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;

    private String path;

    private Map<String, String> errors;
}