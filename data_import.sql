SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM furnitureshop.cart_items;
DELETE FROM furnitureshop.order_items;
DELETE FROM furnitureshop.orders;
DELETE FROM furnitureshop.favorites;
DELETE FROM furnitureshop.reviews;
DELETE FROM furnitureshop.addresses;
DELETE FROM furnitureshop.banners;
DELETE FROM furnitureshop.products;
DELETE FROM furnitureshop.categories;
DELETE FROM furnitureshop.users;

INSERT INTO users (username, password, email, phone, role, enabled, create_time, update_time) VALUES
('admin', '$2a$10$QMhUnXcs/RyfCaTPuSXD8e69MuJiblrZn.BAjldKwWggpRXUUTnGG', 'admin@furniture.com', '13800138000', 'ROLE_ADMIN', TRUE, NOW(), NOW()),
('seller', '$2a$10$QMhUnXcs/RyfCaTPuSXD8e69MuJiblrZn.BAjldKwWggpRXUUTnGG', 'seller@furniture.com', '13800138001', 'ROLE_SELLER', TRUE, NOW(), NOW()),
('user', '$2a$10$QMhUnXcs/RyfCaTPuSXD8e69MuJiblrZn.BAjldKwWggpRXUUTnGG', 'user@furniture.com', '13800138002', 'ROLE_BUYER', TRUE, NOW(), NOW());

INSERT INTO categories (name, description, enabled, sort_order, create_time, update_time) VALUES
('沙发', '各种风格的舒适沙发', TRUE, 1, NOW(), NOW()),
('床', '卧室精品床具', TRUE, 2, NOW(), NOW()),
('衣柜', '大容量收纳衣柜', TRUE, 3, NOW(), NOW()),
('餐桌', '现代简约餐桌', TRUE, 4, NOW(), NOW()),
('椅子', '舒适办公椅餐椅', TRUE, 5, NOW(), NOW());

INSERT INTO products (name, description, price, stock, category_id, seller_id, image_url, main_image, status, sales, material, size, style, brand, is_new, recommended, view_count, create_time, update_time) VALUES
('现代简约布艺沙发', '舒适亲肤面料，高密度海绵填充，简约时尚设计', 2999.00, 50, 1, 2, '/images/sofa1.jpg', '/images/sofa1.jpg', 1, 120, '棉麻', '三人位', '现代简约', '宜家', TRUE, FALSE, 0, NOW(), NOW()),
('北欧实木床', '进口橡木，环保水性漆，简约北欧风格', 3599.00, 30, 2, 2, '/images/bed1.jpg', '/images/bed1.jpg', 1, 85, '橡木', '1.8m*2m', '北欧', '林氏木业', FALSE, TRUE, 0, NOW(), NOW()),
('大容量推拉门衣柜', 'E0级板材，大容量收纳，静音导轨', 4299.00, 25, 3, 2, '/images/wardrobe1.jpg', '/images/wardrobe1.jpg', 1, 60, '人造板', '1.6m宽', '现代', '索菲亚', FALSE, FALSE, 0, NOW(), NOW()),
('大理石餐桌', '天然大理石台面，不锈钢桌脚，高端大气', 5999.00, 15, 4, 2, '/images/table1.jpg', '/images/table1.jpg', 1, 45, '大理石+不锈钢', '1.4m', '轻奢', '全友', FALSE, TRUE, 0, NOW(), NOW()),
('人体工学办公椅', '可调节靠背，透气网布，护腰设计', 899.00, 100, 5, 2, '/images/chair1.jpg', '/images/chair1.jpg', 1, 200, '网布', '标准', '现代', '西昊', FALSE, FALSE, 0, NOW(), NOW()),
('欧式真皮沙发', '头层牛皮，实木框架，经典欧式雕花', 8999.00, 10, 1, 2, '/images/sofa2.jpg', '/images/sofa2.jpg', 1, 35, '真皮', '四人位', '欧式', '顾家', FALSE, FALSE, 0, NOW(), NOW()),
('儿童高低床', '环保松木，安全护栏，梯柜设计', 3299.00, 40, 2, 2, '/images/bed2.jpg', '/images/bed2.jpg', 1, 75, '松木', '1.2m', '儿童', '喜梦宝', FALSE, FALSE, 0, NOW(), NOW()),
('开放式书柜', '简约设计，多层储物，自由组合', 1899.00, 60, 3, 2, '/images/wardrobe2.jpg', '/images/wardrobe2.jpg', 1, 90, '人造板', '1.2m', '现代', '曲美', FALSE, FALSE, 0, NOW(), NOW()),
('伸缩餐桌', '可大可小，适合各种空间，实木材质', 2599.00, 35, 4, 2, '/images/table2.jpg', '/images/table2.jpg', 1, 55, '实木', '1.2m-1.8m', '现代', '红苹果', FALSE, FALSE, 0, NOW(), NOW()),
('休闲躺椅', '可调节角度，舒适躺卧，阳台必备', 699.00, 80, 5, 2, '/images/chair2.jpg', '/images/chair2.jpg', 1, 150, '藤编', '标准', '休闲', '藤朝', TRUE, FALSE, 0, NOW(), NOW());

INSERT INTO banners (title, image_url, link_url, sort_order, active, enabled, create_time, update_time) VALUES
('新品上市', '/images/banner1.jpg', '/products', 1, TRUE, TRUE, NOW(), NOW()),
('限时优惠', '/images/banner2.jpg', '/products', 2, TRUE, TRUE, NOW(), NOW()),
('品牌推荐', '/images/banner3.jpg', '/products', 3, TRUE, TRUE, NOW(), NOW());

SET FOREIGN_KEY_CHECKS = 1;
