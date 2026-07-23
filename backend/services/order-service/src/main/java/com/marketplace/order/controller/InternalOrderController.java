package com.marketplace.order.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.order.dto.internal.UpdatePaymentStatusRequest;
import com.marketplace.order.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/internal/orders")
@RequiredArgsConstructor
@Validated
public class InternalOrderController {
    private final OrderService orderService;

    @PutMapping("/{orderId}/payment-status")
    public ResponseEntity<Void> updatePaymentStatus(@PathVariable("orderId") UUID orderId,
            @Valid @RequestBody UpdatePaymentStatusRequest request) {

        orderService.updatePaymentStatus(orderId, request);

        return ResponseEntity.ok().build();

    }
}
