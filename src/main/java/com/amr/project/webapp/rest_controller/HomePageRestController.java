package com.amr.project.webapp.rest_controller;


import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.impl.ShopServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
@Validated
@RequiredArgsConstructor
public class HomePageRestController {

    private final ShopServiceImpl shopService;
    private final ShopMapper shopMapper;


    @ApiOperation(value = "получение списка магазинов")
    @GetMapping("allShops")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список магазинов получен")
    })
    public ResponseEntity<List<ShopDto>> getAllShops() {
        return ResponseEntity.ok(shopService.getAll().stream()
                .map(shopMapper::shopToDto)
                .collect(Collectors.toList()));
    }
}
