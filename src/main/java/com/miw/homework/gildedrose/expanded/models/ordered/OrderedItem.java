package com.miw.homework.gildedrose.expanded.models.ordered;

import com.miw.homework.gildedrose.expanded.models.InventoryItem;

/**
 * A marker interface for responses to API users attempting to buy {@link InventoryItem}s
 */
public interface OrderedItem {

    static final String INVALID_ORDER_ID = "n/a";
}
