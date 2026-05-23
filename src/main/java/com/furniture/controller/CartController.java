package com.furniture.controller;

import com.furniture.entity.Cart;
import com.furniture.entity.User;
import com.furniture.service.CartService;
import com.furniture.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @GetMapping
    public String viewCart(Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("currentUser", user);
        
        List<Cart> cartItems = cartService.getCartByUserId(user.getId());
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", cartService.getCartTotal(user.getId()));
        
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId,
                           @RequestParam(defaultValue = "1") int quantity,
                           Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        cartService.addToCart(user.getId(), productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam Long cartId,
                            @RequestParam int quantity,
                            Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        cartService.updateCartQuantity(cartId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long cartId) {
        cartService.removeFromCart(cartId);
        return "redirect:/cart";
    }
}
