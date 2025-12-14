package ru.yandex.practicum.mymarket.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import ru.yandex.practicum.mymarket.entity.Item;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("Находит только товары с количеством больше нуля для корзины")
    void findAllCartItemsReturnsOnlyAvailableItems() {
        itemRepository.save(buildItem("Available", 100L, 2));
        itemRepository.save(buildItem("Zero", 100L, 0));

        assertThat(itemRepository.findAllCartItems())
                .extracting(Item::getTitle)
                .containsExactly("Available");
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
