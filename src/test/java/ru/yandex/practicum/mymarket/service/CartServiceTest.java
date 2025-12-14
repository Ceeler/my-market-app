package ru.yandex.practicum.mymarket.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.mymarket.dto.CartDto;
import ru.yandex.practicum.mymarket.entity.Item;
import ru.yandex.practicum.mymarket.repository.ItemRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();

        itemRepository.save(buildItem("Milk", 120L, 2));
        itemRepository.save(buildItem("Bread", 80L, 1));
        itemRepository.save(buildItem("Cheese", 300L, 0));
    }

    @Test
    @DisplayName("Возвращает товары корзины и корректно считает сумму")
    void getCartItemsCalculatesTotal() {
        CartDto cartDto = cartService.getCartItems();

        assertThat(cartDto.items()).hasSize(2);
        assertThat(cartDto.total()).isEqualTo(320L);
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
