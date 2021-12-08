package com.amr.project.service.impl;

import com.amr.project.converter.ItemMapper;
import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.inserttestdata.repository.ItemRepository;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Mail;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.email.EmailSenderService;
import com.amr.project.util.TrackedEmailItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl extends ReadWriteServiceImpl<Item, Long> implements ItemService {

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;
    private final ItemDao itemDao;
    private final TrackedEmailItem trackedEmailItem;
    private final EmailSenderService emailSenderService;

    @Autowired
    protected ItemServiceImpl(ItemMapper itemMapper, ItemRepository itemRepository, ItemDao itemDao, TrackedEmailItem trackedEmailItem, EmailSenderService emailSenderService) {
        super(itemDao);
        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
        this.itemDao = itemDao;
        this.emailSenderService = emailSenderService;
        this.trackedEmailItem = trackedEmailItem;
    }

    @Override
    @Transactional
    public List<Item> getUnmoderatedItems() {
        return itemDao.getUnmoderatedItems();
    }

    @Override
    @Transactional
    public List<Item> getModeratedItems() {
        return itemDao.getModeratedItems();
    }

    @Override
    @Transactional
    public Item getItemName(String nameItem) {
        return itemDao.getItemName(nameItem);
    }

    @Override
    @Transactional
    public void update(Item item) {
        Mail mail = trackedEmailItem.trackedEmailItemUpdateDelete(item);
        if (mail.getMessage() != null)
            emailSenderService.sendSimpleEmail(mail);
        itemDao.update(item);
    }

    @Override
    @Transactional
    public void persist(Item item) {
        emailSenderService.sendSimpleEmail(trackedEmailItem.trackedEmailItemPersist(item));
        itemDao.persist(item);
    }

    @Override
    @Transactional
    public List<Item> getMostPopular(int quantity) {
        return itemDao.getMostPopular(quantity);
    }

    @Override
    public Item getItemId(long id) {
        Item item = itemRepository.findById(id).orElse(null);
        return item;
    }

    @Override
    public ItemDto getItemDtoId(long id) {
        ItemDto itemDto = itemMapper.itemToDto(itemRepository.findById(id).orElse(null));
        return itemDto;
    }

    @Override
    public List<ItemDto> getAllItemsRatingSort(){
        List<Item> itemList = itemRepository.findAllByOrderByRatingDesc();
        List<ItemDto> itemDtoList = itemList.stream().map(item -> itemMapper.itemToDto(item)).collect(Collectors.toList());
        return itemDtoList;
    }

    @Override
    public List<Item> getAllItem(User user) {
        List<Long> list = user.getCartItems().stream().map(item -> item.getId()).sorted().collect(Collectors.toList());
        List<Item> itemsList = new ArrayList<>();
        for (Long id : list) {
            itemsList.add(itemRepository.findById(id).orElse(null));
        }
        return itemsList;
    }
}
