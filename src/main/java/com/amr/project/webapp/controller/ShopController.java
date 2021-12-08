package com.amr.project.webapp.controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.service.impl.ShopServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
@Controller
@RequiredArgsConstructor
@Validated
@Api(tags = { "Операции с магазином (получение списка магазинов, получение магазина по ID, получение товара)"})
public class ShopController {

    private final ShopServiceImpl shopServiceImpl;
    private final ShopMapper shopMapper;
    private final ItemMapper itemMapper;


    @ApiOperation(value = "получение магазина по ID")
    @GetMapping(value = "/shopi/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "магазин найден по ID"),
            @ApiResponse(code = 404, message = "Магазин не найден по id")
    })
    public String shopPage(Model model,@PathVariable("id") Long id) {

        ShopDto shop = shopMapper.shopToDto(shopServiceImpl.getByKey(id));
        ItemDto ratingItem = shopServiceImpl.getTheMostRatingItem(shop.getItems());
        List<String> images = shopServiceImpl.convertListImages(ratingItem.getImages());
        String logo = shopServiceImpl.convertImage(shop.getLogo().get(0));

        model.addAttribute("shop", shop);
        model.addAttribute("images", images);
        model.addAttribute("logo", logo);
        model.addAttribute("ratingItem", ratingItem);

        return "shopPage/shop_page";
    }

    @ApiOperation(value = "получение товара магазина по ID")
    @GetMapping(value = "/shop/{id}/item/{itemId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Товар найден по ID"),
            @ApiResponse(code = 404, message = "Товар не найден по id")
    })
    public String itemPage(Model model,@PathVariable("id") Long shopId,@PathVariable("itemId") Long itemId) {

        ItemDto item = itemMapper.itemToDto(shopServiceImpl.getItemById(shopServiceImpl.getByKey(shopId).getItems(),itemId));

        List<String> image = shopServiceImpl.convertListImages(item.getImages());

        model.addAttribute("item",item);
        model.addAttribute("image", image);
        return "shopPage/shop_item_page";
    }

    @GetMapping("/shop/{id}")
    public String homeShopPage(@PathVariable("id") long id, Model model){
        model.addAttribute("shop", shopServiceImpl.getShopId(id));
        return "shopPage/home-shop-page";
    }



}
