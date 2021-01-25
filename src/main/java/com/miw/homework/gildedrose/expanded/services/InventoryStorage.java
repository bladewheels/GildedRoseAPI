package com.miw.homework.gildedrose.expanded.services;

import com.miw.homework.gildedrose.expanded.models.InventoryItem;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

public interface InventoryStorage {

    void incrementViews(LocalDateTime dateTime);

    Collection<LocalDateTime> getViews();

    Map<Integer, InventoryItem> getAllInventoryItems();

    void putInventoryItem(int id, InventoryItem inventoriedItem);

    InventoryItem getInventoryItemById(int id);
}
