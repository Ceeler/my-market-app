package ru.yandex.practicum.mymarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mymarket.dto.CartDto;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.mapper.ItemMapper;
import ru.yandex.practicum.mymarket.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService {

    private final ItemRepository itemRepository;

    public CartDto getCartItems() {
        var items = itemRepository.findAllCartItems();

        Long total = 0L;
        List<ItemDto> dtos = new ArrayList<>(items.size());
        for (var i : items) {
            total = total + (i.getPrice() * i.getCount());
            dtos.add(ItemMapper.toDto(i));
        }

        return new CartDto(dtos, total);
    }

}
