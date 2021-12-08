package com.amr.project.webapp.controller;

import com.amr.project.AbstractIntegrationTest;
import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataSet(value = {"Item.xml", "Item-category.xml",
        "Category.xml", "Shop.xml", "Shop-item.xml",
        "Country.xml","Image.xml","Item-image.xml"}, cleanBefore = true, cleanAfter = true)
class ShopControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shopPage() throws Exception {
        mockMvc.perform(get("/shop/{id}",1L))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void itemPage() throws Exception {
        mockMvc.perform(get("/shop/{id}/item/{itemId}",1L,1L))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}