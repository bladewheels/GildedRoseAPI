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
public class InMemoryInventoryStorage implements InventoryStorage {

    // Currently, this List grows without bound...
    // TODO: Prune this list, we only need Dates from the last SURGE_MINUTES or so.
    private List<LocalDateTime> views = new CopyOnWriteArrayList<>();
    private Map<Integer, InventoryItem> items = new ConcurrentHashMap<>();

    @Override
    public void incrementViews(LocalDateTime dateTime) { views.add(dateTime); }

    @Override
    public Collection<LocalDateTime> getViews() {
        return views;
    }

    @Override
    public Map<Integer, InventoryItem> getAllInventoryItems() {
        return items;
    }

    @Override
    public void putInventoryItem(int id, InventoryItem inventoriedItem) {
        items.put(id, inventoriedItem);
    }

    @Override
    public InventoryItem getInventoryItemById(int id) { return items.get(id); }
}
