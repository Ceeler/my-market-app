package ru.yandex.practicum.mymarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mymarket.dto.ItemShortDto;
import ru.yandex.practicum.mymarket.dto.OrderDto;
import ru.yandex.practicum.mymarket.mapper.ItemMapper;
import ru.yandex.practicum.mymarket.mapper.OrderMapper;
import ru.yandex.practicum.mymarket.repository.OrderItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderItemRepository orderItemIdRepository;

    public List<OrderDto> getOrders() {
        return orderItemIdRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        o -> o.getOrder().getId()
                ))
                .entrySet().stream()
                .map(kv -> OrderMapper.toDto(kv.getValue(), kv.getKey()))
                .toList();
    }

    public Object getOrder(Long id) {
        var orderItems = orderItemIdRepository.findAllByOrder_Id(id);

        return OrderMapper.toDto(orderItems, id);
    }
}
