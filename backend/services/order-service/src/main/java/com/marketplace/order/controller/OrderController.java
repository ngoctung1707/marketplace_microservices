package com.marketplace.order.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.order.dto.request.CreateOrderRequest;
import com.marketplace.order.dto.response.OrderResponse;
import com.marketplace.order.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        return orderService.createOrder(request);
    }

}