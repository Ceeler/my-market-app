package ru.yandex.practicum.mymarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.dto.PageDto;
import ru.yandex.practicum.mymarket.dto.PagingDto;
import ru.yandex.practicum.mymarket.dto.request.ItemsRequest;
import ru.yandex.practicum.mymarket.entity.Item;
import ru.yandex.practicum.mymarket.enums.Action;
import ru.yandex.practicum.mymarket.mapper.ItemMapper;
import ru.yandex.practicum.mymarket.repository.ItemRepository;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public PageDto<ItemDto> getItems(ItemsRequest itemsRequest) {
        var page = switch (itemsRequest.getSort()) {
            case NO -> PageRequest.of(itemsRequest.getPageNumber(), itemsRequest.getPageSize());
            case ALPHA -> PageRequest.of(itemsRequest.getPageNumber(), itemsRequest.getPageSize(), Sort.by("title"));
            case PRICE -> PageRequest.of(itemsRequest.getPageNumber(), itemsRequest.getPageSize(), Sort.by("price"));
        };

        Page<Item> items;
        if (itemsRequest.getSearch() != null) {
            items = itemRepository.findAllByTitleContainsIgnoreCase(itemsRequest.getSearch(), page);
        } else {
            items = itemRepository.findAll(page);
        }

        return new PageDto<>(items.get()
                                .map(ItemMapper::toDto)
                                .toList(),
                        new PagingDto(items.getSize(), items.getNumber(), items.hasPrevious(), items.hasNext()));
    }

    public ItemDto addItem(Long id, Action action) {
        var item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item bot found"));
        int amount = item.getCount();

        switch (action) {
            case PLUS -> item.setCount(amount + 1);
            case MINUS -> {
                if (amount > 0) item.setCount(amount - 1);
            }
        }

        item = itemRepository.save(item);
        return ItemMapper.toDto(item);
    }

    public ItemDto getItem(Long id) {
        var item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item bot found"));

        return ItemMapper.toDto(item);
    }

}
