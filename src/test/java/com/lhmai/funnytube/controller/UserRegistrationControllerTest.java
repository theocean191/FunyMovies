package com.lhmai.funnytube.controller;

import com.lhmai.funnytube.FunnytubeApplication;
import com.lhmai.funnytube.ViewResolverFactory;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FunnytubeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRegistrationControllerTest {
    @Autowired
    private UserRegistrationController userRegistrationController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        // Process mock annotations
        MockitoAnnotations.initMocks(this);

        // Setup Spring test in stand-alone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(userRegistrationController).setViewResolvers(ViewResolverFactory.thymleafViewResolver())
                .build();
    }

    @Test
    public void getRegistrationForm_CallApi_ReturnStatusOK() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("registration"))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assert.assertTrue(StringUtils.isNotEmpty(content));
    }

}