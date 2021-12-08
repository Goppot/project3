package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Shop;

import java.util.List;

public interface ShopDao extends ReadWriteDao<Shop, Long> {
    Shop getShop(String nameShop);

    List<Shop> getUnmoderatedShops();

    List<Shop> getModeratedShops();

    List<Shop> getMostPopular(int quantity);
}
