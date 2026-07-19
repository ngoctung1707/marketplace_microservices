package com.marketplace.order.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.order.client.product.ProductClientService;
import com.marketplace.order.client.product.dto.InternalProductResponse;
import com.marketplace.order.client.product.dto.ProductStatus;
import com.marketplace.order.dto.request.CreateOrderItemRequest;
import com.marketplace.order.dto.request.CreateOrderRequest;
import com.marketplace.order.dto.response.OrderResponse;
import com.marketplace.order.entity.Order;
import com.marketplace.order.entity.OrderItem;
import com.marketplace.order.entity.OrderStatus;
import com.marketplace.order.entity.PaymentStatus;
import com.marketplace.order.exception.InvalidOrderException;
import com.marketplace.order.mapper.OrderMapper;
import com.marketplace.order.repository.OrderItemRepository;
import com.marketplace.order.repository.OrderRepository;
import com.marketplace.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final ProductClientService productClientService;

    private String generateOrderCode() {

        String date = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String suffix = String.valueOf(System.currentTimeMillis());

        suffix = suffix.substring(suffix.length() - 6);

        return "ORD-" + date + "-" + suffix;
    }

    private Order buildOrder(
            CreateOrderRequest request,
            String orderCode) {

        return Order.builder()
                .userId(request.getUserId())
                .orderCode(orderCode)

                .receiverName(request.getReceiverName())
                .receiverPhone(request.getReceiverPhone())
                .shippingAddress(request.getShippingAddress())

                .status(OrderStatus.PENDING)
                .paymentStatus(PaymentStatus.UNPAID)

                .subtotal(BigDecimal.ZERO)
                .shippingFee(BigDecimal.ZERO)
                .discount(BigDecimal.ZERO)
                .totalAmount(BigDecimal.ZERO)

                .active(true)
                .build();
    }

    private List<OrderItem> buildOrderItems(
            Order order,
            CreateOrderRequest request) {

        List<OrderItem> orderItems = new ArrayList<>();

        for (CreateOrderItemRequest itemRequest : request.getItems()) {

            OrderItem orderItem = buildOrderItem(
                    order,
                    itemRequest);

            orderItems.add(orderItem);
        }

        return orderItems;
    }

    private OrderItem buildOrderItem(
            Order order,
            CreateOrderItemRequest request) {

        InternalProductResponse product = productClientService.getProduct(request.getProductId());

        if (!Boolean.TRUE.equals(product.getActive())) {
            throw new InvalidOrderException(
                    "Product is inactive: " + product.getName());
        }

        if (product.getStatus() != ProductStatus.ACTIVE) {
            throw new InvalidOrderException(
                    "Product is not available: " + product.getName());
        }

        BigDecimal unitPrice = product.getDiscountPrice() != null
                ? product.getDiscountPrice()
                : product.getPrice();

        BigDecimal subtotal = unitPrice.multiply(
                BigDecimal.valueOf(request.getQuantity()));

        return OrderItem.builder()
                .order(order)

                .productId(product.getId())
                .shopId(product.getShopId())

                .productName(product.getName())
                .thumbnail(product.getThumbnail())

                .unitPrice(unitPrice)

                .quantity(request.getQuantity())

                .subtotal(subtotal)

                .build();
    }

    private void calculateOrderAmount(Order order) {

        BigDecimal subtotal = order.getOrderItems()
                .stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal shippingFee = order.getShippingFee();

        BigDecimal discount = order.getDiscount();

        BigDecimal totalAmount = subtotal
                .add(shippingFee)
                .subtract(discount);

        order.setSubtotal(subtotal);
        order.setTotalAmount(totalAmount);

    }

    private OrderResponse buildResponse(Order order) {

        return orderMapper.toResponse(order);

    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {

        String orderCode = generateOrderCode();

        Order order = buildOrder(request, orderCode);

        List<OrderItem> orderItems = buildOrderItems(order, request);

        order.setOrderItems(orderItems);

        calculateOrderAmount(order);

        // Lưu Order
        Order savedOrder = orderRepository.saveAndFlush(order);

        System.out.println("===== AFTER SAVE =====");
        System.out.println("Entity createdAt = " + savedOrder.getCreatedAt());
        System.out.println("Entity updatedAt = " + savedOrder.getUpdatedAt());

        // Đọc lại từ database
        savedOrder = orderRepository.findById(savedOrder.getId())
                .orElseThrow();

        System.out.println("===== AFTER FIND =====");
        System.out.println("Entity createdAt = " + savedOrder.getCreatedAt());
        System.out.println("Entity updatedAt = " + savedOrder.getUpdatedAt());

        // Map sang Response
        OrderResponse response = buildResponse(savedOrder);

        System.out.println("===== RESPONSE =====");
        System.out.println("Response createdAt = " + response.getCreatedAt());
        System.out.println("Response updatedAt = " + response.getUpdatedAt());

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrder(UUID orderId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByUser(UUID userId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void cancelOrder(UUID orderId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}