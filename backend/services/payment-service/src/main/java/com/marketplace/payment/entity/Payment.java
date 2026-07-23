package com.marketplace.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    /** * Đơn hàng cần thanh toán */
    @Column(name = "order_id", nullable = false, unique = true)
    private UUID orderId;

    /** * Mã giao dịch nội bộ * Ví dụ: * PAY-20260720-123456 */
    @Column(name = "payment_code", nullable = false, unique = true, length = 50)
    private String paymentCode;

    /** * Phương thức thanh toán */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    /** * Trạng thái thanh toán */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;

    /** * Tổng tiền cần thanh toán */
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    /** * Mã giao dịch từ cổng thanh toán */
    @Column(name = "transaction_id")
    private String transactionId;

    /** * Raw response từ gateway */
    @Column(columnDefinition = "TEXT")
    private String gatewayResponse;

    /** * Soft delete */
    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /** Thời điểm thanh toán thành công */
    @Column(name = "paid_at")
    private LocalDateTime paidAt;
}
