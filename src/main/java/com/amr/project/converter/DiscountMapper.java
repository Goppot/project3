package com.amr.project.converter;

import com.amr.project.model.dto.DiscountDto;
import com.amr.project.model.entity.Discount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {UserMapper.class,ShopMapper.class})
public interface DiscountMapper {

    DiscountDto discountToDiscountDto(Discount discount);

    Discount discountDtoToDiscount(DiscountDto discountDto);
}
