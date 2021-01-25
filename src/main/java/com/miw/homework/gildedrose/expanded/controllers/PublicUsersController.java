package com.miw.homework.gildedrose.expanded.controllers;

import com.miw.homework.gildedrose.expanded.security.user.User;
import com.miw.homework.gildedrose.expanded.security.user.services.UserService;
import com.miw.homework.gildedrose.expanded.services.ClientRelationshipService;
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
  UserService userService;

  @PostMapping("/register")
  ResponseEntity<String> register(@RequestParam("email") final String email) {

    if (ClientRelationshipService.isShippingAndBillingSetupForThisEmailAddress(email)) {
      final String uuid = UUID.randomUUID().toString();
      userService
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
}
