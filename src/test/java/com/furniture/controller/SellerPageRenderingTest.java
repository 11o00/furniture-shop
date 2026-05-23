package com.furniture.controller;

import com.furniture.config.SecurityConfig;
import com.furniture.entity.Product;
import com.furniture.entity.User;
import com.furniture.security.CustomUserDetailsService;
import com.furniture.service.OrderService;
import com.furniture.service.ProductService;
import com.furniture.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(SellerController.class)
@Import(SecurityConfig.class)
class SellerPageRenderingTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ProductService productService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void sellerDashboardRendersForSeller() throws Exception {
        User seller = new User();
        seller.setId(2L);
        seller.setUsername("seller");
        seller.setRole(User.Role.ROLE_SELLER);

        Product product = new Product();
        product.setId(100L);
        product.setName("云屿奶油沙发");
        product.setPrice(new BigDecimal("2899.00"));
        product.setStock(18);
        product.setSales(126);
        product.setStatus(1);
        product.setImageUrl("/images/sofa1.jpg");

        when(userService.findByUsername("seller")).thenReturn(seller);
        when(productService.getProductsBySeller(2L)).thenReturn(Collections.singletonList(product));

        mockMvc.perform(get("/seller")
                        .with(SecurityMockMvcRequestPostProcessors.user("seller").roles("SELLER")))
                .andExpect(status().isOk())
                .andExpect(view().name("seller/dashboard"));
    }
}
