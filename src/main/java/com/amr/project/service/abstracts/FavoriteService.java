package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Favorite;
import com.amr.project.model.entity.User;

public interface FavoriteService extends ReadWriteService<Favorite, Long> {

    Favorite findByUser(User user);
}
