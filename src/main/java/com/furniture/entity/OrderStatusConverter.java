package com.furniture.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class OrderStatusConverter implements AttributeConverter<Order.OrderStatus, String> {

    @Override
    public String convertToDatabaseColumn(Order.OrderStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public Order.OrderStatus convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return Order.OrderStatus.PENDING;
        }

        String normalized = dbData.trim();
        switch (normalized) {
            case "1":
                return Order.OrderStatus.PENDING;
            case "2":
                return Order.OrderStatus.SHIPPED;
            case "3":
                return Order.OrderStatus.COMPLETED;
            case "4":
                return Order.OrderStatus.CANCELLED;
            case "5":
                return Order.OrderStatus.REFUNDING;
            case "6":
                return Order.OrderStatus.REFUNDED;
            default:
                return Order.OrderStatus.valueOf(normalized);
        }
    }
}
