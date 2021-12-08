package com.amr.project.inserttestdata.repository;

import com.amr.project.model.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Favorite findByUserId(Long id);

}
