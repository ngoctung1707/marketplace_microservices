package com.marketplace.payment.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marketplace.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByOrderId(UUID orderId);

    Optional<Payment> findByPaymentCode(String paymentCode);

    Optional<Payment> findByTransactionId(String transactionId);

}
