package com.miw.homework.gildedrose.expanded.models.ordered;

import com.miw.homework.gildedrose.expanded.models.InventoryItem;
import lombok.Value;

import java.io.Serializable;

/**
 * This class represents a response to an API user who has tried to buy an {@link InventoryItem}
 * that does not exist in inventory.
 */
@Value
public class DiscontinuedItem implements Serializable, OrderedItem {
    private static final long serialVersionUID = 1L;
    private static final String DISCONTINUED_ITEM_WARNING = "Sorry, this Item is no longer available for purchase.";

    final String id = "n/a";
    final int quantity = 0;
    final int priceEach = 0;
    final int totalCharged = 0;
    final String notice = DISCONTINUED_ITEM_WARNING;
}
