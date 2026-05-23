package com.furniture.entity;

import javax.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_no", nullable = false, unique = true, length = 50)
    private String orderNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(length = 20)
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 20)
    private String phone;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;
    
    @CreatedDate
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;
    
    public LocalDateTime getCreatedAt() {
        return createTime;
    }

    @Column(name = "payment_time")
    private LocalDateTime paymentTime;

    @Column(name = "shipping_time")
    private LocalDateTime shippingTime;

    @Column(name = "completion_time")
    private LocalDateTime completionTime;

    @Column(name = "cancellation_time")
    private LocalDateTime cancellationTime;

    @Column(name = "refund_time")
    private LocalDateTime refundTime;

    public enum OrderStatus {
        PENDING, PAID, SHIPPED, COMPLETED, CANCELLED, REFUNDING, REFUNDED
    }
}
