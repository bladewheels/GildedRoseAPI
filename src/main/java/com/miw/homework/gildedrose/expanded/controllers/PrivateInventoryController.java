package com.miw.homework.gildedrose.expanded.controllers;

import com.miw.homework.gildedrose.expanded.models.InventoryItem;
import com.miw.homework.gildedrose.expanded.models.Item;
import com.miw.homework.gildedrose.expanded.models.ordered.DiscontinuedItem;
import com.miw.homework.gildedrose.expanded.models.ordered.OrderedItem;
import com.miw.homework.gildedrose.expanded.models.ordered.UnderStockedItem;
import com.miw.homework.gildedrose.expanded.services.InventoryService;
import com.miw.homework.gildedrose.expanded.security.user.User;
import com.miw.homework.gildedrose.expanded.utils.StockLevelAwareLongAdder;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.miw.homework.gildedrose.expanded.models.ordered.OrderedItem.INVALID_ORDER_ID;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

/**
 * A web controller for private resources i.e. {@link InventoryItem}s.
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
    // Guards:
    // TODO: REVIEW: Returning placeholders when parameters evaluate to NaN is quick&dirty but opaque to callers
    // i.e. throw Exceptions instead...
    try { Integer.valueOf(id); } catch (NumberFormatException nfe) { return getPlaceholderOrderItem(true, null, null); }
    try { Integer.valueOf(quantity); } catch (NumberFormatException nfe) { return getPlaceholderOrderItem(false, id, quantity); }

    return service.buy(Integer.valueOf(quantity), Integer.valueOf(id));
  }

  private OrderedItem getPlaceholderOrderItem(boolean malformedId, String id, String quantity) {
    if (malformedId) {
      return new DiscontinuedItem();
    }
    else {
      return new UnderStockedItem(
                  INVALID_ORDER_ID,
                  new InventoryItem(
                          Integer.valueOf(id),
                          new Item(Integer.valueOf(id),"Invalid quantity provided","i.e. quantity = " + quantity,0), new StockLevelAwareLongAdder()), 0, 0
      );
    }
  }
}
