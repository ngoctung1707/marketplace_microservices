package com.marketplace.order.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    /**
     * ID của người mua (User Service)
     */
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    /**
     * Mã đơn hàng (VD: ORD202607160001)
     */
    @Column(name = "order_code", nullable = false, unique = true, length = 50)
    private String orderCode;

    /**
     * Trạng thái đơn hàng
     */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    /**
     * Trạng thái thanh toán
     */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    /**
     * Thông tin người nhận
     */
    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @Column(name = "receiver_phone", nullable = false)
    private String receiverPhone;

    @Column(name = "shipping_address", nullable = false, columnDefinition = "TEXT")
    private String shippingAddress;

    /**
     * Giá trị đơn hàng
     */
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal subtotal;

    @Builder.Default
    @Column(name = "shipping_fee", nullable = false, precision = 19, scale = 2)
    private BigDecimal shippingFee = BigDecimal.ZERO;

    @Builder.Default
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    /**
     * Soft Delete
     */
    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    /**
     * Danh sách sản phẩm trong đơn hàng
     */
    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
