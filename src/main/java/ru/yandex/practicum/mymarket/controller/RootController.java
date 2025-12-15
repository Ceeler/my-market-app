package ru.yandex.practicum.mymarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.yandex.practicum.mymarket.dto.request.ItemsRequest;

@Controller
public class RootController {

    @GetMapping
    public String root(ItemsRequest request, RedirectAttributes ra) {
        ra.addAllAttributes(request.getAsMap());
        return "redirect:/items";
    }

}
