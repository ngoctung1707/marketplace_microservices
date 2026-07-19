package com.marketplace.order.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    /**
     * Đơn hàng
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /**
     * Thông tin sản phẩm
     */
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "shop_id", nullable = false)
    private UUID shopId;

    /**
     * Snapshot thông tin sản phẩm tại thời điểm đặt hàng
     */
    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "thumbnail")
    private String thumbnail;

    /**
     * Giá bán tại thời điểm đặt hàng
     */
    @Column(name = "unit_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal unitPrice;

    /**
     * Số lượng mua
     */
    @Column(nullable = false)
    private Integer quantity;

    /**
     * Thành tiền = unitPrice × quantity
     */
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal subtotal;
}
