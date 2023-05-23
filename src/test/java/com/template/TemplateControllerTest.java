package com.template;

import com.template.config.TemplateSpringBootApplication;
import com.template.config.TestJpaConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {TemplateSpringBootApplication.class})
@AutoConfigureMockMvc
public class TemplateControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void getTest() throws Exception {
        MvcResult result = mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertTrue(result.getResponse().getContentAsString().contains("<h1>Template page</h1>"));
    }
}
