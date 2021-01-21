package com.miw.homework.gildedrose.expanded.services;

import com.miw.homework.gildedrose.expanded.models.InventoryItem;
import com.miw.homework.gildedrose.expanded.models.Item;
import com.miw.homework.gildedrose.expanded.models.ordered.OrderedItem;

import java.util.List;

/**
 * Offers a couple of CRUD operations on the inventory.
 */
public interface InventoryService {

    List<InventoryItem> findAll();

    OrderedItem buy(int quantity, int inventoriedItemId);
}
