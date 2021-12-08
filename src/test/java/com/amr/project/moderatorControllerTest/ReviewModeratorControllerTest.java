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
@DataSet(value = {"Review.xml",
        "User.xml","Image.xml","Shop.xml","Country.xml"} , cleanBefore = true, cleanAfter = true)
public class ReviewModeratorControllerTest extends AbstractIntegrationTest {

    private final MockMvc mockMvc;
    private final String url = "/moderator/api/reviews/";

    @Autowired
    public ReviewModeratorControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void getAllUnmoderatedReviewsTest() throws Exception {

        mockMvc.perform(get(url + "getUnmoderatedReviews"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public  void moderatedFalseTest() throws Exception {
        mockMvc.perform(get(url + "getUnmoderatedReviews"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].moderated").value(false));
    }

    @Test
    public void getOneUnmoderatedReviewTest() throws Exception {
        mockMvc.perform(get(url + "getOneUnmoderatedReview/{id}", 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void unmoderatedReviewNotFoundTest() throws Exception {
        mockMvc.perform(get(url + "getOneUnmoderatedReview/42"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void unmoderatedReviewsCountIsOkTest() throws Exception {
        mockMvc.perform(get(url + "getUnmoderatedReviewsCount"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void ReviewIdIsExistAndOkTest() throws Exception {

        mockMvc.perform(put(url + "editReview")
                        .contentType(MediaType.APPLICATION_JSON).content(
                                "{"  + "\"id\": 1," +
                                        "\"date\": \"2021-09-26 17:48:51\"," +
                                        "\"dignity\": \"dignity_review_user4\"," +
                                        "\"flaw\": \"flaw_review_user4\"," +
                                        "\"text\": \"text_review_user4\"," +
                                        "\"rating\": 4," +
                                        "\"userId\": 1," +
                                        "\"itemId\": 1," +
                                        "\"shopId\": 1," +
                                        "\"shopName\": \"shop1\"," +
                                        "\"moderateAccept\": true," +
                                        "\"moderated\": true," +
                                        "\"moderatedRejectReason\": null" +
                                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.moderated").value(true))
                .andExpect(jsonPath("$.moderateAccept").value(true))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    public void reviewIdIsNotExistAndBadRequest() throws Exception {
        mockMvc.perform(put(url + "editReview")
                        .contentType(MediaType.APPLICATION_JSON).content(
                                "{"  + "\"id\": 42," +
                                        "\"date\": \"2021-09-26 17:48:51\"," +
                                        "\"dignity\": \"dignity_review_user4\"," +
                                        "\"flaw\": \"flaw_review_user4\"," +
                                        "\"text\": \"text_review_user4\"," +
                                        "\"rating\": 4," +
                                        "\"userId\": 1," +
                                        "\"itemId\": 1," +
                                        "\"shopId\": 1," +
                                        "\"shopName\": \"shop1\"," +
                                        "\"moderateAccept\": true," +
                                        "\"moderated\": true," +
                                        "\"moderatedRejectReason\": null" +
                                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
