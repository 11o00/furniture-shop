package com.furniture.service;

import com.furniture.entity.*;
import com.furniture.repository.OrderItemRepository;
import com.furniture.repository.OrderRepository;
import com.furniture.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;

    public Order createOrder(Long userId, List<Cart> cartItems, String address, String phone) {
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        User user = new User();
        user.setId(userId);
        order.setUser(user);
        order.setAddress(address);
        order.setPhone(phone);
        order.setStatus(Order.OrderStatus.PENDING);
        
        order.setTotalAmount(cartService.getCartTotal(userId));
        
        List<OrderItem> orderItems = new ArrayList<>();
        for (Cart cart : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cart.getProduct());
            orderItem.setProductName(cart.getProduct().getName());
            orderItem.setPrice(cart.getProduct().getPrice());
            orderItem.setQuantity(cart.getQuantity());
            orderItems.add(orderItem);
            
            Product product = cart.getProduct();
            product.setSales(product.getSales() + cart.getQuantity());
            product.setStock(product.getStock() - cart.getQuantity());
            productRepository.save(product);
        }
        order.setOrderItems(orderItems);
        
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(userId);
        
        return savedOrder;
    }

    private String generateOrderNo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "ORD" + timestamp + uuid;
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserIdOrderByCreateTimeDesc(userId);
    }

    public List<Order> getOrdersByUserIdAndStatus(Long userId, Order.OrderStatus status) {
        return orderRepository.findByUserIdAndStatusOrderByCreateTimeDesc(userId, status);
    }

    public List<Order> getOrdersBySellerId(Long sellerId) {
        return orderRepository.findBySellerId(sellerId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByCreateTimeDesc();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
        }
    }

    public List<Order> buildMockOrders(Long userId) {
        List<Order> orders = new ArrayList<>();
        orders.add(buildMockOrder(9001L, userId, Order.OrderStatus.SHIPPED, "MOCK202605230001",
                "上海市浦东新区张江路 88 号", "13800138000", "云朵布艺沙发", "¥2899", "/images/sofa1.jpg"));
        orders.add(buildMockOrder(9002L, userId, Order.OrderStatus.COMPLETED, "MOCK202605230002",
                "北京市朝阳区望京街道 66 号", "13900139000", "北欧原木餐桌", "¥1599", "/images/table1.jpg"));
        return orders;
    }

    public Order buildMockOrderDetail(Long orderId, Long userId) {
        return buildMockOrder(orderId, userId, Order.OrderStatus.SHIPPED, "MOCK" + orderId,
                "上海市浦东新区张江路 88 号", "13800138000", "云朵布艺沙发", "¥2899", "/images/sofa1.jpg");
    }

    private Order buildMockOrder(Long orderId,
                                 Long userId,
                                 Order.OrderStatus status,
                                 String orderNo,
                                 String address,
                                 String phone,
                                 String productName,
                                 String priceText,
                                 String imageUrl) {
        User user = new User();
        user.setId(userId);

        Product product = new Product();
        product.setId(orderId);
        product.setName(productName);
        product.setMainImage(imageUrl);
        product.setImageUrl(imageUrl);

        OrderItem item = new OrderItem();
        item.setId(orderId);
        item.setProduct(product);
        item.setProductName(productName);
        item.setPrice(parsePrice(priceText));
        item.setQuantity(1);

        Order order = new Order();
        order.setId(orderId);
        order.setOrderNo(orderNo);
        order.setUser(user);
        order.setStatus(status);
        order.setAddress(address);
        order.setPhone(phone);
        order.setCreateTime(LocalDateTime.now().minusDays(orderId % 3).withSecond(0).withNano(0));
        order.setShippingTime(LocalDateTime.now().minusHours(18).withSecond(0).withNano(0));
        order.setTotalAmount(item.getPrice());
        order.setOrderItems(Collections.singletonList(item));
        item.setOrder(order);
        return order;
    }

    private BigDecimal parsePrice(String priceText) {
        return new BigDecimal(priceText.replace("¥", "").trim());
    }
}
