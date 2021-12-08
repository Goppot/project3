package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.CartItemDao;
import com.amr.project.model.entity.CartItem;
import com.amr.project.model.entity.User;
import com.amr.project.util.QueryResultWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CartItemDaoImpl extends ReadWriteDaoImpl<CartItem, Long> implements CartItemDao {

    @Override
    public boolean existByUserIdAndItemId(long userId, long itemId) {
        Query query = entityManager
                .createQuery("SELECT c FROM CartItem c JOIN User u ON u.id = c.user.id JOIN Item i ON i.id = c.item.id " +
                        "WHERE i.id = :itemId AND u.id = :userId");
        query.setParameter("itemId", itemId);
        query.setParameter("userId", userId);
        int count = query.getResultList().size();
        return count > 0;
    }

    @Override
    public CartItem getByUserIdAndItemId(long userId, long itemId) {
        TypedQuery<CartItem> cartItemTypedQuery = (TypedQuery<CartItem>) entityManager
                .createQuery("SELECT c FROM CartItem c JOIN User u ON u.id = c.user.id JOIN Item i ON i.id = c.item.id " +
                        "WHERE i.id = :itemId AND u.id = :userId");
        cartItemTypedQuery.setParameter("userId", userId);
        cartItemTypedQuery.setParameter("itemId", itemId);
        return QueryResultWrapper.wrapGetSingleResult(cartItemTypedQuery);
    }

    @Override
    public List<CartItem> getAllByUser(User user) {
        Query query = entityManager.createQuery("SELECT c FROM CartItem c JOIN User u ON u.id = c.user.id WHERE u.id = :userId");
        query.setParameter("userId", user.getId());
        return query.getResultList();
    }

    @Override
    public void deleteCartItemById(Long id){
        Query query1 = entityManager.createNativeQuery("delete from user_cart_items where cart_items_id = " + id);
        Query query2 = entityManager.createNativeQuery("delete from item_cart_items where cart_items_id = " + id);
        Query query3 = entityManager.createNativeQuery("delete from cart_item where id = " + id);

        query1.executeUpdate();
        query2.executeUpdate();
        query3.executeUpdate();
    }
}
