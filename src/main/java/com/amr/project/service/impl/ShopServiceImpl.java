package com.amr.project.service.impl;

import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.inserttestdata.repository.ShopRepository;
import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Mail;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.email.EmailSenderService;
import com.amr.project.util.TrackedEmailShop;
import com.github.scribejava.core.base64.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShopServiceImpl extends ReadWriteServiceImpl<Shop, Long> implements ShopService {

    private final ShopMapper shopMapper;
    private final ShopRepository shopRepository;
    private final ShopDao shopDao;
    private final TrackedEmailShop trackedEmailShop;
    private final EmailSenderService emailSenderService;

    @Autowired
    public ShopServiceImpl(ShopMapper shopMapper, ShopRepository shopRepository, ShopDao shopDao, TrackedEmailShop trackedEmailShop, EmailSenderService emailSenderService) {
        super(shopDao);
        this.shopMapper = shopMapper;
        this.shopRepository = shopRepository;
        this.shopDao = shopDao;
        this.trackedEmailShop = trackedEmailShop;
        this.emailSenderService = emailSenderService;
    }

    public ItemDto getTheMostRatingItem(List<ItemDto> itemList) {
        return itemList.stream()
                .max(Comparator.comparingDouble(ItemDto::getRating)).get();
    }

    public String convertImage(ImageDto image) {
        return Base64.encode(image.getPicture());
    }

    public List<String> convertListImages(List<ImageDto> list) {
        return list.stream()
                .map(s -> Base64.encode(s.getPicture()))
                .collect(Collectors.toList());

    }

    public Item getItemById(List<Item> itemList, long id) {
        return itemList.stream().filter(i -> i.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Shop getShop(String nameShop) {
        return shopDao.getShop(nameShop);
    }

    @Override
    public List<Shop> getUnmoderatedShops() {
        return shopDao.getUnmoderatedShops();
    }

    @Override
    public List<Shop> getModeratedShops() {
        return shopDao.getModeratedShops();
    }


    @Override
    @Transactional
    public void persist(Shop shop) {
        emailSenderService.sendSimpleEmail(trackedEmailShop.trackedEmailShopPersist(shop));
        shopDao.persist(shop);
    }

    @Override
    @Transactional
    public void update(Shop shop) {
        Mail mail = trackedEmailShop.trackedEmailShopUpdate(shop);
        if (mail.getMessage() != null)
            emailSenderService.sendSimpleEmail(mail);
        shopDao.update(shop);

    }

    @Override
    @Transactional
    public void delete(Shop shop) {
        emailSenderService.sendSimpleEmail(trackedEmailShop.trackedEmailShopDelete(shop));
        shopDao.delete(shop);

    }

    @Override
    @Transactional
    public List<Shop> getMostPopular(int quantity) {
        return shopDao.getMostPopular(quantity);
    }

    @Override
    public ShopDto getShopId(Long id){
        ShopDto shopDto = shopMapper.shopToDto(shopRepository.findById(id).orElse(null));
        return shopDto;
    }

    public List<ShopDto> getAllShopsRatingSort(){
        List<Shop> shopList = shopRepository.findAllByOrderByRatingDesc();
        List<ShopDto> shopDtoList = shopList.stream().map(shop -> shopMapper.shopToDto(shop)).collect(Collectors.toList());
        return shopDtoList;
    }
}
