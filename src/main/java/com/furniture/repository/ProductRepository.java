package com.furniture.repository;

import com.furniture.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatusOrderBySalesDesc(Integer status, Pageable pageable);

    List<Product> findByStatusOrderByCreateTimeDesc(Integer status, Pageable pageable);

    Page<Product> findByStatus(Integer status, Pageable pageable);

    Page<Product> findByCategoryIdAndStatus(Long categoryId, Integer status, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.status = 1 AND " +
           "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
           "(:keyword IS NULL OR p.name LIKE %:keyword% OR p.description LIKE %:keyword%)")
    Page<Product> searchProducts(@Param("categoryId") Long categoryId,
                                  @Param("minPrice") BigDecimal minPrice,
                                  @Param("maxPrice") BigDecimal maxPrice,
                                  @Param("keyword") String keyword,
                                  Pageable pageable);

    List<Product> findBySellerId(Long sellerId);

    List<Product> findTop10ByStatusOrderByCreateTimeDesc(Integer status);
    
    // 查询库存低于指定阈值的商品
    List<Product> findByStockLessThan(Integer threshold);
}
