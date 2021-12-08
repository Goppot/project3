package com.amr.project.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReviewDto {
    private Long id;

    @NotBlank(message = "Введите отзыв для товара")
    private String text;

    @Min(value = 1, message = "Минимальная оценка 1")
    @Max(value = 5, message = "Максимальная оценка 5")
    private int rating;

    @NotBlank(message = "Введите плюсы для товара")
    private String dignity; //плюсы

    @NotBlank(message = "Введите минусы для товара")
    private String flaw; //минусы

    private Date date;
    private List<ImageDto> logo;
    private Long userId;
    private String userFirstName;
    private String userLastName;
    private Long itemId;
    private String itemName;
    private Long shopId;
    private String shopName;
    private boolean isModerated;
    private boolean isModerateAccept;
    private String moderatedRejectReason;

}
