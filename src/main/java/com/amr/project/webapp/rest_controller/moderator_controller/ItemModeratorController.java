package com.amr.project.webapp.rest_controller.moderator_controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Item;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/moderator/api/items")
@Api(tags = {"API для работы с товарами на странице модератора"})
public class ItemModeratorController {
    private final ItemService itemService;
    private final ShopService shopService;
    private final ItemMapper itemMapper;

    @Autowired
    public ItemModeratorController(ItemService itemService, ShopService shopService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.shopService = shopService;
        this.itemMapper = itemMapper;
    }

    @ApiOperation(value = "Отправляет все товары не прошедшие модерацию на фронт")
    @GetMapping("/getUnmoderatedItems")
    public ResponseEntity<List<ItemDto>> getUnmoderatedItems() {
        return ResponseEntity.ok(
                itemService.getUnmoderatedItems()
                        .stream()
                        .map(itemMapper::itemToDto)
                        .collect(Collectors.toList()));
    }

    @ApiOperation(value = "Отправляет один не прошедший модерацию товар на фронт по id")
    @GetMapping("/getOneUnmoderatedItem/{id}")
    public ResponseEntity<ItemDto> getOneUnmoderatedItem(@PathVariable("id") Long id) {
        Item item = itemService.getByKey(id);
        if (item == null || item.isModerated()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(itemMapper.itemToDto(item));
    }
    @ApiOperation(value = "Получает измененный товар из фронта и обновляет в базе данных")
    @PutMapping("/editItem")
    public ResponseEntity<ItemDto> editItem(@RequestBody ItemDto itemDto) {
        if (itemService.existsById(itemDto.getId())) {
            Item item = itemMapper.dtoToItem(itemDto);
            item.setShop(shopService.getByKey(itemDto.getShopId()));
            itemService.update(item);
            return ResponseEntity.ok(itemMapper.itemToDto(item));
        }
        return ResponseEntity.badRequest().build();
    }
    @ApiOperation(value = "возвращает на фронт количество не прошедних модерацию товаров для счетчика")
    @GetMapping("/getUnmoderatedItemsCount")
    public ResponseEntity<Long> getUnmoderatedItemsCount() {
        int size = itemService.getUnmoderatedItems().size();
        return ResponseEntity.ok((long) size);
    }
}
