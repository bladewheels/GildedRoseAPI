package com.miw.homework.gildedrose.expanded.security.user.services.auth;

import com.miw.homework.gildedrose.expanded.security.user.User;

import java.util.Optional;

public interface AuthenticationService {

  /**
   * Finds a User by their username.
   *
   * @param token the username
   * @return a {@link User}
   */
  Optional<User> findByToken(String token);

}
