package com.furniture.service;

import com.furniture.entity.Favorite;
import com.furniture.entity.Product;
import com.furniture.entity.User;
import com.furniture.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public List<Favorite> getFavoritesByUserId(Long userId) {
        return favoriteRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public boolean isFavorite(Long userId, Long productId) {
        return favoriteRepository.existsByUserIdAndProductId(userId, productId);
    }

    public void addFavorite(Long userId, Long productId) {
        if (!isFavorite(userId, productId)) {
            Favorite favorite = new Favorite();
            User user = new User();
            user.setId(userId);
            favorite.setUser(user);
            Product product = new Product();
            product.setId(productId);
            favorite.setProduct(product);
            favoriteRepository.save(favorite);
        }
    }

    public void removeFavorite(Long userId, Long productId) {
        Favorite favorite = favoriteRepository.findByUserIdAndProductId(userId, productId).orElse(null);
        if (favorite != null) {
            favoriteRepository.delete(favorite);
        }
    }
}
