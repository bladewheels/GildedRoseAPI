package com.miw.homework.gildedrose.expanded.controllers;

import com.miw.homework.gildedrose.expanded.security.user.User;
import com.miw.homework.gildedrose.expanded.security.user.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

/**
 * A web controller for registering new {@link User}s, identified by their email address.
 */
@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PublicUsersController {

  @NonNull
  UserService service;

  @PostMapping("/register")
  ResponseEntity<String> register(@RequestParam("email") final String email) {

    if (isShippingAndBillingSetupForThisEmailAddress(email)) {
      final String uuid = UUID.randomUUID().toString();
      service
        .save(
          User
            .builder()
            .id(uuid)
            .username(email)
            .build()
        );
      return new ResponseEntity<>(
                  "Welcome " + email + ", please use the following API token to shop at Gilded Rose ===>" + uuid + "<===.",
                  HttpStatus.CREATED);
    }
    else {
      return new ResponseEntity<>(
              "Sorry, that email address is not setup for shipping/billing - please visit https://gildedrose.com/accounts to set that up first.",
              HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Bogus remote service call to validate email addresses eligible for API registration.
   *
   * @param email
   * @return true for email addresses eligible for API registration
   */
  private boolean isShippingAndBillingSetupForThisEmailAddress(String email) {
    return email.equalsIgnoreCase("customer_x@go.to") ||
            email.equalsIgnoreCase("customer_x@the.wang") ||
            email.equalsIgnoreCase("customer_x@us.online") ||
            email.equalsIgnoreCase("customer_x@cheese.pizza");
  }
}
