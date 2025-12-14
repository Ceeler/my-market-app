package ru.yandex.practicum.mymarket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemId {

    @Column(name = "order_id", insertable = false, updatable = false)
    private Long orderId;

    @Column(name = "item_id", insertable = false, updatable = false)
    private Long itemId;

}