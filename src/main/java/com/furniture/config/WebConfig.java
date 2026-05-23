package com.furniture.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置。UTF-8 编码由 application.properties 中 server.servlet.encoding 统一配置，
 * 勿在此重复注册 characterEncodingFilter，否则会与 Spring Boot 自动配置冲突导致启动失败。
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
}
