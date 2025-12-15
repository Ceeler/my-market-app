package ru.yandex.practicum.mymarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.yandex.practicum.mymarket.service.BuyService;

@Controller
@RequestMapping("/buy")
@RequiredArgsConstructor
public class BuyController {

    private final BuyService buyService;

    @PostMapping
    public String postBuy(RedirectAttributes ra) {
        var order = buyService.buy();

        ra.addAttribute("newOrder", true);
        ra.addAttribute("id", order.getId());
        return "redirect:/orders/{id}";
    }

}

