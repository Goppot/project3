package com.amr.project.service.abstracts;

import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.User;

import java.util.List;

public interface ItemService extends ReadWriteService<Item, Long> {
    Item getItemName(String nameItem);

    List<Item> getUnmoderatedItems();

    List<Item> getModeratedItems();

    List<Item> getMostPopular(int quantity);

    Item getItemId(long id);

    ItemDto getItemDtoId(long id);

    List<ItemDto> getAllItemsRatingSort();

    List<Item> getAllItem(User user);
}
