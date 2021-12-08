package com.amr.project.converter;
import com.amr.project.model.dto.FavoriteDto;
import com.amr.project.model.entity.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        ItemMapper.class, ShopMapper.class})
public interface FavoriteMapper {

    @Mapping(source = "items", target = "itemDto")
    @Mapping(source = "shops", target = "shopDto")
    FavoriteDto favoriteToDto(Favorite favorite);

    @Mapping(source = "itemDto", target = "items")
    @Mapping(source = "shopDto", target = "shops")
    Favorite dtoToFavorite(FavoriteDto favoriteDto);
}
