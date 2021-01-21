package com.miw.homework.gildedrose.expanded.controllers;

import com.miw.homework.gildedrose.expanded.models.InventoryItem;
import com.miw.homework.gildedrose.expanded.models.ordered.OrderedItem;
import com.miw.homework.gildedrose.expanded.services.InventoryService;
import com.miw.homework.gildedrose.expanded.security.user.User;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

/**
 * A web controller for private resources i.e. {@link InventoryItem}.
 */
@RestController
@RequestMapping("/private/inventory")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PrivateInventoryController {

  @NonNull
  InventoryService service;

  @GetMapping("/list")
  List<InventoryItem> list(@AuthenticationPrincipal final User user) {
    return service.findAll();
  }
  @PostMapping("/buy/{quantity}/ofItem/{id}")
  OrderedItem buy(@AuthenticationPrincipal final User user,
                  @PathVariable String id,
                  @PathVariable String quantity) {
    return service.buy(Integer.valueOf(quantity), Integer.valueOf(id));
  }
}
