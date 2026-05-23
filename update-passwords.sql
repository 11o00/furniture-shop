SET NAMES utf8mb4;
UPDATE users SET password='$2a$10$QMhUnXcs/RyfCaTPuSXD8e69MuJiblrZn.BAjldKwWggpRXUUTnGG'
WHERE username IN ('admin', 'seller', 'user');
