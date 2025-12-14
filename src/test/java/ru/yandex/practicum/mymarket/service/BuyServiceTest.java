package ru.yandex.practicum.mymarket.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.mymarket.entity.Item;
import ru.yandex.practicum.mymarket.entity.Order;
import ru.yandex.practicum.mymarket.exceptions.EmptyCartException;
import ru.yandex.practicum.mymarket.repository.ItemRepository;
import ru.yandex.practicum.mymarket.repository.OrderItemRepository;
import ru.yandex.practicum.mymarket.repository.OrderRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class BuyServiceTest {

    @Autowired
    private BuyService buyService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @BeforeEach
    void setUp() {
        orderItemRepository.deleteAll();
        orderRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    @DisplayName("Выбрасывает исключение, если корзина пуста")
    void buyThrowsWhenCartEmpty() {
        assertThatThrownBy(() -> buyService.buy())
                .isInstanceOf(EmptyCartException.class)
                .hasMessageContaining("Корзина пуста");
    }

    @Test
    @DisplayName("Создает заказ и обнуляет количество товаров в корзине")
    void buyCreatesOrderAndClearsCart() {
        Item bread = itemRepository.save(buildItem("Bread", 50L, 2));
        Item milk = itemRepository.save(buildItem("Milk", 120L, 1));

        Order order = buyService.buy();

        assertThat(orderRepository.findById(order.getId())).isPresent();
        assertThat(orderItemRepository.findAllByOrder_Id(order.getId())).hasSize(2);

        assertThat(itemRepository.findById(bread.getId()).orElseThrow().getCount()).isZero();
        assertThat(itemRepository.findById(milk.getId()).orElseThrow().getCount()).isZero();
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
