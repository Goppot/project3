package com.amr.project.model.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class DiscountDto {

    private Long id;

    private Integer minOrder;

    private Integer percentage;

    private Integer fixedDiscount;

    private ShopDto shop;

    private UserDto user;
}
