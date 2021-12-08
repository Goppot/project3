package com.amr.project.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class ItemDto {
    private Long id;
    private String name;
    private Integer count;
    private String[] categoriesName;
    private BigDecimal price;
    private List<ImageDto> images;
    private Double rating;
    private String description;
    private String shopName;
    private boolean isModerateAccept;
    private boolean isModerated;
    private boolean isPretendentToBeDeleted;
    private String moderatedRejectReason;
    private List<CategoryDto> categories;
    private List<ReviewDto> reviews;
    private Long shopId;
}
