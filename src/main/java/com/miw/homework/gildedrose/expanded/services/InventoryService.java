package com.miw.homework.gildedrose.expanded.services;

import com.miw.homework.gildedrose.expanded.models.InventoriedItem;
import com.miw.homework.gildedrose.expanded.models.Item;

import java.util.List;
import java.util.Optional;

/**
 * Offers some CRUD operations on {@link Item}.
 */
public interface InventoryService {

    Item save(Item item);

    Optional<Item> find(String id);

    Optional<Item> findByName(String email);

    List<InventoriedItem> findAll();

    Optional<Item> remove(Item item);

    Optional<Item> decreaseStock(Item item, int decreaseBy);

    Optional<Item> increaseStock(Item item, int increaseBy);
}
