package com.miw.homework.gildedrose.expanded.security.user.services.auth;

import com.miw.homework.gildedrose.expanded.security.user.User;
import com.miw.homework.gildedrose.expanded.security.user.services.InMemoryUserService;
import com.miw.homework.gildedrose.expanded.security.user.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public final class UUIDAuthenticationService implements AuthenticationService {

  @NonNull
  UserService users;

  @Override
  public Optional<User> findByToken(final String token) {
    return users.find(token);
  }

}

