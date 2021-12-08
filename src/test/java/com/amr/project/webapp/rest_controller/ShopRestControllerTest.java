package com.amr.project.webapp.rest_controller;

import com.amr.project.AbstractApiTest;
import com.amr.project.inserttestdata.repository.ShopRepository;
import com.amr.project.model.entity.Country;
import com.amr.project.model.entity.Image;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Gender;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by Veilas on 11/8/2021.
 * Class: ShopRestControllerTest.java
 */

@AutoConfigureMockMvc
public class ShopRestControllerTest extends AbstractApiTest {

    private final static String REGISTRATION_URL = "/shop_api";

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    ShopRestController shopRestController;

    List<Image> list = new ArrayList<>();

    private Shop defaultShop = Shop.builder().name("first_name_test")
            .email("last_name_test")
            .phone("username_test")
            .description("email_test@mail.ru")
            .user(new User())
            .logo(list)
            .location(new Country(1L,"Russia", new ArrayList<>())).build();

    private Shop shopWrongFields1 = Shop.builder().name("first_name_test")
            .email("last_name_test")
            .phone("username_test")
            .description("email_test@mail.ru")
            .user(new User())
            .location(new Country(1L,"Russia", new ArrayList<>())).build();


    @Test
    @ExpectedDataSet(value = "datasets/user/expected/ExpectedUserXml.xml")
    void registrationNewShop_then200Status() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(REGISTRATION_URL)
                        .content(asJsonString(defaultShop))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void registrationNewShop_WrongValidation_then400Status() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(REGISTRATION_URL)
                        .content(asJsonString(shopWrongFields1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().json("{'errors':{'phone':'phone number must be at least 11 characters'}}"));
    }
}
