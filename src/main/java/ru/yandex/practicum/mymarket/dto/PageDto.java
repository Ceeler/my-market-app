package ru.yandex.practicum.mymarket.dto;

import lombok.Value;

import java.util.List;

@Value
public class PageDto<T> {

    List<T> data;

    PagingDto page;
}
