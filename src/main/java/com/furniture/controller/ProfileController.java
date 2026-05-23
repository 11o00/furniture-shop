package com.furniture.controller;

import com.furniture.entity.*;
import com.furniture.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final AddressService addressService;
    private final FavoriteService favoriteService;
    private final ReviewService reviewService;

    @GetMapping
    public String profile(Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("currentUser", user);
        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute User user, Authentication authentication) {
        User currentUser = userService.findByUsername(authentication.getName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPhone(user.getPhone());
        userService.updateUser(currentUser);
        return "redirect:/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        userService.updatePassword(user.getId(), oldPassword, newPassword);
        return "redirect:/profile";
    }

    @GetMapping("/addresses")
    public String addresses(Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("currentUser", user);
        
        List<Address> addresses = addressService.getAddressesByUserId(user.getId());
        model.addAttribute("addresses", addresses);
        model.addAttribute("newAddress", new Address());
        
        return "addresses";
    }

    @PostMapping("/addresses/add")
    public String addAddress(@ModelAttribute Address address, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        addressService.saveAddress(address, user.getId());
        return "redirect:/profile/addresses";
    }

    @PostMapping("/addresses/{id}/delete")
    public String deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return "redirect:/profile/addresses";
    }

    @PostMapping("/addresses/{id}/default")
    public String setDefaultAddress(@PathVariable Long id, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        addressService.setDefaultAddress(id, user.getId());
        return "redirect:/profile/addresses";
    }

    @GetMapping("/favorites")
    public String favorites(Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("currentUser", user);
        
        List<Favorite> favorites = favoriteService.getFavoritesByUserId(user.getId());
        model.addAttribute("favorites", favorites);
        
        return "favorites";
    }

    @PostMapping("/favorites/add")
    public String addFavorite(@RequestParam Long productId, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        favoriteService.addFavorite(user.getId(), productId);
        return "redirect:/products/" + productId;
    }

    @PostMapping("/favorites/{productId}/remove")
    public String removeFavorite(@PathVariable Long productId, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        favoriteService.removeFavorite(user.getId(), productId);
        return "redirect:/profile/favorites";
    }

    @GetMapping("/reviews")
    public String reviews(Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("currentUser", user);
        
        List<Review> reviews = reviewService.getReviewsByUserId(user.getId());
        model.addAttribute("reviews", reviews);
        
        return "my-reviews";
    }
}
