package com.amr.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.junit5.api.DBRider;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProjectApplication.class})
@AutoConfigureMockMvc
@DBRider
@DBUnit(allowEmptyFields = true)
@DbUnitConfiguration(databaseConnection = "h2DataSource")
@TestPropertySource(
        locations = "classpath:/application-integrationtest.properties")
public abstract class AbstractApiTest {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    protected String asJsonString(final Object obj) {
        try {
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
