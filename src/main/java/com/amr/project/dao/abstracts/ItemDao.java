package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Item;

import java.util.List;

public interface ItemDao extends ReadWriteDao<Item, Long> {
    Item getItemName(String nameItem);

    List<Item> getUnmoderatedItems();

    List<Item> getModeratedItems();

    List<Item> getMostPopular(int quantity);
}
