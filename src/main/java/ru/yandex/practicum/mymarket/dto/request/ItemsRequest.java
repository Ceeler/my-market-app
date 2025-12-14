package ru.yandex.practicum.mymarket.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ItemsRequest {

    private String search = null;

    private Sort sort = Sort.NO;

    @Positive
    private Integer pageNumber = 0;

    @Min(1)
    private Integer pageSize = 5;

    public Map<String, Object> getAsMap() {
        Map<String, Object> map = new HashMap<>();
        if (search != null) map.put("search", search);
        map.put("sort", sort);
        map.put("pageNumber", pageNumber);
        map.put("pageSize", pageSize);
        return map;
    }

    public enum Sort {
        NO, ALPHA, PRICE
    }
}
