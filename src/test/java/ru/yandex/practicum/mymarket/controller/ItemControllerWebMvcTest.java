package ru.yandex.practicum.mymarket.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.dto.PageDto;
import ru.yandex.practicum.mymarket.dto.PagingDto;
import ru.yandex.practicum.mymarket.dto.request.ItemsRequest;
import ru.yandex.practicum.mymarket.enums.Action;
import ru.yandex.practicum.mymarket.service.ItemService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ItemController.class)
class ItemControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    @Test
    @DisplayName("Отображает список товаров и передает атрибуты в модель")
    void getItemsReturnsViewWithModel() throws Exception {
        List<ItemDto> items = List.of(
                ItemDto.builder().id(1L).title("Banana").price(100L).count(1).build(),
                ItemDto.builder().id(2L).title("Apple").price(50L).count(2).build()
        );
        when(itemService.getItems(any(ItemsRequest.class)))
                .thenReturn(new PageDto<>(items, new PagingDto(5, 0, false, false)));

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(view().name("items"))
                .andExpect(model().attributeExists("items", "paging", "sort"));
    }

    @Test
    @DisplayName("Обрабатывает отправку формы и делает редирект на список товаров")
    void postItemsRedirectsToList() throws Exception {
        mockMvc.perform(post("/items")
                        .param("id", "1")
                        .param("action", Action.PLUS.name())
                        .param("pageNumber", "0")
                        .param("pageSize", "5")
                        .param("sort", ItemsRequest.Sort.NO.name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/items?pageNumber=0&pageSize=5&sort=NO"));

        verify(itemService).addItem(1L, Action.PLUS);
    }
}
