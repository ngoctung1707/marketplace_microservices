
package com.marketplace.order.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Order không tồn tại
     */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFound(
            OrderNotFoundException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildErrorResponse(
                        HttpStatus.NOT_FOUND,
                        ex.getMessage(),
                        request.getRequestURI()));
    }

    /**
     * Đơn hàng đã bị hủy
     */
    @ExceptionHandler(OrderAlreadyCancelledException.class)
    public ResponseEntity<ErrorResponse> handleOrderCancelled(
            OrderAlreadyCancelledException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        ex.getMessage(),
                        request.getRequestURI()));
    }

    /**
     * Dữ liệu đơn hàng không hợp lệ
     */
    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOrder(
            InvalidOrderException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        ex.getMessage(),
                        request.getRequestURI()));
    }

    /**
     * Validation (@Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest()
                .body(buildErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        message,
                        request.getRequestURI()));
    }

    /**
     * Các lỗi chưa xử lý
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex,
            HttpServletRequest request) {

        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ex.getMessage(),
                        request.getRequestURI()));
    }

    /**
     * Hàm tạo ErrorResponse dùng chung
     */
    private ErrorResponse buildErrorResponse(
            HttpStatus status,
            String message,
            String path) {

        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .build();
    }
}
