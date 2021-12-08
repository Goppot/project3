package com.amr.project.webapp.rest_controller;

import com.amr.project.model.entity.CartItem;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.CartItemService;
import com.amr.project.service.abstracts.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart-item")
public class CartItemRestController {

    private final CartItemService cartItemService;
    private final ItemService itemService;


    public CartItemRestController(CartItemService cartItemService, ItemService itemService) {
        this.cartItemService = cartItemService;
        this.itemService = itemService;
    }

    @PostMapping("")
    public List<Item> getCartItems(@RequestBody User user){
        List<Item> items = itemService.getAllItem(user);
        return items;
    }

    @GetMapping("/user")
    public List<CartItem> getUserCartItem(){
        List<CartItem> cartItemList = cartItemService.getCartItemByUserAuthorized();
        return cartItemList;
    }
    @PostMapping("/user")
    public ResponseEntity addCookiesCartItem(@RequestBody User user){
        cartItemService.addCookieToCartItem(user);
        return  new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/plus/{id}")
    public ResponseEntity plusCartItem(@PathVariable("id") Long id){
        cartItemService.plusCartItem(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/minus/{id}")
    public ResponseEntity minusCartItem(@PathVariable("id") Long id){
        cartItemService.minusCartItem(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/add/item/{id}")
    public ResponseEntity addItemToCart(@PathVariable("id") Long id){
        cartItemService.addItemToCart(id);
        return new ResponseEntity(HttpStatus.OK);
    }






}
