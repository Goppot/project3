package com.amr.project.converter;

import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mappings({
            @Mapping(source = "user.lastName", target = "userLastName"),
            @Mapping(source = "user.firstName", target = "userFirstName"),
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "item.id", target = "itemId"),
            @Mapping(source = "item.name", target = "itemName"),
            @Mapping(source = "shop.id", target = "shopId"),
            @Mapping(source = "shop.name", target = "shopName")
    })
    ReviewDto reviewToDto(Review review);

    @Mappings({
            @Mapping(source = "userLastName", target = "user.lastName"),
            @Mapping(source = "userFirstName", target = "user.firstName"),
            @Mapping(source = "userId", target = "user.id"),
            @Mapping(source = "itemId", target = "item.id"),
            @Mapping(source = "itemName", target = "item.name"),
            @Mapping(source = "shopId", target = "shop.id"),
            @Mapping(source = "shopName", target = "shop.name")
    })
    Review dtoToReview(ReviewDto reviewDto);

}
