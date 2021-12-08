package com.amr.project.moderatorControllerTest;

import com.amr.project.AbstractIntegrationTest;
import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@DataSet(value = {"Item.xml", "Item-category.xml",
        "Category.xml", "Shop.xml", "Shop-item.xml",
        "Country.xml"} , cleanBefore = true, cleanAfter = true)
public class ItemModeratorControllerTest extends AbstractIntegrationTest {

    private final MockMvc mockMvc;
    private final String url = "/moderator/api/items/";

    @Autowired
    public ItemModeratorControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void getAllUnmoderatedItemsTest() throws Exception {
        mockMvc.perform(get(url + "getUnmoderatedItems"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(6));
    }

    @Test
    public  void moderatedFalseTest() throws Exception {
        mockMvc.perform(get(url + "getUnmoderatedItems"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].moderated").value(false));
    }

    @Test
    public void getUnmoderatedItemTest() throws Exception {
        mockMvc.perform(get(url + "getOneUnmoderatedItem/{id}", 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void getUnmoderatedItemNotFoundTest() throws Exception {
        mockMvc.perform(get(url + "getOneUnmoderatedItem/55"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void itemIdIsExistAndOkTest() throws Exception {
        mockMvc.perform(put(url + "editItem")
                .contentType(MediaType.APPLICATION_JSON).content(
                        "{"  + "\"id\": 1," +
                                "\"name\": \"item1\"," +
                                "\"categoriesName\": []," +
                                "\"count\": 100," +
                                "\"price\": 111111.00," +
                                "\"images\": []," +
                                "\"rating\": 1.0," +
                                "\"description\": \"item1_description\"," +
                                "\"shopName\": \"shop1\"," +
                                "\"moderateAccept\": true," +
                                "\"moderated\": true," +
                                "\"pretendentToBeDeleted\": false," +
                                "\"moderatedRejectReason\": null," +
                                "\"categories\": []," +
                                "\"reviews\": []," +
                                "\"shopId\": 1" +
                                "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.moderated").value(true))
                .andExpect(jsonPath("$.moderateAccept").value(true))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    public void itemIdIsNotExistAndBadRequest() throws Exception {
        mockMvc.perform(put(url + "editItem")
                .contentType(MediaType.APPLICATION_JSON).content(
                        "{"  + "\"id\": 55," +
                                "\"name\": \"item4\"," +
                                "\"categoriesName\": []," +
                                "\"count\": 100," +
                                "\"price\": 111111.00," +
                                "\"images\": []," +
                                "\"rating\": 1.0," +
                                "\"description\": \"item1_description\"," +
                                "\"shopName\": \"shop1\"," +
                                "\"moderateAccept\": true," +
                                "\"moderated\": true," +
                                "\"pretendentToBeDeleted\": false," +
                                "\"moderatedRejectReason\": null," +
                                "\"categories\": []," +
                                "\"reviews\": []," +
                                "\"shopId\": 1" +
                                "}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void itemsCountIsOkTest() throws Exception {
        mockMvc.perform(get(url + "getUnmoderatedItemsCount"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("6"));
    }
}
