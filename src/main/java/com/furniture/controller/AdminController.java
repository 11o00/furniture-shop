package com.furniture.controller;

import com.furniture.entity.*;
import com.furniture.repository.OrderRepository;
import com.furniture.repository.ProductRepository;
import com.furniture.repository.UserRepository;
import com.furniture.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;
    private final BannerService bannerService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AdminMockService adminMockService;

    private void addAdminUser(Model model, Authentication authentication) {
        model.addAttribute("currentUser", userService.findByUsername(authentication.getName()));
    }

    @GetMapping
    public String adminDashboard(Model model, Authentication authentication) {
        addAdminUser(model, authentication);
        
        // 统计今日订单数
        Long todayOrderCount = orderRepository.countTodayOrders();
        model.addAttribute("todayOrderCount", todayOrderCount);
        
        // 统计今日销售额
        BigDecimal todayAmount = orderRepository.sumTodayAmount();
        model.addAttribute("todayAmount", todayAmount != null ? todayAmount : BigDecimal.ZERO);
        
        // 统计待处理订单数（PENDING和PAID状态）
        Long pendingOrderCount = orderRepository.countPendingOrders(
            Arrays.asList(Order.OrderStatus.PENDING, Order.OrderStatus.PAID)
        );
        model.addAttribute("pendingOrderCount", pendingOrderCount);
        
        // 统计低库存商品数（库存小于10）
        List<Product> lowStockProducts = productRepository.findByStockLessThan(10);
        model.addAttribute("lowStockCount", lowStockProducts.size());
        
        // 统计最近7天新用户数
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        Long newUserCount = userRepository.countNewUsers(sevenDaysAgo);
        model.addAttribute("newUserCount", newUserCount);
        
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String users(Model model, Authentication authentication) {
        addAdminUser(model, authentication);

        List<User> buyers = userService.findByRole(User.Role.ROLE_BUYER);
        List<User> sellers = userService.findByRole(User.Role.ROLE_SELLER);

        model.addAttribute("buyers", buyers);
        model.addAttribute("sellers", sellers);

        return "admin/users";
    }

    @PostMapping("/users/{id}/toggle")
    public String toggleUserStatus(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user.getEnabled()) {
            userService.disableUser(id);
        } else {
            userService.enableUser(id);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/products")
    public String products(Model model, Authentication authentication) {
        addAdminUser(model, authentication);

        model.addAttribute("products", productService.getAllProductsForAdmin());

        return "admin/products";
    }

    @PostMapping("/products/{id}/toggle")
    public String toggleProductStatus(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        product.setStatus(product.getStatus() == 1 ? 0 : 1);
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/categories")
    public String categories(Model model, Authentication authentication) {
        addAdminUser(model, authentication);

        List<Category> categories = productService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("category", new Category());

        return "admin/categories";
    }

    @PostMapping("/categories")
    public String saveCategory(@ModelAttribute Category category) {
        productService.saveCategory(category);
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/{id}/delete")
    public String deleteCategory(@PathVariable Long id) {
        productService.deleteCategory(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/orders")
    public String orders(Model model, Authentication authentication) {
        addAdminUser(model, authentication);

        model.addAttribute("orders", adminMockService.getMockOrders());
        model.addAttribute("mockOrders", true);

        return "admin/orders";
    }

    @GetMapping("/banners")
    public String banners(Model model, Authentication authentication) {
        addAdminUser(model, authentication);

        List<Banner> banners = bannerService.getAllBanners();
        model.addAttribute("banners", banners);
        model.addAttribute("banner", new Banner());

        return "admin/banners";
    }

    @PostMapping("/banners")
    public String saveBanner(@ModelAttribute Banner banner) {
        bannerService.saveBanner(banner);
        return "redirect:/admin/banners";
    }

    @PostMapping("/banners/{id}/delete")
    public String deleteBanner(@PathVariable Long id) {
        bannerService.deleteBanner(id);
        return "redirect:/admin/banners";
    }

    @GetMapping("/reviews")
    public String reviews(Model model, Authentication authentication) {
        addAdminUser(model, authentication);
        model.addAttribute("reviews", adminMockService.getMockReviews());
        return "admin/reviews";
    }

    @PostMapping("/reviews/{id}/approve")
    public String approveReview(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "评价 #" + id + " 已通过审核（模拟）");
        return "redirect:/admin/reviews";
    }

    @PostMapping("/reviews/{id}/hide")
    public String hideReview(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "评价 #" + id + " 已隐藏（模拟）");
        return "redirect:/admin/reviews";
    }

    @GetMapping("/announcements")
    public String announcements(Model model, Authentication authentication) {
        addAdminUser(model, authentication);
        model.addAttribute("announcements", adminMockService.getMockAnnouncements());
        return "admin/announcements";
    }

    @PostMapping("/announcements/publish")
    public String publishAnnouncement(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "公告已发布（模拟）");
        return "redirect:/admin/announcements";
    }

    @GetMapping("/reports")
    public String reports(Model model, Authentication authentication) {
        addAdminUser(model, authentication);
        model.addAttribute("salesReport", adminMockService.getMockSalesReport());
        model.addAttribute("monthlyTrend", adminMockService.getMockMonthlyTrend());
        model.addAttribute("totalGmv", 401300);
        model.addAttribute("totalOrders", 351);
        model.addAttribute("avgOrderAmount", 1143);
        return "admin/reports";
    }

    @GetMapping("/settings")
    public String settings(Model model, Authentication authentication) {
        addAdminUser(model, authentication);
        model.addAttribute("settings", adminMockService.getMockSettings());
        return "admin/settings";
    }

    @PostMapping("/settings")
    public String saveSettings(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "系统设置已保存（模拟，未写入数据库）");
        return "redirect:/admin/settings";
    }
}
