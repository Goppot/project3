package com.amr.project.service.abstracts;

import com.amr.project.model.dto.CartItemDto;
import com.amr.project.model.entity.CartItem;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.User;

import java.util.List;

public interface CartItemService extends ReadWriteService<CartItem, Long> {

    boolean existByUserIdAndItemId(long userId, long itemId);

    CartItem getByUserIdAndItemId(long userId, long itemId);

    List<CartItem> getAllByUser(User user);

    void plusCartItem(Long id);

    void minusCartItem(Long id);

    List<CartItem> getCartItemByUserAuthorized();

    void addItemToCart(Long id);

    void addCookieToCartItem(User u);
}
