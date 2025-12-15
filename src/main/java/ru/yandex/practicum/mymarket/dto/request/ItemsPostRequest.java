package ru.yandex.practicum.mymarket.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.yandex.practicum.mymarket.enums.Action;

@Data
public class ItemsPostRequest extends ItemsRequest {

    @NotNull
    private final Long id;

    @NotNull
    private final Action action;

    public ItemsPostRequest(Long id, Action action) {
        this.id = id;
        this.action = action;
    }

}
