package com.amr.project.webapp.rest_controller;

import com.amr.project.AbstractApiTest;
import com.amr.project.model.dto.CategoryDto;
import com.amr.project.model.dto.CountryDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminRestControllerTest extends AbstractApiTest {

    @Test
    public void adminShopsTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/api/shops").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void adminItemsTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/api/items").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void adminUsersTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/api/users").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void adminCategoriesTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/api/categories").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void adminAddCategoryTest() throws Exception {
        CategoryDto categoryDto = CategoryDto.builder()
                .name("newCategory")
                .build();
        mvc.perform(MockMvcRequestBuilders.post("/admin/api/categories").content(asJsonString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void adminAddCountryTest() throws Exception {
        CountryDto countryDto = CountryDto.builder().name("RRR").build();
        mvc.perform((MockMvcRequestBuilders.post("/admin/api/countries")).content(asJsonString(countryDto)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void adminDeleteCountryTest() throws Exception {
        mvc.perform((MockMvcRequestBuilders.delete("/admin/api/countries/{id}",4)))
                .andExpect(status().isOk());
        mvc.perform((MockMvcRequestBuilders.delete("/admin/api/countries/{id}",4)))
                .andExpect(status().is4xxClientError());
    }

}
