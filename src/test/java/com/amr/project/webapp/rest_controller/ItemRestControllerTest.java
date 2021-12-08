package com.amr.project.webapp.rest_controller;

import com.amr.project.AbstractApiTest;
import com.amr.project.converter.ItemMapper;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.service.abstracts.ItemService;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ItemRestControllerTest extends AbstractApiTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemMapper itemMapper;

    @Test
    @DataSet(cleanBefore = true, value = "dataset/testApiItem/TestItemPersist.xml")
    @ExpectedDataSet(value = "dataset/testApiItem/expected/TestItemPersist.xml")
    public void testItemPersist() throws Exception {
        ItemDto itemDto = ItemDto.builder().name("Test").shopName("Alibaba").description("Test")
                .count(2).rating(0d).price(new BigDecimal(200)).categoriesName(new String[]{"Sport"}).build();
        ItemDto itemDto2 = ItemDto.builder().name("Test").description("Test")
                .count(2).rating(0d).price(new BigDecimal(200)).categoriesName(new String[]{"Sport"}).build();
        ItemDto itemDto3 = ItemDto.builder().name("Test").shopName("Alibaba").description("Test")
                .count(2).rating(0d).price(new BigDecimal(200)).build();
        mvc.perform(
                post("/shop/item")
                        .content(asJsonString(itemDto))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful());

        mvc.perform(
                post("/shop/item")
                        .content(asJsonString(itemDto2))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError());

        mvc.perform(
                post("/shop/item")
                        .content(asJsonString(itemDto3))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError());

        mvc.perform(
                post("/shop/item")
                        .content(asJsonString(null))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError());

    }

    @Test
    @DataSet(cleanBefore = true, value = "dataset/testApiItem/TestItemDelete.xml")
    @ExpectedDataSet(value = "dataset/testApiItem/expected/TestItemDelete.xml")
    public void testItemDelete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/shop/item/{id}", 1))
                .andExpect(status().is2xxSuccessful());
        mvc.perform(MockMvcRequestBuilders.delete("/shop/item/{id}", 2))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DataSet(value = "dataset/testApiItem/TestItemUpdate.xml")
    @ExpectedDataSet(value = "dataset/testApiItem/expected/TestItemUpdate.xml")
    public void testItemUpdate() throws Exception {
        ItemDto itemDto = itemMapper.itemToDto(itemService.getByKey(1l));
        itemDto.setShopName("Alibaba");
        itemDto.setCategoriesName(new String[]{"Sport"});
        itemDto.setName("TestTest");

        ItemDto itemDto1 = itemMapper.itemToDto(itemService.getByKey(1l));
        itemDto1.setShopName(null);

        ItemDto itemDto2 = itemMapper.itemToDto(itemService.getByKey(1l));
        itemDto2.setCategoriesName(null);

        mvc.perform(MockMvcRequestBuilders.put("/shop/item")
                .content(asJsonString(itemDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        mvc.perform(MockMvcRequestBuilders.put("/shop/item")
                .content(asJsonString(itemDto1)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        mvc.perform(MockMvcRequestBuilders.put("/shop/item")
                .content(asJsonString(itemDto2)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}

