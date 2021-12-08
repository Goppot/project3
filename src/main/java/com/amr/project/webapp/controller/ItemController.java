package com.amr.project.webapp.controller;

import com.amr.project.service.abstracts.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    //8888
    @GetMapping("/{id}")
    public String getItem(@PathVariable("id") long id, Model model){
        model.addAttribute("item", itemService.getItemDtoId(id));
        return "shopPage/home-shop-page";
    }
}
