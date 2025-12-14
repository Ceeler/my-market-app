package ru.yandex.practicum.mymarket.mapper;

import ru.yandex.practicum.mymarket.dto.ItemShortDto;
import ru.yandex.practicum.mymarket.dto.OrderDto;
import ru.yandex.practicum.mymarket.entity.OrderItem;

import java.util.ArrayList;
import java.util.List;

public final class OrderMapper {

    public static OrderDto toDto(List<OrderItem> orderItems, Long orderId) {
        long total = 0L;
        List<ItemShortDto> items = new ArrayList<>(orderItems.size());
        for (var orderItem : orderItems) {
            total = total + orderItem.getItem().getPrice() * orderItem.getAmount();
            items.add(ItemMapper.toShortDto(orderItem.getItem(), orderItem.getAmount()));
        }

        return new OrderDto(orderId, items, total);
    }

}
