package com.furniture.service;

import com.furniture.entity.Category;
import com.furniture.entity.Product;
import com.furniture.repository.CategoryRepository;
import com.furniture.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getHotProducts(int limit) {
        return productRepository.findByStatusOrderBySalesDesc(
                1,
                PageRequest.of(0, limit)
        );
    }

    public List<Product> getNewProducts(int limit) {
        return productRepository.findByStatusOrderByCreateTimeDesc(
                1,
                PageRequest.of(0, limit)
        );
    }

    public Page<Product> getAllProducts(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findByStatus(1, pageable);
    }

    /** 管理端：查询全部商品（含上架/下架） */
    public List<Product> getAllProductsForAdmin() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "createTime"));
    }

    public Page<Product> searchProducts(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice,
                                        String keyword, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.searchProducts(categoryId, minPrice, maxPrice, keyword, pageable);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getProductsBySeller(Long sellerId) {
        return productRepository.findBySellerId(sellerId);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
