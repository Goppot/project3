package com.amr.project.converter;

import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;


@Mapper(componentModel = "spring", uses = {
            ImageMapper.class,
            ReviewMapper.class})
    public interface ItemMapper {

    @Mapping(source = "shop.id", target = "shopId")
    @Mapping(source = "shop.name", target = "shopName")
    ItemDto itemToDto(Item item);

    @Mapping(source = "shopId", target = "shop.id")
    @Mapping(source = "shopName", target = "shop.name")
    Item dtoToItem(ItemDto itemDto);

        Collection<Item> toItems(List<ItemDto> items);
    }
