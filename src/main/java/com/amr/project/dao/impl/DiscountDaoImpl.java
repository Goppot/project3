package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.DiscountDao;
import com.amr.project.model.entity.Discount;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class DiscountDaoImpl extends ReadWriteDaoImpl<Discount, Long> implements DiscountDao {


    @Override
    public List<Discount> findByUser(User user) {
        return entityManager.createQuery("select u from Discount u where u.user.username = :name ", Discount.class)
                .setParameter("name", user.getUsername()).getResultList();
    }

    @Override
    public List<Discount> findByShop(Shop shop) {
        return entityManager.createQuery("SELECT d from Discount d where d.shop.id = :id", Discount.class)
                .setParameter("id", shop.getId()).getResultList();
    }

    @Override
    public Discount findByUserAndShop(Long userId, Long shopId) {
        return entityManager.createQuery("SELECT d from Discount d where d.user.id=:userId and d.shop.id=:shopId", Discount.class)
                .setParameter("userId", userId)
                .setParameter("shopId", shopId)
                .getSingleResult();
    }


}
