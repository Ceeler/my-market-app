package ru.yandex.practicum.mymarket.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import ru.yandex.practicum.mymarket.entity.Item;
import ru.yandex.practicum.mymarket.entity.Order;
import ru.yandex.practicum.mymarket.entity.OrderItem;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Находит позиции заказа по идентификатору заказа")
    void findAllByOrderIdReturnsOrderItems() {
        Order order = orderRepository.save(new Order());
        Item item = itemRepository.save(buildItem("Item", 10L, 1));

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setItem(item);
        orderItem.setAmount(2);
        orderItemRepository.save(orderItem);

        assertThat(orderItemRepository.findAllByOrder_Id(order.getId()))
                .singleElement()
                .satisfies(oi -> {
                    assertThat(oi.getOrder().getId()).isEqualTo(order.getId());
                    assertThat(oi.getItem().getId()).isEqualTo(item.getId());
                    assertThat(oi.getAmount()).isEqualTo(2);
                });
    }

    private Item buildItem(String title, Long price, Integer count) {
        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        item.setCount(count);
        item.setDescription(title + " description");
        item.setImgPath("/img/" + title.toLowerCase());
        return item;
    }
}
