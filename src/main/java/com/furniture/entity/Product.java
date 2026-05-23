package com.furniture.entity;

import javax.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "main_image", length = 255)
    private String mainImage;

    @Column(name = "detail_images", length = 2000)
    private String detailImages;

    @Column
    private Integer status = 1;

    @Column
    private Integer sales = 0;

    @Column(length = 100)
    private String material;

    @Column(length = 50)
    private String size;

    @Column(length = 50)
    private String style;

    @Column(length = 50)
    private String brand;

    @Column(length = 100)
    private String dimensions;

    @Column(name = "original_price", precision = 10, scale = 2)
    private BigDecimal originalPrice;

    @Column(length = 200)
    private String subtitle;

    @Column(name = "is_new")
    private Boolean isNew = false;

    @Column
    private Boolean recommended = false;

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    @CreatedDate
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    public enum ProductStatus {
        ACTIVE, INACTIVE
    }

    /** 前端展示用图片地址：优先主图，其次 imageUrl，最后占位图 */
    public String getDisplayImage() {
        if (mainImage != null && !mainImage.trim().isEmpty()) {
            return mainImage;
        }
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            return imageUrl;
        }
        return "/images/placeholder-product.svg";
    }
}
