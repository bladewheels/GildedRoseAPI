package com.miw.homework.gildedrose.expanded.controllers;

import com.miw.homework.gildedrose.expanded.security.user.User;
import com.miw.homework.gildedrose.expanded.security.user.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * SpringBoot tests for the public controller for Users.
 *
 * TODO: REVIEW: Refactor(?) to use @WebMvc, as they will run w/o boot the whole app context
 * Watch out for the security filter chain being autoloaded though, might not be so simple to configure?
 */
@SpringBootTest
@AutoConfigureMockMvc
class PublicUsersControllerTest {

    // TODO: Find more dynamic way to get context root + path...
    private static final String REGISTRATION_URL = "/public/users/register";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    public void registrationShouldReturnWelcomeMessageWhenValidEmailSupplied() throws Exception {
        when(service.save(any(User.class))).thenReturn(any(User.class));
        this.mockMvc
                .perform(
                    post(REGISTRATION_URL)
                        .param("email", "customer_x@cheese.pizza"))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Welcome ")));
    }

    @Test
    public void registrationShouldReturnSorryMessageWhenInValidEmailSupplied() throws Exception {
        this.mockMvc
                .perform(
                    post(REGISTRATION_URL)
                        .param("email", "a.b@c.de"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Sorry, ")));
    }
}