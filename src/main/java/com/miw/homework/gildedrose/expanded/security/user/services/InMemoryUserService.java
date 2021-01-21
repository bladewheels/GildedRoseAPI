package com.miw.homework.gildedrose.expanded.security.user.services;

import com.miw.homework.gildedrose.expanded.security.user.User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Optional.ofNullable;

/**
 * An in-memory implementation of {@UserService}.
 */
@Service
final class InMemoryUserService implements UserService {

  private final Map<String, User> users = new ConcurrentHashMap<>();

  @Override
  public User save(final User user) {
    return users.put(user.getId(), user);
  }

  @Override
  public Optional<User> find(final String id) {
    return ofNullable(users.get(id));
  }

  @Override
  public Optional<User> remove(User user) {
    return Optional.ofNullable(users.remove(user.getId()));
  }
}
