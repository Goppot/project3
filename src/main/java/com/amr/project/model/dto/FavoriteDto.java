package com.amr.project.model.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class FavoriteDto {
    private Long id;
    private List<ItemDto> itemDto;
    private List<ShopDto> shopDto;
}
