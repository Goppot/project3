package com.amr.project.model.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class ShopDto {

    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Введите имя магазина")
    private String name;

    @NotBlank
    @Email(message = "Введите корректный email")
    private String email;

    @Length(min = 11, max = 12, message = "Длина номера должна быть 11 либо 12 сиволов")
    private String phone;

    @NotBlank(message = "Введите описание магазина")
    private String description;

    private CountryDto location;
    private List<ItemDto> items;

    @NotEmpty(message = "Выберите изображение")
    private List<ImageDto> logo;

    private double rating;
    private String username;
    private boolean isModerated;
    private boolean isModerateAccept;
    private String moderatedRejectReason;
    private boolean isPretendentToBeDeleted;
    private List<ReviewDto> reviews;
    private List<OrderDto> orders;
}
