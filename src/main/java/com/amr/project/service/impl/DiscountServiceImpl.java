package com.amr.project.service.impl;

import com.amr.project.converter.DiscountMapper;
import com.amr.project.dao.abstracts.DiscountDao;
import com.amr.project.model.dto.DiscountDto;
import com.amr.project.model.entity.Discount;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl extends ReadWriteServiceImpl<Discount, Long> implements DiscountService {

    private final DiscountDao discountDao;
    private final DiscountMapper discountMapper;

    @Autowired
    public DiscountServiceImpl(DiscountDao discountDao, DiscountMapper discountMapper) {
        super(discountDao);
        this.discountDao = discountDao;
        this.discountMapper = discountMapper;
    }

    @Override
    public List<DiscountDto> findByUser(User user) {
        List<Discount> discount = discountDao.findByUser(user);
        List<DiscountDto> listDto = discount.stream().map(d -> discountMapper.discountToDiscountDto(d))
                .collect(Collectors.toList());
        return listDto;
    }

    @Override
    public List<DiscountDto> findByShop(Shop shop) {
        List<Discount> discounts = discountDao.findByShop(shop);
        if (discounts == null) {
            return new ArrayList<>();
        }
        List<DiscountDto> listdto = discounts.stream().map(d -> discountMapper.discountToDiscountDto(d))
                .filter(x -> x.getFixedDiscount() != 0 || x.getPercentage() != 0)
                .collect(Collectors.toList());
        return listdto;
    }

    @Override
    public DiscountDto findByUserAndShop(Long userId, Long shopId) {
        Discount list = discountDao.findByUserAndShop(userId, shopId);
        if (list == null) {
            return new DiscountDto();
        }
        return discountMapper.discountToDiscountDto(list);
    }

}