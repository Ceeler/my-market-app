package ru.yandex.practicum.mymarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mymarket.entity.Order;
import ru.yandex.practicum.mymarket.entity.OrderItem;
import ru.yandex.practicum.mymarket.entity.OrderItemId;
import ru.yandex.practicum.mymarket.exceptions.EmptyCartException;
import ru.yandex.practicum.mymarket.repository.ItemRepository;
import ru.yandex.practicum.mymarket.repository.OrderItemRepository;
import ru.yandex.practicum.mymarket.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class BuyService {

    private final ItemRepository itemRepository;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    public Order buy() {
        var cartItems = itemRepository.findAllCartItems();

        if (cartItems.isEmpty()) throw new EmptyCartException("Корзина пуста!");
        var order = orderRepository.save(new Order());

        var orderItems = cartItems.stream()
                .map(i -> {
                    var oi = new OrderItem();
                    oi.setItem(i);
                    oi.setOrder(order);
                    oi.setAmount(i.getCount());

                    // Обнуляем к-во предметов в корзине
                    i.setCount(0);

                    return oi;
                })
                .toList();

        orderItemRepository.saveAll(orderItems);

        return order;
    }

}
