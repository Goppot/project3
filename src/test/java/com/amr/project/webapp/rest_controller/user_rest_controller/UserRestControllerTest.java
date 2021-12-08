package com.amr.project.webapp.rest_controller.user_rest_controller;

import com.amr.project.AbstractApiTest;
import com.amr.project.inserttestdata.repository.UserRepository;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Gender;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc(addFilters = false)
class UserRestControllerTest extends AbstractApiTest {

    private final static String REGISTRATION_URL = "/registration";
    private final static String GET_USER_SHOPS_URL = "/getUserShops/";
    private final static String GET_USER_ITEMS_URL = "/getUserOrders/";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    private User defaultUser = User.builder().firstName("first_name_test")
            .lastName("last_name_test")
            .username("username_test")
            .email("email_test@mail.ru")
            .phone("+7777777777")
            .gender(Gender.MALE)
            .age(60)
            .password("123456789").build();
    private User userWithEmailExist = User.builder().firstName("first_name_test")
            .lastName("last_name_test")
            .username("username_test")
            .email("exist_email@mail.ru")
            .phone("+7477777777")
            .gender(Gender.MALE)
            .password("123456789").build();
    private User userWithUsernameExist = User.builder().firstName("first_name_test")
            .lastName("last_name_test")
            .username("exists_username")
            .email("123test@mail.ru")
            .phone("+7777778777")
            .gender(Gender.MALE)
            .password("123456789").build();
    private User userWithPhoneExist = User.builder().firstName("first_name_test")
            .lastName("last_name_test")
            .username("username_test")
            .email("email_test7@mail.ru")
            .phone("exists_phone")
            .gender(Gender.MALE)
            .password("123456789").build();
    private User userWrongFields1 = User.builder().firstName("first_name_test")
            .lastName("last_name_test")
            .username("username_test")
            .email("email_test@mail.ru")
            .phone("3")
            .gender(Gender.MALE)
            .password("123456789").build();

    private ItemDto expectedItem = ItemDto.builder().id(1l)
            .count(500)
            .description("test_item")
            .isModerateAccept(true)
            .isModerated(true)
            .isPretendentToBeDeleted(false)
            .moderatedRejectReason("no reason")
            .name("test_item")
            .price(new BigDecimal("500.00"))
            .rating(5.5)
            .categories(new ArrayList<>())
            .images(new ArrayList<>())
            .categories(new ArrayList<>())
            .reviews(new ArrayList<>())
            .categoriesName(new String[0])
            .build();

    private ShopDto expectedShop = ShopDto.builder().id(1l)
            .description("test_description")
            .email("test_shop@mail.ru")
            .isModerateAccept(true)
            .isModerated(true)
            .isPretendentToBeDeleted(false)
            .moderatedRejectReason("no reason")
            .name("test_shop")
//            .phone("test_phone")
            .rating(5.5)
            .items(new ArrayList<>())
            .reviews(new ArrayList<>())
            .build();

    @Test
    @ExpectedDataSet(value = "datasets/user/expected/ExpectedUserXml.xml")
    void registrationNewUserTest_then200Status() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(REGISTRATION_URL)
                .content(asJsonString(defaultUser))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        User userFromBd = userRepository.findByEmail(defaultUser.getEmail());
        assertTrue(passwordEncoder.matches(defaultUser.getPassword(), userFromBd.getPassword()));


    }

    @Test
    @DataSet(value = "datasets/user/data/UsersWithFieldsExists.xml", cleanBefore = true, useSequenceFiltering = false)
    void registrationNewUserTest_ExistUser_then400Status() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(REGISTRATION_URL)
                .content(asJsonString(userWithEmailExist))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json("{'isExist':'User with this email exist'}"));
        mvc.perform(MockMvcRequestBuilders.post(REGISTRATION_URL)
                .content(asJsonString(userWithUsernameExist))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json("{'isExist':'User with this username exist'}"));
        mvc.perform(MockMvcRequestBuilders.post(REGISTRATION_URL)
                .content(asJsonString(userWithPhoneExist))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json("{'isExist':'User with this phone exist'}"));
    }

    @Test
    void registrationNewUserTest_WrongValidation_then400Status() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(REGISTRATION_URL)
                .content(asJsonString(userWrongFields1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json("{'errors':{'phone':'phone number must be at least 11 characters'}}"));
    }

    @Test
    @DataSet(value = "datasets/user/data/UserWithShop.xml", cleanBefore = true, useSequenceFiltering = false)
    void getUserShopTest_then200Status() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(GET_USER_SHOPS_URL + 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ShopDto actualShop = new ObjectMapper().readValue(content.substring(1, content.length() - 1), ShopDto.class);
        assertEquals(expectedShop, actualShop);
    }

    @Test
    @DataSet(value = "datasets/user/data/UserWithItem.xml", cleanBefore = true, useSequenceFiltering = false)
    void getUserItemsTest_then200Status() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(GET_USER_ITEMS_URL + 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ItemDto actualItem = new ObjectMapper().readValue(content.substring(1, content.length() - 1), ItemDto.class);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    @DataSet(value = "datasets/user/data/EmptyUser.xml", cleanBefore = true, useSequenceFiltering = false)
    void getUserShopTest_UserNoShops_then400Status() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(GET_USER_SHOPS_URL + 1))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }

    @Test
    @DataSet(value = "datasets/user/data/EmptyUser.xml", cleanBefore = true, useSequenceFiltering = false)
    void getUserItemsTest_UserNoItems_then400Status() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(GET_USER_ITEMS_URL + 1))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


}