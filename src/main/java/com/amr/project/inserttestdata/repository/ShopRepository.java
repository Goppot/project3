package com.amr.project.inserttestdata.repository;

import com.amr.project.model.entity.Review;
import com.amr.project.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    Shop findByName(String name);

    List<Shop> findAllByOrderByRatingDesc();
    Shop findByUserId(Long id);
}
