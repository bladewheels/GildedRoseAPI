package com.miw.homework.gildedrose.expanded.security.user.services;

import com.miw.homework.gildedrose.expanded.security.user.User;

import java.util.Optional;

/**
 * Offers some CRUD operations on {@link User}.
 */
public interface UserService {

  User save(User user);

  Optional<User> find(String id);

  Optional<User> remove(User user);
}
