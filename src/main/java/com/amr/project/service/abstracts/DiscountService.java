package com.amr.project.service.abstracts;

import com.amr.project.model.dto.DiscountDto;
import com.amr.project.model.entity.Discount;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface DiscountService extends ReadWriteService<Discount, Long> {

    List<DiscountDto> findByUser(User user);

    List<DiscountDto> findByShop(Shop shop);

    DiscountDto findByUserAndShop(Long userId, Long shopId);
}