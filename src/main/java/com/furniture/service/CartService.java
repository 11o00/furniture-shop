package com.furniture.service;

import com.furniture.entity.Cart;
import com.furniture.entity.Product;
import com.furniture.entity.User;
import com.furniture.repository.CartRepository;
import com.furniture.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public List<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public BigDecimal getCartTotal(Long userId) {
        List<Cart> carts = getCartByUserId(userId);
        return carts.stream()
                .map(cart -> cart.getProduct().getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addToCart(Long userId, Long productId, int quantity) {
        Cart existingCart = cartRepository.findByUserIdAndProductId(userId, productId).orElse(null);
        if (existingCart != null) {
            existingCart.setQuantity(existingCart.getQuantity() + quantity);
            cartRepository.save(existingCart);
        } else {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                User user = new User();
                user.setId(userId);
                Cart cart = new Cart();
                cart.setUser(user);
                cart.setProduct(product);
                cart.setQuantity(quantity);
                cartRepository.save(cart);
            }
        }
    }

    public void updateCartQuantity(Long cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null) {
            cart.setQuantity(quantity);
            cartRepository.save(cart);
        }
    }

    public void removeFromCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    public void clearCart(Long userId) {
        cartRepository.deleteByUserId(userId);
    }
}
