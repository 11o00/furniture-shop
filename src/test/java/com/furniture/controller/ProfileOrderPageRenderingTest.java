package com.furniture.controller;

import com.furniture.config.SecurityConfig;
import com.furniture.entity.Address;
import com.furniture.entity.Order;
import com.furniture.entity.OrderItem;
import com.furniture.entity.Product;
import com.furniture.entity.User;
import com.furniture.security.CustomUserDetailsService;
import com.furniture.service.AddressService;
import com.furniture.service.CartService;
import com.furniture.service.FavoriteService;
import com.furniture.service.OrderService;
import com.furniture.service.ReviewService;
import com.furniture.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest({ProfileController.class, OrderController.class})
@Import(SecurityConfig.class)
class ProfileOrderPageRenderingTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AddressService addressService;

    @MockBean
    private FavoriteService favoriteService;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private CartService cartService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void addressesPageRendersForAuthenticatedUser() throws Exception {
        User user = buildUser();
        Address address = new Address();
        address.setId(1L);
        address.setName("测试用户");
        address.setPhone("13800138000");
        address.setAddress("上海市浦东新区张江路 88 号");
        address.setIsDefault(true);

        when(userService.findByUsername("user")).thenReturn(user);
        when(addressService.getAddressesByUserId(58L)).thenReturn(Collections.singletonList(address));

        mockMvc.perform(get("/profile/addresses")
                        .with(SecurityMockMvcRequestPostProcessors.user("user").roles("BUYER")))
                .andExpect(status().isOk())
                .andExpect(view().name("addresses"));
    }

    @Test
    void orderDetailPageRendersForOwner() throws Exception {
        User user = buildUser();
        Product product = new Product();
        product.setId(9L);
        product.setName("北欧餐桌");
        product.setImageUrl("/images/table1.jpg");

        OrderItem item = new OrderItem();
        item.setId(2L);
        item.setProduct(product);
        item.setProductName("北欧餐桌");
        item.setPrice(new BigDecimal("2599.00"));
        item.setQuantity(1);

        Order order = new Order();
        order.setId(1L);
        order.setOrderNo("ORD202605230001");
        order.setUser(user);
        order.setStatus(Order.OrderStatus.PENDING);
        order.setAddress("上海市浦东新区张江路 88 号");
        order.setPhone("13800138000");
        order.setTotalAmount(new BigDecimal("2599.00"));
        order.setCreateTime(LocalDateTime.of(2026, 5, 23, 16, 30));
        order.setOrderItems(Collections.singletonList(item));

        when(userService.findByUsername("user")).thenReturn(user);
        when(orderService.getOrderById(1L)).thenReturn(order);

        mockMvc.perform(get("/order/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("user").roles("BUYER")))
                .andExpect(status().isOk())
                .andExpect(view().name("order-detail"));
    }

    @Test
    void myOrdersPageRendersForAuthenticatedUser() throws Exception {
        User user = buildUser();
        Order order = new Order();
        order.setId(1L);
        order.setOrderNo("ORD202605230001");
        order.setUser(user);
        order.setStatus(Order.OrderStatus.SHIPPED);
        order.setAddress("上海市浦东新区张江路 88 号");
        order.setPhone("13800138000");
        order.setTotalAmount(new BigDecimal("2599.00"));
        order.setCreateTime(LocalDateTime.of(2026, 5, 23, 16, 30));
        order.setOrderItems(Collections.singletonList(new OrderItem()));

        when(userService.findByUsername("user")).thenReturn(user);
        when(orderService.getOrdersByUserId(58L)).thenReturn(Collections.singletonList(order));

        mockMvc.perform(get("/order/my-orders")
                        .with(SecurityMockMvcRequestPostProcessors.user("user").roles("BUYER")))
                .andExpect(status().isOk())
                .andExpect(view().name("my-orders"));
    }

    private User buildUser() {
        User user = new User();
        user.setId(58L);
        user.setUsername("user");
        user.setRole(User.Role.ROLE_BUYER);
        user.setEnabled(true);
        return user;
    }
}
