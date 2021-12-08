package com.amr.project.homePageControllerTest;

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
public class HomePageControllerTest extends AbstractIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getHomePageController() throws Exception {
        mockMvc.perform(get("/homepage"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}