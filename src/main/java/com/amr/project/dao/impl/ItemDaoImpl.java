package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.model.entity.Item;
import com.amr.project.util.QueryResultWrapper;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ItemDaoImpl extends ReadWriteDaoImpl<Item, Long> implements ItemDao {

    @Override
    public Item getItemName(String nameItem) {
        TypedQuery<Item> query = (TypedQuery<Item>) entityManager.createQuery("select u from Item u where u.name=:nameItem");
        query.setParameter("nameItem", nameItem);
        return QueryResultWrapper.wrapGetSingleResult(query);
    }

    @Override
    public List<Item> getUnmoderatedItems() {
        return entityManager.createQuery("SELECT i FROM Item i WHERE i.isModerateAccept = false AND i.isModerated = false", Item.class)
                .getResultList();
    }

    @Override
    public List<Item> getModeratedItems() {
        return entityManager.createQuery("SELECT i FROM Item i WHERE i.isModerateAccept = true AND i.isModerated = true ", Item.class)
                .getResultList();
    }

    @Override
    @Transactional
    public List<Item> getMostPopular(int quantity) {
        Query query = entityManager.createQuery("Select i from Item as i order by i.rating desc");
        return query.getResultList().size() > quantity ? query.getResultList().subList(0, quantity) : query.getResultList();
    }
}
