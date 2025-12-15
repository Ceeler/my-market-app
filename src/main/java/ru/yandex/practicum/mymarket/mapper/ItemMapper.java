package ru.yandex.practicum.mymarket.mapper;

import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.dto.ItemShortDto;
import ru.yandex.practicum.mymarket.entity.Item;

public final class ItemMapper {

    public static ItemDto toDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .title(item.getTitle())
                .price(item.getPrice())
                .count(item.getCount())
                .description(item.getDescription())
                .imgPath(item.getImgPath())
                .build();
    }

    public static ItemShortDto toShortDto(Item item, Integer count) {
        return ItemShortDto.builder()
                .id(item.getId())
                .title(item.getTitle())
                .price(item.getPrice())
                .count(count)
                .build();
    }

}
