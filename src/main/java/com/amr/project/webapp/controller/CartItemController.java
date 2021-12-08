package com.amr.project.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class CartItemController {

    @GetMapping("/basket")
    public String getCartItem(){
        return "shopPage/home-shop-page";
    }
}
