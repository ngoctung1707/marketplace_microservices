package com.marketplace.payment.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.payment.config.VNPayConfig;
import com.marketplace.payment.dto.request.CreatePaymentRequest;
import com.marketplace.payment.dto.request.PaymentFailedRequest;
import com.marketplace.payment.dto.request.PaymentSuccessRequest;
import com.marketplace.payment.dto.response.PaymentResponse;
import com.marketplace.payment.dto.response.VNPayPaymentResponse;
import com.marketplace.payment.service.PaymentService;
import com.marketplace.payment.util.VNPayUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Validated
public class PaymentController {
    private final PaymentService paymentService;
    private final VNPayConfig vnPayConfig;

    /** * Tạo payment */
    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @Valid @RequestBody CreatePaymentRequest request) {

        return ResponseEntity.ok(paymentService.createPayment(request));

    }

    /** * Lấy payment theo ID */
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPayment(
            @PathVariable("paymentId") UUID paymentId) {

        return ResponseEntity.ok(paymentService.getPayment(paymentId));

    }

    /** * Lấy payment theo Order */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentByOrder(
            @PathVariable("orderId") UUID orderId) {

        return ResponseEntity.ok(paymentService.getPaymentByOrder(orderId));

    }

    /** * Lấy toàn bộ payment */
    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {

        return ResponseEntity.ok(paymentService.getAllPayments());

    }

    @PostMapping("/{paymentId}/success")
    public ResponseEntity<PaymentResponse> paymentSuccess(

            @PathVariable("paymentId") UUID paymentId,

            @RequestBody PaymentSuccessRequest request) {

        PaymentResponse response = paymentService.paymentSuccess(

                paymentId,

                request.getTransactionId(),

                request.getGatewayResponse());

        return ResponseEntity.ok(response);

    }

    @PostMapping("/{paymentId}/failed")
    public ResponseEntity<PaymentResponse> paymentFailed(

            @PathVariable("paymentId") UUID paymentId,

            @Valid @RequestBody PaymentFailedRequest request) {

        PaymentResponse response = paymentService.paymentFailed(
                paymentId,
                request.getGatewayResponse());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{paymentId}/vnpay")
    public ResponseEntity<VNPayPaymentResponse> createVNPayPayment(

            @PathVariable("paymentId") UUID paymentId) {

        return ResponseEntity.ok(
                paymentService.createVNPayPayment(paymentId));

    }

    @GetMapping("/vnpay/return")
    public ResponseEntity<?> vnPayReturn(
            @RequestParam Map<String, String> params) {

        // Verify chữ ký VNPay
        if (!VNPayUtil.verifySignature(
                params,
                vnPayConfig.getHashSecret())) {

            return ResponseEntity.badRequest()
                    .body("Invalid VNPay Signature");

        }

        String paymentCode = params.get("vnp_TxnRef");

        String transactionId = params.get("vnp_TransactionNo");

        String responseCode = params.get("vnp_ResponseCode");

        if ("00".equals(responseCode)) {

            return ResponseEntity.ok(
                    paymentService.paymentSuccessByCode(
                            paymentCode,
                            transactionId,
                            "SUCCESS"));

        }

        return ResponseEntity.ok(
                paymentService.paymentFailedByCode(
                        paymentCode,
                        responseCode));

    }

}
