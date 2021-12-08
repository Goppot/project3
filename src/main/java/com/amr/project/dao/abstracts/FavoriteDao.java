package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Favorite;
import com.amr.project.model.entity.User;

public interface FavoriteDao {
    Favorite findByUser(User user);
}
