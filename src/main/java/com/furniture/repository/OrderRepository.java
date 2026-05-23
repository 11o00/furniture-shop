package com.furniture.repository;

import com.furniture.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserIdOrderByCreateTimeDesc(Long userId);
    List<Order> findByUserIdAndStatusOrderByCreateTimeDesc(Long userId, Order.OrderStatus status);
    
    @Query("SELECT o FROM Order o JOIN o.orderItems oi WHERE oi.product.seller.id = :sellerId ORDER BY o.createTime DESC")
    List<Order> findBySellerId(Long sellerId);
    
    List<Order> findAllByOrderByCreateTimeDesc();
    
    @Query("SELECT COUNT(o) FROM Order o WHERE DATE(o.createTime) = CURRENT_DATE")
    Long countTodayOrders();
    
    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE DATE(o.createTime) = CURRENT_DATE")
    BigDecimal sumTodayAmount();
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.status IN (:statuses)")
    Long countPendingOrders(@Param("statuses") List<Order.OrderStatus> statuses);
}