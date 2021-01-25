package com.miw.homework.gildedrose.expanded.controllers;

import com.miw.homework.gildedrose.expanded.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@WebMvcTest(PrivateInventoryController.class)
class PrivateInventoryControllerTest {

    private static final String CONTEXT_ROOT = "/private/inventory";

    @Autowired
    private MockMvc mvc;

    @WithMockUser
    @Test
    public void givenAuthenticatedRequestFor_Buy_1_ofInventoryItem_5_shouldSucceedWith200() throws Exception {
        mvc
        .perform(post(CONTEXT_ROOT + "/buy/1/ofItem/5"))
        .andExpect(status().isOk());
    }

    @Test
    public void givenUnAuthenticatedRequestFor_Buy_1_ofInventoryItem_5_shouldFailWith403() throws Exception {
        mvc
        .perform(post(CONTEXT_ROOT + "/buy/1/ofItem/5"))
        .andExpect(status().isForbidden());
    }

    @WithMockUser
    @Test
    public void givenAuthenticatedRequestFor_InventoryListing_shouldSucceedWith200() throws Exception {
        mvc
                .perform(
                        get(CONTEXT_ROOT + "/list")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
    @Test
    public void givenUnAuthenticatedRequestFor_InventoryListing_shouldFailWith403() throws Exception {
        mvc
        .perform(
                get(CONTEXT_ROOT + "/list")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isForbidden());
    }

}