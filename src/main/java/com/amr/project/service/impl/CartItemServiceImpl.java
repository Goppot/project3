package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.CartItemDao;
import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.inserttestdata.repository.CartItemRepository;
import com.amr.project.inserttestdata.repository.ItemRepository;
import com.amr.project.inserttestdata.repository.UserRepository;
import com.amr.project.model.entity.CartItem;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.CartItemService;
import com.amr.project.service.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl extends ReadWriteServiceImpl<CartItem, Long> implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final CartItemDao cartItemDao;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserDao userDao;

    @Autowired
    protected CartItemServiceImpl(CartItemRepository cartItemRepository,
                                  ItemRepository itemRepository,
                                  CartItemDao cartItemDao,
                                  UserService userService, UserRepository userRepository, UserDao userDao) {
        super(cartItemDao);
        this.cartItemRepository = cartItemRepository;
        this.itemRepository = itemRepository;
        this.cartItemDao = cartItemDao;
        this.userService = userService;
        this.userRepository = userRepository;
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existByUserIdAndItemId(long userId, long itemId) {
        return cartItemDao.existByUserIdAndItemId(userId, itemId);
    }

    @Override
    @Transactional(readOnly = true)
    public CartItem getByUserIdAndItemId(long userId, long itemId) {
        return cartItemDao.getByUserIdAndItemId(userId, itemId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItem> getAllByUser(User user) {
        return cartItemDao.getAllByUser(user);
    }

    @Override
    public void plusCartItem(Long id) {
        CartItem cartItem = cartItemRepository.findById(id).orElse(null);
        int quantity = cartItem.getQuantity();
        cartItem.setQuantity(quantity + 1);
        cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional
    public void minusCartItem(Long id) {
        CartItem cartItem = cartItemRepository.findById(id).orElse(null);
        int quantity = cartItem.getQuantity();
        if (quantity > 1) {
            cartItem.setQuantity(quantity - 1);
            cartItemRepository.save(cartItem);
        } else {
            cartItemDao.deleteCartItemById(id);
        }
    }

    @Override
    public List<CartItem> getCartItemByUserAuthorized() {
        User user = userService.getUserId(userService.getAuthorized().getId());
        List<CartItem> cartItems = user.getCartItems();
        return cartItems;
    }

    @Override
    public void addItemToCart(Long id) {
        Item item = itemRepository.findById(id).orElse(null);
        User user = userRepository.findById(userService.getAuthorized().getId()).orElse(null);
        CartItem cartItem = cartItemRepository.findCartItemByUser_IdAndItem_Id(user.getId(), item.getId()).orElse(null);
        if (cartItem == null) {
            cartItem = CartItem.builder()
                    .item(item)
                    .shop(item.getShop())
                    .user(user)
                    .quantity(1)
                    .build();
            cartItemRepository.save(cartItem);
            user.getCartItems().add(cartItem);
            userRepository.save(user);
            item.getCartItems().add(cartItem);
            itemRepository.save(item);
        } else {
            int quantity = cartItem.getQuantity();
            cartItem.setQuantity(quantity + 1);
            cartItemRepository.save(cartItem);
        }
    }
    @Override
    public void addCookieToCartItem(User u){
        User user = userRepository.findById(userService.getAuthorized().getId()).orElse(null);
        List<CartItem> list = u.getCartItems();
        for (CartItem c : list){
            Item item = itemRepository.findById(c.getId()).orElse(null);
            CartItem cartItem = cartItemRepository.findCartItemByUser_IdAndItem_Id(
                    user.getId(), item.getId()).orElse(null);
            if (cartItem == null) {
                cartItem = CartItem.builder()
                        .item(item)
                        .shop(item.getShop())
                        .user(user)
                        .quantity(c.getQuantity())
                        .build();
                cartItemRepository.save(cartItem);
                user.getCartItems().add(cartItem);
                userRepository.save(user);
                item.getCartItems().add(cartItem);
                itemRepository.save(item);
            } else {
                int quantity = cartItem.getQuantity();
                cartItem.setQuantity(quantity + c.getQuantity());
                cartItemRepository.save(cartItem);
            }
        }
    }
}
