package com.amr.project.inserttestdata.repository;

import com.amr.project.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByName(String name);

    List<Item> findAllByOrderByRatingDesc();
}
