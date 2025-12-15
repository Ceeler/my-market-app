package ru.yandex.practicum.mymarket.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.dto.PageDto;
import ru.yandex.practicum.mymarket.dto.PagingDto;
import ru.yandex.practicum.mymarket.dto.request.ItemsRequest;
import ru.yandex.practicum.mymarket.entity.Item;
import ru.yandex.practicum.mymarket.enums.Action;
import ru.yandex.practicum.mymarket.repository.ItemRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();

        itemRepository.save(buildItem("Banana", 200L, 3));
        itemRepository.save(buildItem("apple", 100L, 0));
        itemRepository.save(buildItem("Carrot", 50L, 1));
    }

    @Test
    @DisplayName("Возвращает отсортированный список товаров без фильтра поиска")
    void getItemsReturnsSortedList() {
        ItemsRequest request = new ItemsRequest();
        request.setSort(ItemsRequest.Sort.ALPHA);
        request.setPageSize(5);

        PageDto<ItemDto> result = itemService.getItems(request);

        assertThat(result.getData())
                .extracting(ItemDto::title)
                .containsExactly("Banana", "Carrot", "apple");
        assertThat(result.getPage()).isEqualTo(new PagingDto(5, 0, false, false));
    }

    @Test
    @DisplayName("Фильтрует товары по поисковой строке")
    void getItemsAppliesSearchFilter() {
        ItemsRequest request = new ItemsRequest();
        request.setSort(ItemsRequest.Sort.NO);
        request.setSearch("app");

        PageDto<ItemDto> result = itemService.getItems(request);

        assertThat(result.getData())
                .extracting(ItemDto::title)
                .containsExactly("apple");
    }

    @Test
    @DisplayName("Изменяет количество товара при добавлении и удалении")
    void addItemUpdatesItemCount() {
        Item existing = itemRepository.findAll().stream()
                .filter(i -> "Banana".equals(i.getTitle()))
                .findFirst()
                .orElseThrow();

        ItemDto increased = itemService.addItem(existing.getId(), Action.PLUS);
        assertThat(increased.count()).isEqualTo(existing.getCount() + 1);

        ItemDto decreased = itemService.addItem(existing.getId(), Action.MINUS);
        assertThat(decreased.count()).isEqualTo(existing.getCount());
    }

    @Test
    @DisplayName("Получает информацию о конкретном товаре")
    void getItemReturnsDto() {
        Item existing = itemRepository.findAll().getFirst();

        ItemDto dto = itemService.getItem(existing.getId());

        assertThat(dto.id()).isEqualTo(existing.getId());
        assertThat(dto.title()).isEqualTo(existing.getTitle());
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
