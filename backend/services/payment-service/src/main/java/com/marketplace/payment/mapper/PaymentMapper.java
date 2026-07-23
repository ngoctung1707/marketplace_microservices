package com.marketplace.payment.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.marketplace.payment.dto.response.PaymentResponse;
import com.marketplace.payment.entity.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentResponse toResponse(Payment payment);

    List<PaymentResponse> toResponseList(List<Payment> payments);
}
