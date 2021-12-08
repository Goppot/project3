package com.amr.project.webapp.rest_controller;

import com.amr.project.AbstractApiTest;
import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.CartItemDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.CartItem;
import com.amr.project.service.abstracts.CartItemService;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ShopService;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ShoppingCartControllerTest extends AbstractApiTest {

    private final static String GET_USER_SHOPPING_CART_URL = "/shoppingCart/getUserShoppingCart";
    private final static String LOAD_LOCAL_SHOPPING_CART_TO_SERVER_URL = "/shoppingCart/loadLocalShoppingCartToServer";
    private final static String ADD_NEW_CART_ITEM_URL = "/shoppingCart/addNewCartItem";
    private final static String UPDATE_QUANTITY_URL = "/shoppingCart/updateQuantity";
    private final static String DELETE_POSITION_FROM_SHOPPING_CART_URL = "/shoppingCart/deletePositionFromCart";

    private final ItemService itemService;
    private final CartItemService cartItemService;
    private final ShopService shopService;
    private final ItemMapper itemMapper;
    private final ShopMapper shopMapper;

    @Autowired
    ShoppingCartControllerTest(ItemService itemService, CartItemService cartItemService, ShopService shopService,
                               ItemMapper itemMapper, ShopMapper shopMapper) {
        this.itemService = itemService;
        this.cartItemService = cartItemService;
        this.shopService = shopService;
        this.itemMapper = itemMapper;
        this.shopMapper = shopMapper;
    }

    @Test
    @WithMockUser(username = "user4_username")
    void getNonEmptyUserShoppingCart() throws Exception {
        mvc.perform(get(GET_USER_SHOPPING_CART_URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    @WithMockUser(username = "user1_username")
    void getEmptyUserShoppingCart() throws Exception {
        mvc.perform(get(GET_USER_SHOPPING_CART_URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getAnonymousUserShoppingCart() throws Exception {
        mvc.perform(get(GET_USER_SHOPPING_CART_URL))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user1_username")
    @ExpectedDataSet(value = "datasets/cart_items/expected/Cart_items_After_Load_Local_Shopping_Cart.xml", ignoreCols = {"id", "item_id"})
    void loadLocalShoppingCartToServerForAuthUserWithEmptyCart() throws Exception {
        Set<CartItemDto> cartItemDtos = new HashSet<>();

        ShopDto shopDto1 = shopMapper.shopToDto(shopService.getByKey(1l));
        ItemDto itemDto1 = itemMapper.itemToDto(itemService.getByKey(1l));
        CartItemDto cartItemDto1 = CartItemDto.builder().itemDto(itemDto1).shopDto(shopDto1).quantity(3).build();
        cartItemDtos.add(cartItemDto1);

        ItemDto itemDto2 = itemMapper.itemToDto(itemService.getByKey(2l));
        CartItemDto cartItemDto2 = CartItemDto.builder().itemDto(itemDto2).shopDto(shopDto1).quantity(3).build();
        cartItemDtos.add(cartItemDto2);

        ItemDto itemDto3 = itemMapper.itemToDto(itemService.getByKey(3l));
        CartItemDto cartItemDto3 = CartItemDto.builder().itemDto(itemDto3).shopDto(shopDto1).quantity(3).build();
        cartItemDtos.add(cartItemDto3);
        mvc.perform(
                post(LOAD_LOCAL_SHOPPING_CART_TO_SERVER_URL)
                        .content(asJsonString(cartItemDtos))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful());
        List<CartItem> cartItems = cartItemService.getAll();
        cartItems.forEach(System.out::println);
    }

    @Test
    @WithMockUser(username = "user4_username")
    void loadLocalShoppingCartToServerForAuthUserWithNonEmptyCart() throws Exception {
        Set<CartItemDto> cartItemDtos = new HashSet<>();

        ShopDto shopDto1 = shopMapper.shopToDto(shopService.getByKey(1l));
        ItemDto itemDto1 = itemMapper.itemToDto(itemService.getByKey(1l));
        CartItemDto cartItemDto1 = CartItemDto.builder().itemDto(itemDto1).shopDto(shopDto1).quantity(3).build();
        cartItemDtos.add(cartItemDto1);

        ItemDto itemDto2 = itemMapper.itemToDto(itemService.getByKey(2l));
        CartItemDto cartItemDto2 = CartItemDto.builder().itemDto(itemDto2).shopDto(shopDto1).quantity(3).build();
        cartItemDtos.add(cartItemDto2);

        ItemDto itemDto3 = itemMapper.itemToDto(itemService.getByKey(3l));
        CartItemDto cartItemDto3 = CartItemDto.builder().itemDto(itemDto3).shopDto(shopDto1).quantity(3).build();
        cartItemDtos.add(cartItemDto3);

        mvc.perform(
                post(LOAD_LOCAL_SHOPPING_CART_TO_SERVER_URL)
                        .content(asJsonString(cartItemDtos))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void loadLocalShoppingCartToServerForAnonymousUser() throws Exception {
        Set<CartItemDto> cartItemDtos = new HashSet<>();

        ShopDto shopDto1 = shopMapper.shopToDto(shopService.getByKey(1l));
        ItemDto itemDto1 = itemMapper.itemToDto(itemService.getByKey(1l));
        CartItemDto cartItemDto1 = CartItemDto.builder().itemDto(itemDto1).shopDto(shopDto1).quantity(3).build();
        cartItemDtos.add(cartItemDto1);

        ItemDto itemDto2 = itemMapper.itemToDto(itemService.getByKey(2l));
        CartItemDto cartItemDto2 = CartItemDto.builder().itemDto(itemDto2).shopDto(shopDto1).quantity(3).build();
        cartItemDtos.add(cartItemDto2);

        ItemDto itemDto3 = itemMapper.itemToDto(itemService.getByKey(3l));
        CartItemDto cartItemDto3 = CartItemDto.builder().itemDto(itemDto3).shopDto(shopDto1).quantity(3).build();
        cartItemDtos.add(cartItemDto3);
        mvc.perform(
                post(LOAD_LOCAL_SHOPPING_CART_TO_SERVER_URL)
                        .content(asJsonString(cartItemDtos))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user1_username")
    @ExpectedDataSet(value = "datasets/cart_items/expected/Cart_items_After_Add_CartItem.xml", ignoreCols = {"id"})
    void addNewCartItemForAuthUserWithoutAddedCartItemInUserCart() throws Exception {
        ShopDto shopDto = shopMapper.shopToDto(shopService.getByKey(1l));
        ItemDto itemDto = itemMapper.itemToDto(itemService.getByKey(1l));
        CartItemDto cartItemDto = CartItemDto.builder().itemDto(itemDto).shopDto(shopDto).quantity(3).build();

        mvc.perform(
                post(ADD_NEW_CART_ITEM_URL)
                        .content(asJsonString(cartItemDto))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user4_username")
    void addNewCartItemForAuthUserWitAddedCartItemInUserCart() throws Exception {
        ShopDto shopDto = shopMapper.shopToDto(shopService.getByKey(1l));
        ItemDto itemDto = itemMapper.itemToDto(itemService.getByKey(1l));
        CartItemDto cartItemDto = CartItemDto.builder().itemDto(itemDto).shopDto(shopDto).quantity(3).build();

        mvc.perform(
                post(ADD_NEW_CART_ITEM_URL)
                        .content(asJsonString(cartItemDto))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void addNewCartItemForAnonymousUser() throws Exception {
        ShopDto shopDto = shopMapper.shopToDto(shopService.getByKey(1l));
        ItemDto itemDto = itemMapper.itemToDto(itemService.getByKey(1l));
        CartItemDto cartItemDto = CartItemDto.builder().itemDto(itemDto).shopDto(shopDto).quantity(3).build();

        mvc.perform(
                post(ADD_NEW_CART_ITEM_URL)
                        .content(asJsonString(cartItemDto))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user4_username")
    @ExpectedDataSet(value = "datasets/cart_items/expected/Cart_items_After_Update_CartItem_Quantity.xml", ignoreCols = {"id"})
    void updateQuantityForAuthUserWithUpdatedCartItemInUserCart() throws Exception {
        ShopDto shopDto = shopMapper.shopToDto(shopService.getByKey(1l));
        ItemDto itemDto = itemMapper.itemToDto(itemService.getByKey(1l));
        CartItemDto cartItemDto = CartItemDto.builder().itemDto(itemDto).shopDto(shopDto).quantity(10).build();

        mvc.perform(MockMvcRequestBuilders.put(UPDATE_QUANTITY_URL)
                        .content(asJsonString(cartItemDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1_username")
    void updateQuantityForAuthUserWithoutUpdatedCartItemInUserCart() throws Exception {
        ShopDto shopDto = shopMapper.shopToDto(shopService.getByKey(1l));
        ItemDto itemDto = itemMapper.itemToDto(itemService.getByKey(1l));
        CartItemDto cartItemDto = CartItemDto.builder().itemDto(itemDto).shopDto(shopDto).quantity(10).build();

        mvc.perform(MockMvcRequestBuilders.put(UPDATE_QUANTITY_URL)
                        .content(asJsonString(cartItemDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateQuantityForAnonymousUser() throws Exception {
        ShopDto shopDto = shopMapper.shopToDto(shopService.getByKey(1l));
        ItemDto itemDto = itemMapper.itemToDto(itemService.getByKey(1l));
        CartItemDto cartItemDto = CartItemDto.builder().itemDto(itemDto).shopDto(shopDto).quantity(10).build();

        mvc.perform(MockMvcRequestBuilders.put(UPDATE_QUANTITY_URL)
                        .content(asJsonString(cartItemDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user4_username")
    @ExpectedDataSet(value = "datasets/cart_items/expected/Cart_items_After_Delete_CartItem.xml", ignoreCols = {"id"})
    void deletePositionFromCartForAuthUserWithDeletedCartItemInUserCart() throws Exception {
        ShopDto shopDto = shopMapper.shopToDto(shopService.getByKey(1l));
        ItemDto itemDto = itemMapper.itemToDto(itemService.getByKey(1l));
        CartItemDto cartItemDto = CartItemDto.builder().itemDto(itemDto).shopDto(shopDto).build();

        mvc.perform(MockMvcRequestBuilders.delete(DELETE_POSITION_FROM_SHOPPING_CART_URL)
                        .content(asJsonString(cartItemDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1_username")
    void deletePositionFromCartForAuthUserWithoutDeletedCartItemInUserCart() throws Exception {
        ShopDto shopDto = shopMapper.shopToDto(shopService.getByKey(1l));
        ItemDto itemDto = itemMapper.itemToDto(itemService.getByKey(1l));
        CartItemDto cartItemDto = CartItemDto.builder().itemDto(itemDto).shopDto(shopDto).build();

        mvc.perform(MockMvcRequestBuilders.delete(DELETE_POSITION_FROM_SHOPPING_CART_URL)
                        .content(asJsonString(cartItemDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletePositionFromCartForAnonymousUser() throws Exception {
        ShopDto shopDto = shopMapper.shopToDto(shopService.getByKey(1l));
        ItemDto itemDto = itemMapper.itemToDto(itemService.getByKey(1l));
        CartItemDto cartItemDto = CartItemDto.builder().itemDto(itemDto).shopDto(shopDto).build();

        mvc.perform(MockMvcRequestBuilders.delete(DELETE_POSITION_FROM_SHOPPING_CART_URL)
                        .content(asJsonString(cartItemDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}