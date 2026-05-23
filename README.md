# 家具商城 - Furniture Mall

基于 Spring Boot 2.7 的家具售卖网上商城系统，适合 Java Web 入门学习。

---

## 📋 项目概况

### 技术栈

| 分类 | 技术 |
|------|------|
| 后端框架 | Spring Boot 2.7.18 |
| 安全框架 | Spring Security |
| 数据访问 | Spring Data JPA |
| 前端模板 | Thymeleaf |
| UI 框架 | Bootstrap 5 |
| 数据库 | MySQL 8.0 |
| 构建工具 | Maven 3.9+ |
| Java 版本 | JDK 8 |

### 功能模块

#### 三种用户角色

| 角色 | 说明 |
|------|------|
| 买家 (BUYER) | 普通消费者，可浏览商品、加入购物车、下单购买、管理收货地址 |
| 卖家 (SELLER) | 商品管理者，可添加商品、管理订单、处理发货 |
| 管理员 (ADMIN) | 系统管理者，可管理所有用户、商品、分类、轮播图等 |

#### 买家功能

- 首页展示轮播图和热门商品
- 商品分类浏览和搜索
- 商品详情查看
- 购物车管理
- 下单购买
- 我的订单
- 商品评价
- 收藏商品
- 个人中心

#### 卖家功能

- 卖家中心首页
- 商品管理（添加、编辑、上下架）
- 订单管理（查看、处理、发货）

#### 管理员功能

- 管理后台首页
- 用户管理
- 商品管理
- 分类管理
- 订单管理
- 轮播图管理

---

## 🚀 启动方式

### 环境要求

- JDK 8
- MySQL 8.0
- Maven 3.9+

### 步骤一：配置数据库

1. 创建数据库并导入表结构：
```sql
mysql -u root -p123456 -e "CREATE DATABASE furniture_mall CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

mysql -u root -p123456 furniture_mall -e "source 你的项目路径/src/main/resources/schema.sql"

mysql -u root -p123456 furniture_mall -e "source 你的项目路径/src/main/resources/data.sql"
```

2. 修改数据库配置（如果密码不是 123456）：
   - 打开 `src/main/resources/application.properties`
   - 修改 `spring.datasource.password` 为你的 MySQL 密码

### 步骤二：启动项目

方式一：使用 Maven 命令
```bash
cd 项目目录
mvn spring-boot:run
```

方式二：使用 Maven Wrapper（无需安装 Maven）
```bash
cd 项目目录
mvnw.cmd spring-boot:run
```

### 步骤三：访问网站

启动成功后，打开浏览器访问：**http://localhost:8080**

---

## 👤 测试账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | 123456 |
| 卖家 | seller | 123456 |
| 买家 | user | 123456 |

---

## 📁 项目结构

```
furniture-mall/
├── src/main/java/com/furniture/
│   ├── config/          # 配置类
│   ├── controller/     # 控制器
│   ├── entity/         # 实体类
│   ├── repository/     # 数据仓库
│   ├── security/       # 安全相关
│   ├── service/        # 业务逻辑
│   └── FurnitureMallApplication.java  # 启动类
├── src/main/resources/
│   ├── static/css/     # 静态资源
│   ├── templates/      # Thymeleaf 模板
│   ├── application.properties  # 应用配置
│   ├── schema.sql      # 数据库表结构
│   └── data.sql        # 测试数据
└── pom.xml             # Maven 配置
```

---

## ⚠️ 注意事项

1. 商品图片显示为占位图标（灰色图片），这是正常的，不影响功能使用
2. 首次启动会自动创建数据库表（`spring.jpa.hibernate.ddl-auto=update`）
3. 如果端口 8080 被占用，可修改 `application.properties` 中的 `server.port`
4. 密码使用 BCrypt 加密存储

---

## 📝 许可证

本项目仅供学习参考使用。