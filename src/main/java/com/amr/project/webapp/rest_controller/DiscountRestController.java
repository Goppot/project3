package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.DiscountMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.DiscountDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.Discount;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.impl.DiscountServiceImpl;
import com.amr.project.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"API для получения пользователей и добавления скидки на странице владельца магазина"})
@RestController
@RequestMapping("/api")
@Validated
@RequiredArgsConstructor
public class DiscountRestController {

    private final UserServiceImpl userService;
    private final ShopService shopService;
    private final DiscountServiceImpl discountService;
    private final UserMapper userMapper;
    private final DiscountMapper discountMapper;
    private final ShopMapper shopMapper;


    @ApiOperation(value = "Получаем всех пользователей с ролью \"Пользователь\" ")
    @GetMapping("/userlist/all")
    public ResponseEntity<List<UserDto>> findAllUsersWithRoleUser() {
        return ResponseEntity.ok(userService.findByRole("User")
                .stream()
                .map(userMapper::userToDto)
                .collect(Collectors.toList()));
    }

    @ApiOperation(value = "Получаем пользователя по ID ")
    @GetMapping("/userlist/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") long id) {
        User user = userService.getByKey(id);
        return ResponseEntity.ok().body(userMapper.userToDto(user));

    }

    @ApiOperation(value = "Получаем магазин по ID ")
    @GetMapping("/userlist/shop/{id}")
    public ResponseEntity<ShopDto> getShopById(@PathVariable("id") long id) {
        Shop shop = shopService.getByKey(id);
        return ResponseEntity.ok().body(shopMapper.shopToDto(shop));
    }

    @ApiOperation(value = "Добавление скидки")
    @PostMapping("/userlist/addDiscount")
    public ResponseEntity<?> addDiscount(@RequestBody DiscountDto discountDto) {
        if (discountDto.getShop() == null | discountDto.getUser() == null)
            return ResponseEntity.badRequest().build();
        Discount discount = discountMapper.discountDtoToDiscount(discountDto);
        discount.setShop(shopService.getShop(discountDto.getShop().getName()));
        discount.setUser(userService.findByUsername(discountDto.getUser().getUsername()));
        discountService.persist(discount);
        return ResponseEntity.ok().build();
    }

}
