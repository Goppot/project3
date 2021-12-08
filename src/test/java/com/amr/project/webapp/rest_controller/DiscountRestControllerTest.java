package com.amr.project.webapp.rest_controller;

import com.amr.project.AbstractApiTest;
import com.amr.project.model.dto.DiscountDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.dto.UserDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
class DiscountRestControllerTest extends AbstractApiTest {

    @Test
    @DataSet(cleanBefore = true, value = "datasets/discount/Discount.xml")
    @ExpectedDataSet(value = "datasets/discount/expected/discountExpected.xml")
    public void addDiscountTest() throws Exception {

        DiscountDto discount = DiscountDto.builder()
                .minOrder(100)
                .fixedDiscount(10)
                .percentage(10)
                .user(UserDto.builder()
                        .email("user@mail.ru")
                        .username("username")
                        .age(10)
                        .build())
                .shop(ShopDto.builder()
                        .description("shop1")
                        .email("shop1@mail.ru")
                        .isModerateAccept(false)
                        .isModerated(false)
                        .isPretendentToBeDeleted(false)
                        .moderatedRejectReason("null")
                        .name("shop1")
                        .phone("7777777")
                        .rating(1)
                        .build())
                .build();

        String addDiscountUrl = "/api/userlist/addDiscount";
        mvc.perform(
                post(addDiscountUrl)
                        .content(asJsonString(discount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        discount.setShop(null);

        mvc.perform(
                post(addDiscountUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(discount)))
                .andExpect(status().isBadRequest());

        discount.setUser(null);

        mvc.perform(
                post(addDiscountUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(discount)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DataSet(cleanBefore = true, value = "datasets/discount/Discount.xml")
    @ExpectedDataSet(value = "datasets/discount/expected/shopExpected.xml")
    void getShopByIdTest() throws Exception {
        String getShopByIdUrl = "/api/userlist/shop/{id}";
        mvc.perform(MockMvcRequestBuilders.get(getShopByIdUrl, 1))
                .andExpect(status().is2xxSuccessful());

        mvc.perform(MockMvcRequestBuilders.get(getShopByIdUrl, "www"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DataSet(cleanBefore = true, value = "datasets/discount/Discount.xml")
    @ExpectedDataSet(value = "datasets/discount/expected/userExpected.xml")
    void getUserByIdTest() throws Exception {
        String getUserByIdUrl = "/api/userlist/{id}";
        mvc.perform(MockMvcRequestBuilders.get(getUserByIdUrl, 1))
                .andExpect(status().is2xxSuccessful());

        mvc.perform(MockMvcRequestBuilders.get(getUserByIdUrl, "user"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DataSet(cleanBefore = true, value = "datasets/discount/UserList.xml")
    @ExpectedDataSet(value = "datasets/discount/expected/UserList.xml")
    void findAllUsersWithRoleUserTest() throws Exception {
        String getUserWithRoleUser = "/api/userlist/all";
        mvc.perform(MockMvcRequestBuilders
                .get(getUserWithRoleUser)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }


}