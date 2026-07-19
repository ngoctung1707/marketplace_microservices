package com.marketplace.order.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.marketplace.order.dto.response.OrderItemResponse;
import com.marketplace.order.dto.response.OrderResponse;
import com.marketplace.order.entity.Order;
import com.marketplace.order.entity.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    /**
     * Order -> Response
     */
    @Mapping(target = "items", source = "orderItems")
    OrderResponse toResponse(Order order);

    List<OrderResponse> toResponseList(List<Order> orders);

    /**
     * OrderItem -> Response
     */
    OrderItemResponse toItemResponse(OrderItem orderItem);

    List<OrderItemResponse> toItemResponseList(List<OrderItem> orderItems);

}
