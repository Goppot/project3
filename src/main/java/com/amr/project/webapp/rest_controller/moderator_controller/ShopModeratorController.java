package com.amr.project.webapp.rest_controller.moderator_controller;

import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"API для работы с магазинами на странице модератора"})
@RestController
@RequestMapping("moderator/api/shops")
public class ShopModeratorController {
    private final ShopService shopService;
    private final ShopMapper shopMapper;

    @Autowired
    public ShopModeratorController(ShopService shopService, ShopMapper shopMapper) {
        this.shopService = shopService;
        this.shopMapper = shopMapper;

    }

    @ApiOperation(value = "Отправляет все магазины не прошедшие модерацию на фронт")
    @GetMapping("/getUnmoderatedShops")
    public ResponseEntity<List<ShopDto>> getUnmoderatedShops() {
        return ResponseEntity.ok(
                shopService
                        .getUnmoderatedShops()
                        .stream().map(shopMapper::shopToDto)
                        .collect(Collectors.toList()));
    }

    @ApiOperation(value = "Отправляет один не прошедший модерацию магазин на фронт по id")
    @GetMapping("/getOneUnmoderatedShop/{id}")
    public ResponseEntity<ShopDto> getOneUnmoderatedItem(@PathVariable("id") Long id) {
        return shopService.getByKey(id).isModerated() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(shopMapper.shopToDto(shopService.getByKey(id)));
    }

    @ApiOperation(value = "Получает измененный магазин из фронта и обновляет в базе данных")
    @PutMapping("/editShop")
    public ResponseEntity<ShopDto> editItem(@RequestBody ShopDto shopDto) {
        Shop shop = shopMapper.dtoToShop(shopDto);
        shopService.update(shop);
        return ResponseEntity.ok(shopMapper.shopToDto(shop));
    }

    @ApiOperation(value = "возвращает на фронт количество не прошедних модерацию магазинов для счетчика")
    @GetMapping("/getUnmoderatedShopsCount")
    public ResponseEntity<Long> getUnmoderatedItemsCount() {
        return ResponseEntity.ok((long) shopService
                .getUnmoderatedShops()
                .size());
    }
}