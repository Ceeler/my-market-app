package ru.yandex.practicum.mymarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.mymarket.enums.Action;
import ru.yandex.practicum.mymarket.service.CartService;
import ru.yandex.practicum.mymarket.service.ItemService;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final ItemService itemService;

    @GetMapping
    public String getCart() {
        return "redirect:/cart/items";
    }

    @GetMapping("/items")
    public String getCartItems(Model model) {
        var cart = cartService.getCartItems();

        model.addAttribute("items", cart.items());
        model.addAttribute("total", cart.total());
        return "cart";
    }

    @PostMapping("/items")
    public String postCartItems(@RequestParam Long id, @RequestParam Action action,
            Model model) {
        itemService.addItem(id, action);
        var cart = cartService.getCartItems();

        model.addAttribute("items", cart.items());
        model.addAttribute("total", cart.total());
        return "cart";
    }

}

