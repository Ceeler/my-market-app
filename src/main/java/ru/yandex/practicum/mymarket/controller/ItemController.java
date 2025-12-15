package ru.yandex.practicum.mymarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.yandex.practicum.mymarket.dto.ItemDto;
import ru.yandex.practicum.mymarket.dto.request.ItemsPostRequest;
import ru.yandex.practicum.mymarket.dto.request.ItemsRequest;
import ru.yandex.practicum.mymarket.enums.Action;
import ru.yandex.practicum.mymarket.service.ItemService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public String getItems(@ModelAttribute ItemsRequest request, Model model) {

        var itemsPage = itemService.getItems(request);

        var items = itemsPage.getData();

        List<List<ItemDto>> itemsList = new ArrayList<>();
        for (int i = 0; i < items.size(); i+=3) {
            itemsList.add(items.subList(i, Math.min(i + 3, items.size())));
        }

        model.addAttribute("items", itemsList);
        model.addAttribute("search", request.getSearch());
        model.addAttribute("sort", request.getSort());
        model.addAttribute("paging", itemsPage.getPage());
        return "items";
    }

    @PostMapping
    public String postItems(@ModelAttribute ItemsPostRequest request, RedirectAttributes ra) {
        itemService.addItem(request.getId(), request.getAction());

        ra.addAllAttributes(request.getAsMap());
        return "redirect:/items";
    }

    @GetMapping("/{id}")
    public String getItem(@PathVariable Long id, Model model) {
        var item = itemService.getItem(id);

        model.addAttribute("item", item);
        return "item";
    }

    @PostMapping("/{id}")
    public String postItem(@PathVariable Long id, @RequestParam Action action,
                           Model model) {

        var item = itemService.addItem(id, action);

        model.addAttribute("item", item);
        return "item";
    }
}

