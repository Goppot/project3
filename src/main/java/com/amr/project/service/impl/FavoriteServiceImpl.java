package com.amr.project.service.impl;

import com.amr.project.dao.impl.FavoriteDaoImpl;
import com.amr.project.model.entity.Favorite;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl extends ReadWriteServiceImpl<Favorite, Long> implements FavoriteService {

    private final FavoriteDaoImpl favoriteDao;

    @Autowired
    public  FavoriteServiceImpl (FavoriteDaoImpl  favoriteDao) {
        super (favoriteDao);
        this.favoriteDao = favoriteDao;
    }

    @Override
    public Favorite findByUser(User user) {
        Favorite favorite = favoriteDao.findByUser(user);
        return favorite;
    }
}
