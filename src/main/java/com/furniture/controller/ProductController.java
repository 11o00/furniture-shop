package com.furniture.controller;

import com.furniture.entity.Category;
import com.furniture.entity.Product;
import com.furniture.entity.Review;
import com.furniture.entity.User;
import com.furniture.service.ProductService;
import com.furniture.service.ReviewService;
import com.furniture.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ReviewService reviewService;
    private final UserService userService;

    @GetMapping("/products")
    public String listProducts(@RequestParam(required = false) Long categoryId,
                               @RequestParam(required = false) BigDecimal minPrice,
                               @RequestParam(required = false) BigDecimal maxPrice,
                               @RequestParam(required = false) String keyword,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "12") int size,
                               @RequestParam(defaultValue = "createTime") String sortBy,
                               @RequestParam(defaultValue = "desc") String sortDir,
                               Model model,
                               Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.findByUsername(authentication.getName());
            model.addAttribute("currentUser", user);
        }
        
        Page<Product> productPage = productService.searchProducts(categoryId, minPrice, maxPrice, keyword, page, size, sortBy, sortDir);
        
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        
        return "products";
    }

    @GetMapping("/products/{id}")
    public String productDetail(@PathVariable Long id, Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.findByUsername(authentication.getName());
            model.addAttribute("currentUser", user);
        }
        
        Product product = productService.getProductById(id);
        List<Review> reviews = reviewService.getReviewsByProductId(id);
        
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("categories", productService.getAllCategories());
        
        return "product-detail";
    }

    @PostMapping("/products/{id}/review")
    public String addReview(@PathVariable Long id,
                           @RequestParam Integer rating,
                           @RequestParam String comment,
                           Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.findByUsername(authentication.getName());
            reviewService.addReview(user.getId(), id, rating, comment);
        }
        return "redirect:/products/" + id;
    }
}
