package com.furniture.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理端模拟展示用通用数据项
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminMockItem {
    private Long id;
    private String title;
    private String subtitle;
    private String content;
    private String status;
    private String statusLabel;
    private String badgeClass;
    private String time;
    private Integer rating;
    private String extra;
}
