package com.amr.project.service.abstracts;

import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;

import javax.persistence.NoResultException;
import java.util.List;

public interface ShopService extends ReadWriteService<Shop, Long> {
    Shop getShop(String nameShop);

    List<Shop> getUnmoderatedShops();

    List<Shop> getModeratedShops();

    List<Shop> getMostPopular(int quantity);

    ShopDto getShopId(Long id);

    List<ShopDto> getAllShopsRatingSort();

    @Override
    void persist(Shop shop);
}
