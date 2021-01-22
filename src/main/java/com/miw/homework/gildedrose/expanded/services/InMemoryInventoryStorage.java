package com.miw.homework.gildedrose.expanded.services;

import com.miw.homework.gildedrose.expanded.models.InventoryItem;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class InMemoryInventoryStorage {

    private Map<Integer, InventoryItem> items = new ConcurrentHashMap<>();
    // Currently, this List grows without bound...
    // TODO: Prune this list, we only need Dates from the last SURGE_MINUTES or so.
    private List<LocalDateTime> views = new CopyOnWriteArrayList<>();
// LocalDateTime.now()
    public void incrementViewsWithThisDate(LocalDateTime dateTime) {
        views.add(dateTime);
    }

    public Collection<LocalDateTime> getViews() {
        return views;
    }

    public Map<Integer, InventoryItem> getAllInventoryItems() {
        return items;
    }

    public InventoryItem getInventoryItem(int inventoriedItemId) {
        return items.get(inventoriedItemId);
    }

    public void addInventoryItem(int id, InventoryItem inventoriedItem) {
        items.put(id, inventoriedItem);
    }
}
