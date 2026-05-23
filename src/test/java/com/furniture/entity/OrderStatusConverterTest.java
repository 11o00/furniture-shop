package com.furniture.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderStatusConverterTest {

    private final OrderStatusConverter converter = new OrderStatusConverter();

    @Test
    void convertsLegacyNumericStatuses() {
        assertEquals(Order.OrderStatus.PENDING, converter.convertToEntityAttribute("1"));
        assertEquals(Order.OrderStatus.SHIPPED, converter.convertToEntityAttribute("2"));
        assertEquals(Order.OrderStatus.COMPLETED, converter.convertToEntityAttribute("3"));
        assertEquals(Order.OrderStatus.CANCELLED, converter.convertToEntityAttribute("4"));
    }

    @Test
    void keepsEnumStringStatusesWorking() {
        assertEquals(Order.OrderStatus.PAID, converter.convertToEntityAttribute("PAID"));
        assertEquals("SHIPPED", converter.convertToDatabaseColumn(Order.OrderStatus.SHIPPED));
    }
}
