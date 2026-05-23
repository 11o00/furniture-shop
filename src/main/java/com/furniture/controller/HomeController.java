package com.furniture.controller;

import com.furniture.entity.Banner;
import com.furniture.entity.Product;
import com.furniture.entity.User;
import com.furniture.service.BannerService;
import com.furniture.service.ProductService;
import com.furniture.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final BannerService bannerService;
    private final UserService userService;

    @GetMapping({"/", "/home"})
    public String home(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.findByUsername(authentication.getName());
            model.addAttribute("currentUser", user);
        }
        
        List<Banner> banners = bannerService.getActiveBanners();
        List<Product> hotProducts = productService.getHotProducts(8);
        List<Product> newProducts = productService.getNewProducts(8);
        
        model.addAttribute("banners", banners);
        model.addAttribute("hotProducts", hotProducts);
        model.addAttribute("newProducts", newProducts);
        model.addAttribute("categories", productService.getAllCategories());
        
        return "home";
    }
}
