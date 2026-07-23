package com.marketplace.payment.service;

import java.util.List;
import java.util.UUID;

import com.marketplace.payment.dto.request.CreatePaymentRequest;
import com.marketplace.payment.dto.response.PaymentResponse;
import com.marketplace.payment.dto.response.VNPayPaymentResponse;

public interface PaymentService {

    /**
     * Tạo payment
     */
    PaymentResponse createPayment(CreatePaymentRequest request);

    /**
     * Lấy payment theo ID
     */
    PaymentResponse getPayment(UUID paymentId);

    /**
     * Lấy payment theo Order
     */
    PaymentResponse getPaymentByOrder(UUID orderId);

    /**
     * Lấy toàn bộ payment
     */
    List<PaymentResponse> getAllPayments();

    /**
     * Sinh URL thanh toán VNPay
     */
    VNPayPaymentResponse createVNPayPayment(UUID paymentId);

    /**
     * Thanh toán thành công
     */
    PaymentResponse paymentSuccess(
            UUID paymentId,
            String transactionId,
            String gatewayResponse);

    /**
     * Thanh toán thất bại
     */
    PaymentResponse paymentFailed(
            UUID paymentId,
            String gatewayResponse);

    PaymentResponse paymentSuccessByCode(
            String paymentCode,
            String transactionId,
            String gatewayResponse);

    PaymentResponse paymentFailedByCode(
            String paymentCode,
            String gatewayResponse);

}
