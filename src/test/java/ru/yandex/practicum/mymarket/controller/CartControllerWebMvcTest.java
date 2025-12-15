package ru.yandex.practicum.mymarket.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.mymarket.dto.CartDto;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.enums.Action;
import ru.yandex.practicum.mymarket.service.CartService;
import ru.yandex.practicum.mymarket.service.ItemService;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(CartController.class)
class CartControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CartService cartService;

    @MockitoBean
    private ItemService itemService;

    @Test
    @DisplayName("Редиректит на список товаров в корзине")
    void getCartRedirectsToItems() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart/items"));
    }

    @Test
    @DisplayName("Отображает товары корзины")
    void getCartItemsReturnsView() throws Exception {
        CartDto cartDto = new CartDto(List.of(ItemDto.builder().id(1L).title("Milk").count(1).price(100L).build()), 100L);
        when(cartService.getCartItems()).thenReturn(cartDto);

        mockMvc.perform(get("/cart/items"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andExpect(model().attribute("items", cartDto.items()))
                .andExpect(model().attribute("total", cartDto.total()));
    }

    @Test
    @DisplayName("Обновляет количество товаров в корзине и возвращает представление")
    void postCartItemsUpdatesCart() throws Exception {
        CartDto cartDto = new CartDto(List.of(), 0L);
        when(cartService.getCartItems()).thenReturn(cartDto);

        mockMvc.perform(post("/cart/items")
                        .param("id", "5")
                        .param("action", Action.MINUS.name()))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andExpect(model().attribute("items", cartDto.items()))
                .andExpect(model().attribute("total", cartDto.total()));

        verify(itemService).addItem(5L, Action.MINUS);
    }
}
