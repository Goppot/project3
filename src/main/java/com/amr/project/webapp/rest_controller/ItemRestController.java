package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Item;
import com.amr.project.service.abstracts.CategoryService;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestControllerAdvice
@RestController
@RequiredArgsConstructor
@Api(tags = {"API для работы с товаром"})
@RequestMapping("shop/")
public class ItemRestController {

    private final CategoryService categoryService;
    private final ShopService shopService;
    private final ItemService itemService;
    private final ItemMapper itemConverter;

    @ApiOperation(value = "Сохраняет объект Item")
    @PostMapping("item")
    public ResponseEntity<?> addItem(@RequestBody ItemDto itemDto) {
        Item item = itemConverter.dtoToItem(itemDto);
        if (itemDto.getShopName() == null || itemDto.getCategoriesName() == null)
            return ResponseEntity.badRequest().body("empty shop");
//        item.setCategories(Arrays.stream(itemDto.getCategoriesName()).map(category -> categoryService.getCategory(category)).collect(Collectors.toList()));
        item.setShop(shopService.getShop(itemDto.getShopName()));
        itemService.persist(item);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Удаляет объект Item по id")
    @DeleteMapping("item/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable @NonNull Long id) {
        Item item = itemService.getByKey(id);
        if (item == null)
            return ResponseEntity.badRequest().body("empty item");
        item.setPretendentToBeDeleted(true);
        itemService.update(item);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Обновляет объект Item")
    @PutMapping("item")
    public ResponseEntity<ItemDto> updateItem(@RequestBody @NonNull ItemDto itemDto) {
        Item item = itemConverter.dtoToItem(itemDto);
        if (itemDto.getId() == null || itemDto.getShopName() == null || itemDto.getCategoriesName() == null)
            return ResponseEntity.badRequest().build();
        item.setId(itemDto.getId());
        item.setShop(shopService.getShop(itemDto.getShopName()));
        item.setCategories(Arrays.stream(itemDto.getCategoriesName())
                .map(category -> categoryService.getCategory(category))
                .collect(Collectors.toList()));
        itemService.update(item);
        return ResponseEntity.ok().body(itemConverter.itemToDto(item));
    }

    @GetMapping("item/{id}")
    public ResponseEntity<ItemDto> getItem(@PathVariable @NonNull Long id) {
        Item item = itemService.getByKey(id);
        return ResponseEntity.ok().body(itemConverter.itemToDto(item));
    }

//    @GetMapping("item/{id}")
//    public ItemDto getItem(@PathVariable("id") long id){
//        ItemDto itemDto = itemService.getItemDtoId(id);
//        return itemDto;
//    }
    @GetMapping("/items")
    public List<ItemDto> getAllItems(){
        List<ItemDto> itemDtoList = itemService.getAllItemsRatingSort();
        return itemDtoList;
    }
}

