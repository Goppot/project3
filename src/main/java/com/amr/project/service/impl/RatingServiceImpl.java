package com.amr.project.service.impl;

import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Review;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.RatingService;
import com.amr.project.service.abstracts.ReviewService;
import com.amr.project.service.abstracts.ShopService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Veilas on 11/10/2021.
 * Class: RatingServiceImpl.java
 */
@Service
@Transactional
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final ReviewService reviewService;
    private final ItemService itemService;
    private final ShopService shopService;

    // присваиваем окончательный рейтинг товару или магазину
    @Override
    public void setRating(Review review) {
        double rating = 0;
        int count = 0;
        // Считываем рейтинг со всех отзывов для товара или магазина
        for (Review r : reviewService.getAll()) {
            if (review.getItem() != null && r.getItem() != null &&
                    r.getItem().getName().equals(review.getItem().getName())) {
                count++;
                if (r.getRating() != null) {
                    rating += r.getRating();
                }
            } else if (review.getShop() != null && r.getShop() != null &&
                    r.getShop().getName().equals(review.getShop().getName())) {
                count++;
                if (r.getRating() != null) {
                    rating += r.getRating();
                }
            }
        }
        rating = rating / count;

        // обновляем рейтинг
        if (review.getShop() != null) {
            Shop shop = review.getShop();
            shop.setRating(rating);
            shopService.update(shop);
        } else if (review.getItem() != null) {
            Item item = review.getItem();
            item.setRating(rating);
            itemService.update(item);
        }

    }
}
