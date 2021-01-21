package com.miw.homework.gildedrose.expanded.controllers;

import com.miw.homework.gildedrose.expanded.security.user.User;
import com.miw.homework.gildedrose.expanded.security.user.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

/**
 * A web controller for private resources i.e. {@link User}.
 */
@RestController
@RequestMapping("/private/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PrivateUsersController {

  @NonNull
  UserService service;

  @PostMapping("/remove")
  boolean remove(@AuthenticationPrincipal final User user) {
    // TODO: REVIEW: This endpoint/controller only makes sense if the user has an ADMIN Role,
    // Roles have not yet been implemented...
    return false; // ,,,but if they were, we could do: return service.remove(user).isPresent();
  }
}
