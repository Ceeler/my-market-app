package ru.yandex.practicum.mymarket.dto;

public record PagingDto(

        Integer pageSize,

        Integer pageNumber,

        boolean hasPrevious,

        boolean hasNext
) {
}
