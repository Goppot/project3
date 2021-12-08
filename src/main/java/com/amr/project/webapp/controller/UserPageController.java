package com.amr.project.webapp.controller;

import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@ControllerAdvice
@Controller
@RequiredArgsConstructor
@Validated
@Api(tags = { "Операции с пользователем"})
public class UserPageController {

    private final UserService userService;
    private final UserMapper userMapper;

    @ApiOperation(value = "Получение пользователя по ID")
    @GetMapping(value = "/user", produces = "image/png")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь найден по ID"),
            @ApiResponse(code = 404, message = "Пользователь не найден по ID")
    })
    public String getUserPage1(Model model) {
        User user = userService.getAuthorized();
        UserDto userDto = userMapper.userToDto(user);
        System.err.println(userDto);
        model.addAttribute("user", userDto);
        return "UserPage";
    }

    @ApiOperation(value = "Получение пользователя по ID")
    @GetMapping(value = "/user/{id}", produces = "image/png")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь найден по ID"),
            @ApiResponse(code = 404, message = "Пользователь не найден по ID")
    })
    public String getUserPage(@PathVariable("id") Long id, Model model) {
        User user = userService.getByKey(id);
        UserDto userDto = userMapper.userToDto(user);
        System.err.println(userDto);
        model.addAttribute("user", userDto);
        return "UserPage";
    }

    @ApiOperation(value = "Просмотр списка покупателей")
    @GetMapping("/user/{id}/userlist")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь найден по ID"),
            @ApiResponse(code = 404, message = "Пользователь не найден по ID")
    })
    public String getUserList(@PathVariable("id") Long id, Model model){
        User user = userService.getByKey(id);
        UserDto userDto = userMapper.userToDto(user);
        System.err.println(userDto);
        model.addAttribute("user", userDto);
        return "/shopOwner/shop_add_discount";
    }

    @GetMapping("/user-cart-page")
    public String getAllCartItem(Model model){
        UserDto userDto = userMapper.userToDto(userService.getAuthorized());
        model.addAttribute(userDto);
        return "/user-cart-page";
    }

    @GetMapping("/user-item")
    public String getUserItems(Model model){
        UserDto userDto = userMapper.userToDto(userService.getAuthorized());
        model.addAttribute("user",userDto);
        return "user-item";
    }

    @GetMapping("/user-orders")
    public String getUserOrders(Model model){
        UserDto userDto = userMapper.userToDto(userService.getAuthorized());
        model.addAttribute("user",userDto);
        return "user-orders";
    }
}
