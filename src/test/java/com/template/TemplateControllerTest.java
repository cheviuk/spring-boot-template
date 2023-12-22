package com.template;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

//@SpringBootTest(classes = {TemplateSpringBootApplication.class})
//@AutoConfigureMockMvc
public class TemplateControllerTest {
//    @Autowired
//    private MockMvc mvc;

    @Test
    void getTest() throws Exception {
//        MvcResult result = mvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andReturn();
//        Assertions.assertTrue(result.getResponse().getContentAsString().contains("<h1>Template page</h1>"));
    }
}
