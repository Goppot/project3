package com.amr.project.converter;

import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = UserMapper.class)
public interface ShopMapper {
    @Mapping(target = "username" , source ="user.username")
    ShopDto shopToDto(Shop shop);

    @Mapping(target = "user.username" , source ="username")
    Shop dtoToShop(ShopDto shopDto);

}
