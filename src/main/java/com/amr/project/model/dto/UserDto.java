package com.amr.project.model.dto;



import com.amr.project.model.entity.CartItem;
import lombok.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString

public class UserDto {
    private Long id;
    private String username;
    private int age;
    private String gender;
    private String email;
    private String phone;
    private AddressDto address;
    private ImageDto images;
    private Calendar birthday;
    private List<OrderDto> orders = new ArrayList<>();
    private List<ShopDto> shops = new ArrayList<>();
    private List<CartItemDto> cartItems = new ArrayList<>();





}
