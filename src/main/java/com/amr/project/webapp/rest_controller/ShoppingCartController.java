package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.CartItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.CartItemDto;
import com.amr.project.model.entity.CartItem;
import com.amr.project.service.abstracts.CartItemService;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@Api(tags = {"API для работы с корзиной покупок"})
@RequestMapping("/shoppingCart/")
public class ShoppingCartController {

    private final CartItemService cartItemService;
    private final ShopService shopService;
    private final UserService userService;
    private final CartItemMapper cartItemMapper;
    private final ShopMapper shopMapper;

    @ApiOperation(value = "Возвращает список товаров в корзине у пользователя, переданного через параметр Principal")
    @GetMapping("/getUserShoppingCart")
    public ResponseEntity<List<CartItemDto>> getUserShoppingCart(Principal principal) {
        if (principal != null) {
            List<CartItemDto> cartItemDtos = new ArrayList<>();
            List<CartItem> cartItems = cartItemService.getAllByUser(userService.findByUsername(principal.getName()));
            if (cartItems != null) {
                cartItems.forEach(cartItem -> cartItemDtos.add(cartItemMapper.cartItemToDto(cartItem)));
            }
            return ResponseEntity.ok(cartItemDtos);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation(value = "Сохраняет на сервере полученную корзину пользователя, переданного через Principal")
    @PostMapping("/loadLocalShoppingCartToServer")
    public ResponseEntity<Void> loadLocalShoppingCartToServer(@RequestBody Set<CartItemDto> localCart, Principal principal) {
        if (principal != null) {
            localCart.forEach(cartItemDto -> {
                CartItem cartItem = cartItemMapper.dtoToCartItem(cartItemDto);
                cartItem.setShop(shopService.getShop(shopMapper.dtoToShop(cartItemDto.getShopDto()).getName()));
                cartItem.setUser(userService.findByUsername(principal.getName()));
                cartItemService.persist(cartItem);
            });
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Добавляет новые позиции к корзине пользователя, переданного через Principal")
    @PostMapping("/addNewCartItem")
    public ResponseEntity<Void> addNewCartItem(@RequestBody CartItemDto cartItemDto, Principal principal) {
        if (principal != null) {
            CartItem cartItem = cartItemMapper.dtoToCartItem(cartItemDto);
            cartItem.setShop(shopService.getShop(shopMapper.dtoToShop(cartItemDto.getShopDto()).getName()));
            cartItem.setUser(userService.findByUsername(principal.getName()));
        } else {
           return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Обновляет количество переданной существующей позиции в корзине пользователя, переданного через Principal")
    @PutMapping("/updateQuantity")
    public ResponseEntity<Void> updateQuantityCartItem(@RequestBody CartItemDto cartItemDto, Principal principal) {
        if (principal != null) {
            CartItem cartItem = cartItemMapper.dtoToCartItem(cartItemDto);
            cartItem.setShop(shopService.getShop(shopMapper.dtoToShop(cartItemDto.getShopDto()).getName()));
            cartItem.setUser(userService.findByUsername(principal.getName()));
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Удаляет переданную позицию из корзины пользователя, переданного через Principal")
    @DeleteMapping("/deletePositionFromCart")
    public ResponseEntity<Void> deletePositionFromCart(@RequestBody CartItemDto cartItemDto, Principal principal) {
        if (principal != null) {
            CartItem cartItem = cartItemMapper.dtoToCartItem(cartItemDto);
            cartItem.setShop(shopService.getShop(shopMapper.dtoToShop(cartItemDto.getShopDto()).getName()));
            cartItem.setUser(userService.findByUsername(principal.getName()));
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
