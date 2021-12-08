package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.FavoriteDao;
import com.amr.project.model.entity.Favorite;
import com.amr.project.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class FavoriteDaoImpl extends ReadWriteDaoImpl<Favorite, Long> implements  FavoriteDao {

    @Override
    public Favorite findByUser(User user) {
        Favorite favorite = entityManager.createQuery("SELECT f from Favorite f where f.user.username =: username",
                Favorite.class)
                .setParameter("username",user.getUsername())
                .getResultStream().findAny().get();
        return favorite;
    }
}
