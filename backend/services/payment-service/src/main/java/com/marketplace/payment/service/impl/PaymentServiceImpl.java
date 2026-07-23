package com.marketplace.payment.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.payment.client.order.OrderClientService;
import com.marketplace.payment.client.order.dto.OrderStatus;
import com.marketplace.payment.client.order.dto.request.UpdatePaymentStatusRequest;
import com.marketplace.payment.client.order.dto.response.OrderResponse;
import com.marketplace.payment.config.VNPayConfig;
import com.marketplace.payment.dto.request.CreatePaymentRequest;
import com.marketplace.payment.dto.response.PaymentResponse;
import com.marketplace.payment.dto.response.VNPayPaymentResponse;
import com.marketplace.payment.entity.Payment;
import com.marketplace.payment.entity.PaymentStatus;
import com.marketplace.payment.exception.InvalidPaymentException;
import com.marketplace.payment.exception.ResourceNotFoundException;
import com.marketplace.payment.mapper.PaymentMapper;
import com.marketplace.payment.repository.PaymentRepository;
import com.marketplace.payment.service.PaymentService;
import com.marketplace.payment.util.VNPayUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderClientService orderClientService;
    private final VNPayConfig vnPayConfig;

    @Override
    public PaymentResponse createPayment(CreatePaymentRequest request) {

        validateCreatePaymentRequest(request);

        OrderResponse order = orderClientService.getOrder(request.getOrderId());

        validateOrder(order);

        String paymentCode = generatePaymentCode();

        Payment payment = buildPayment(request, order, paymentCode);
        Payment savedPayment = savePayment(payment);

        return buildResponse(savedPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPayment(UUID paymentId) {

        Payment payment = findPaymentById(paymentId);

        return buildResponse(payment);

    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByOrder(UUID orderId) {

        Payment payment = paymentRepository.findByOrderId(orderId)

                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for order: " + orderId));

        return buildResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getAllPayments() {

        return paymentMapper.toResponseList(paymentRepository.findAll());

    }

    @Override
    public PaymentResponse paymentSuccess(
            UUID paymentId,
            String transactionId,
            String gatewayResponse) {

        Payment payment = findPaymentById(paymentId);

        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            return paymentMapper.toResponse(payment);
        }

        payment.setStatus(PaymentStatus.SUCCESS);

        payment.setTransactionId(transactionId);

        payment.setGatewayResponse(gatewayResponse);

        payment.setPaidAt(LocalDateTime.now());

        Payment savedPayment = paymentRepository.saveAndFlush(payment);

        updateOrderPaymentStatus(
                payment.getOrderId(),
                com.marketplace.payment.client.order.dto.PaymentStatus.PAID,
                com.marketplace.payment.client.order.dto.OrderStatus.CONFIRMED);

        return paymentMapper.toResponse(savedPayment);

    }

    @Override
    public PaymentResponse paymentFailed(
            UUID paymentId,
            String gatewayResponse) {

        Payment payment = findPaymentById(paymentId);

        if (payment.getStatus() == PaymentStatus.FAILED) {
            return paymentMapper.toResponse(payment);
        }

        payment.setStatus(PaymentStatus.FAILED);

        payment.setGatewayResponse(gatewayResponse);

        Payment savedPayment = paymentRepository.saveAndFlush(payment);

        updateOrderPaymentStatus(
                payment.getOrderId(),
                com.marketplace.payment.client.order.dto.PaymentStatus.UNPAID,
                com.marketplace.payment.client.order.dto.OrderStatus.CANCELLED);

        return paymentMapper.toResponse(savedPayment);
    }

    @Override
    public VNPayPaymentResponse createVNPayPayment(
            UUID paymentId) {

        Payment payment = findPaymentById(paymentId);

        Map<String, String> params = new HashMap<>();

        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");

        params.put("vnp_TmnCode",
                vnPayConfig.getTmnCode());

        params.put("vnp_Amount",
                payment.getAmount()
                        .multiply(BigDecimal.valueOf(100))
                        .toBigInteger()
                        .toString());

        params.put("vnp_CurrCode", "VND");

        params.put("vnp_TxnRef",
                payment.getPaymentCode());

        params.put("vnp_OrderInfo",
                "Thanh toan don hang "
                        + payment.getPaymentCode());

        params.put("vnp_OrderType", "other");

        params.put("vnp_Locale", "vn");

        params.put("vnp_ReturnUrl",
                vnPayConfig.getReturnUrl());

        params.put("vnp_IpAddr",
                "127.0.0.1");

        params.put(
                "vnp_CreateDate",
                LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))
                        .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

        String queryString = VNPayUtil.buildQueryString(params);

        String secureHash = VNPayUtil.buildSecureHash(
                vnPayConfig.getHashSecret(),
                params);

        String paymentUrl = vnPayConfig.getPayUrl()
                + "?"
                + queryString
                + "&vnp_SecureHash="
                + secureHash;

        return VNPayPaymentResponse.builder()
                .paymentUrl(paymentUrl)
                .build();

    }

    private void validateCreatePaymentRequest(CreatePaymentRequest request) {

        paymentRepository.findByOrderId(request.getOrderId()).ifPresent(payment -> {

            throw new InvalidPaymentException("Payment already exists for order: " + request.getOrderId());

        });
    }

    private void validateOrder(OrderResponse order) {

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new InvalidPaymentException("Order has been cancelled.");
        }

        if (order.getPaymentStatus() == com.marketplace.payment.client.order.dto.PaymentStatus.PAID) {
            throw new InvalidPaymentException("Order has already been paid.");
        }
    }

    private Payment buildPayment(
            CreatePaymentRequest request,
            OrderResponse order,
            String paymentCode) {

        return Payment.builder()
                .orderId(order.getId())
                .paymentCode(paymentCode)
                .paymentMethod(request.getPaymentMethod())
                .status(PaymentStatus.PENDING)
                .amount(order.getTotalAmount())
                .active(true).build();

    }

    private Payment savePayment(Payment payment) {

        Payment savedPayment = paymentRepository.saveAndFlush(payment);

        return paymentRepository.findById(savedPayment.getId()).orElseThrow();
    }

    private PaymentResponse buildResponse(Payment payment) {

        return paymentMapper.toResponse(payment);
    }

    private String generatePaymentCode() {

        String date = LocalDate.now()
                .format(DateTimeFormatter
                        .ofPattern("yyyyMMdd"));

        String suffix = String.valueOf(System.currentTimeMillis());

        suffix = suffix.substring(suffix.length() - 6);

        return "PAY-" + date + "-" + suffix;
    }

    private Payment findPaymentById(UUID paymentId) {

        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found: " + paymentId));

    }

    private void updateOrderPaymentStatus(
            UUID orderId,
            com.marketplace.payment.client.order.dto.PaymentStatus paymentStatus,
            com.marketplace.payment.client.order.dto.OrderStatus orderStatus) {

        UpdatePaymentStatusRequest request = new UpdatePaymentStatusRequest();

        request.setPaymentStatus(paymentStatus);

        request.setOrderStatus(orderStatus);

        orderClientService.updatePaymentStatus(orderId, request);

    }

    private Payment findPaymentByCode(
            String paymentCode) {

        return paymentRepository
                .findByPaymentCode(paymentCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found: " + paymentCode));

    }

    public PaymentResponse paymentSuccessByCode(
            String paymentCode,
            String transactionId,
            String gatewayResponse) {

        Payment payment = findPaymentByCode(paymentCode);

        return paymentSuccess(
                payment.getId(),
                transactionId,
                gatewayResponse);

    }

    public PaymentResponse paymentFailedByCode(
            String paymentCode,
            String gatewayResponse) {

        Payment payment = findPaymentByCode(paymentCode);

        return paymentFailed(
                payment.getId(),
                gatewayResponse);

    }

}
