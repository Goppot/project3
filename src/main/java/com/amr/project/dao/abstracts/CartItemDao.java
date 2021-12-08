package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.CartItem;
import com.amr.project.model.entity.User;

import java.util.List;

public interface CartItemDao extends ReadWriteDao<CartItem, Long> {

    boolean existByUserIdAndItemId(long userId, long itemId);

    CartItem getByUserIdAndItemId(long userId, long itemId);

    List<CartItem> getAllByUser(User user);

    void deleteCartItemById(Long id);
}
