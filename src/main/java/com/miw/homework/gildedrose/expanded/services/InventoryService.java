package com.miw.homework.gildedrose.expanded.services;

import com.miw.homework.gildedrose.expanded.models.InventoryItem;
import com.miw.homework.gildedrose.expanded.models.Item;
import com.miw.homework.gildedrose.expanded.models.ordered.OrderedItem;

import java.util.List;

/**
 * Do some CRUD operations on the inventory.
 */
public interface InventoryService {

    int OUT_OF_STOCK_INVENTORY_ID__FOR_DEMO_PURPOSES_ONLY = 3;

    /**
     * Get a List of {@link InventoryItem}s.
     *
     * @return a List of {@link InventoryItem}s
     */
    List<InventoryItem> findAll();

    /**
     * Buy some quantity of an InventoryItem.
     *
     * @param quantity
     * @param inventoriedItemId
     * @return an OrderedItem representing a purchase
     */
    OrderedItem buy(int quantity, int inventoriedItemId);
}
