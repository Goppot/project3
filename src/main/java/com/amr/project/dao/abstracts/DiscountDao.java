package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Discount;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface DiscountDao extends ReadWriteDao<Discount, Long> {

    List<Discount> findByUser(User user);
    List<Discount> findByShop(Shop shop);
    Discount findByUserAndShop(Long userId, Long shopId);

}
