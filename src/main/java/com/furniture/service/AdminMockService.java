package com.furniture.service;

import com.furniture.dto.admin.AdminMockItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminMockService {

    public List<AdminMockItem> getMockReviews() {
        List<AdminMockItem> list = new ArrayList<>();
        list.add(new AdminMockItem(1L, "user", "现代简约布艺沙发", "坐感舒适，物流很快，和图片一致。", "VISIBLE", "已展示", "bg-success", "2026-05-22 14:30", 5, null));
        list.add(new AdminMockItem(2L, "zhangsan", "北欧实木床", "味道很小，安装师傅很专业。", "VISIBLE", "已展示", "bg-success", "2026-05-21 09:15", 5, null));
        list.add(new AdminMockItem(3L, "lisi", "人体工学办公椅", "靠背调节方便，久坐不累。", "PENDING", "待审核", "bg-warning", "2026-05-23 10:02", 4, null));
        list.add(new AdminMockItem(4L, "wangwu", "大理石餐桌", "台面有轻微划痕，已联系卖家处理。", "HIDDEN", "已隐藏", "bg-secondary", "2026-05-20 18:40", 3, null));
        list.add(new AdminMockItem(5L, "user", "休闲躺椅", "阳台放着很惬意，性价比高。", "VISIBLE", "已展示", "bg-success", "2026-05-19 11:20", 5, null));
        return list;
    }

    public List<AdminMockItem> getMockAnnouncements() {
        List<AdminMockItem> list = new ArrayList<>();
        list.add(new AdminMockItem(1L, "五一家居焕新季", "全平台", "5月1日-5月7日精选家具满300减50。", "PUBLISHED", "已发布", "bg-success", "2026-04-28", null, "置顶"));
        list.add(new AdminMockItem(2L, "卖家入驻须知", "卖家端", "请完善店铺资质与售后政策说明。", "PUBLISHED", "已发布", "bg-success", "2026-05-10", null, null));
        list.add(new AdminMockItem(3L, "系统维护通知", "全平台", "5月25日凌晨2:00-4:00进行系统升级。", "DRAFT", "草稿", "bg-secondary", "2026-05-23", null, null));
        list.add(new AdminMockItem(4L, "新用户注册礼", "买家端", "注册即送满100减10优惠券（模拟）。", "PUBLISHED", "已发布", "bg-success", "2026-05-15", null, null));
        return list;
    }

    public List<Map<String, Object>> getMockSalesReport() {
        List<Map<String, Object>> rows = new ArrayList<>();
        rows.add(row("沙发", 128500, 86));
        rows.add(row("床", 98500, 62));
        rows.add(row("衣柜", 76200, 45));
        rows.add(row("餐桌", 65800, 38));
        rows.add(row("椅子", 42300, 120));
        return rows;
    }

    public List<Map<String, Object>> getMockMonthlyTrend() {
        List<Map<String, Object>> rows = new ArrayList<>();
        rows.add(trend("1月", 42));
        rows.add(trend("2月", 55));
        rows.add(trend("3月", 48));
        rows.add(trend("4月", 68));
        rows.add(trend("5月", 75));
        rows.add(trend("6月", 82));
        return rows;
    }

    public List<AdminMockItem> getMockOrders() {
        List<AdminMockItem> list = new ArrayList<>();
        list.add(new AdminMockItem(1L, "ORD20260523001", "user", "现代简约布艺沙发 x1", "PENDING", "待付款", "bg-warning", "2026-05-23 10:20", null, "2999.00"));
        list.add(new AdminMockItem(2L, "ORD20260522008", "zhangsan", "北欧实木床 x1", "PAID", "已付款", "bg-primary", "2026-05-22 16:45", null, "3599.00"));
        list.add(new AdminMockItem(3L, "ORD20260521003", "lisi", "人体工学办公椅 x2", "SHIPPED", "已发货", "bg-info", "2026-05-21 09:30", null, "1798.00"));
        list.add(new AdminMockItem(4L, "ORD20260520015", "user", "大理石餐桌 x1", "COMPLETED", "已完成", "bg-success", "2026-05-20 14:10", null, "5999.00"));
        list.add(new AdminMockItem(5L, "ORD20260519002", "wangwu", "休闲躺椅 x1", "CANCELLED", "已取消", "bg-secondary", "2026-05-19 11:05", null, "699.00"));
        return list;
    }

    public Map<String, String> getMockSettings() {
        Map<String, String> settings = new LinkedHashMap<>();
        settings.put("siteName", "家具商城");
        settings.put("siteSlogan", "品质家具，品味生活");
        settings.put("contactPhone", "400-123-4567");
        settings.put("contactEmail", "support@furniture.com");
        settings.put("orderAutoCancelMinutes", "30");
        settings.put("lowStockThreshold", "10");
        settings.put("maintenanceMode", "false");
        return settings;
    }

    private Map<String, Object> row(String category, int amount, int orders) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("category", category);
        m.put("amount", amount);
        m.put("orders", orders);
        m.put("percent", Math.min(100, orders));
        return m;
    }

    private Map<String, Object> trend(String month, int percent) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("month", month);
        m.put("percent", percent);
        return m;
    }
}
