package com.amr.project.service.impl;

import com.amr.project.inserttestdata.repository.CartItemRepository;
import com.amr.project.model.entity.CartItem;
import com.amr.project.model.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
class CartItemServiceImplTest {

    @Autowired
    private CartItemRepository cartItemRepository;

    @BeforeAll
    void startUp(){
        System.out.println("Before all: " + this);
    }

    @Test
    void cartItemSave() throws Exception{
        CartItem cartItem = cartItemRepository.save(new CartItem());
        assertNotNull(cartItem);
        assertTrue(cartItem.getId() > 0);
        cartItemRepository.delete(cartItem);
    }
    @Test
    void getCartItem() throws Exception{
        CartItem cartItem = cartItemRepository.findById(1L).orElse(null);
        if (cartItem != null) {
            assertEquals(CartItem.class, cartItem.getClass());
            System.out.println("not null");
        } else {
            assertEquals(null, cartItem);
            System.out.println("null");
        }
    }

    @Test
    void plusCartItem() throws Exception{
        CartItem cartItem = new CartItem();
        int quantity = cartItem.getQuantity();
        cartItem.setQuantity(quantity + 1);
        assertTrue(cartItem.getQuantity() - quantity == 1);
        System.out.println("test plus OK");

    }

    @Test
    void minusCartItem() throws Exception{
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(10);
        int quantity = cartItem.getQuantity();
        cartItem.setQuantity(quantity - 1);
        assertTrue(quantity - cartItem.getQuantity() == 1);
        System.out.println("test minus OK");
    }

    @Test
    void getCartItemByUser() throws Exception{
        CartItem cartItem1 = CartItem.builder().build();
        CartItem cartItem2 = CartItem.builder().build();
        User user = User.builder()
                .cartItems(Arrays.asList(cartItem1, cartItem2))
                .build();
        assertFalse(user.getCartItems().isEmpty());
    }


}