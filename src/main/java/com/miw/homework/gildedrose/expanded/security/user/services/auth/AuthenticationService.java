package com.miw.homework.gildedrose.expanded.security.user.services.auth;

import com.miw.homework.gildedrose.expanded.security.user.User;

import java.util.Optional;

public interface AuthenticationService {

//  /**
//   * Logs in with the given {@code username} and {@code password}.
//   *
//   * @param username
//   * @param password
//   * @return an {@link Optional} of a user when login succeeds
//   */
//  Optional<String> login(String username, String password);

  /**
   * Finds a user by Id.
   *
   * @param token user Id
   * @return
   */
  Optional<User> findByToken(String token);

//  /**
//   * Logs out the given input {@code user}.
//   *
//   * @param user the user to logout
//   */
//  void logout(User user);
}
