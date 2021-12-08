package com.amr.project.converter;

import com.amr.project.model.dto.CartItemDto;
import com.amr.project.model.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        ItemMapper.class, ShopMapper.class})
public interface CartItemMapper {

    @Mapping(source = "item", target = "itemDto")
    @Mapping(source = "shop", target = "shopDto")
    CartItemDto cartItemToDto(CartItem cartItem);

    @Mapping(source = "itemDto", target = "item")
    @Mapping(source = "shopDto", target = "shop")
    CartItem dtoToCartItem(CartItemDto cartItemDto);
}
