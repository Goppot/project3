package com.amr.project.userPageControllerTest;

import com.amr.project.AbstractIntegrationTest;
import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@DataSet(value = {"User.xml", "Image.xml"}, cleanBefore = true, cleanAfter = true)
public class UserPageControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getUserPageController() throws Exception {
        mockMvc.perform(get("/user/{id}", 1))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
