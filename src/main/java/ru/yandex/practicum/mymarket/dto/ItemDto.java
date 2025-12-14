package ru.yandex.practicum.mymarket.dto;

import lombok.Builder;

/**
 * DTO for {@link ru.yandex.practicum.mymarket.entity.Item}
 */
@Builder
public record ItemDto(Long id, String title, String description, String imgPath, Long price, Integer count) {
}