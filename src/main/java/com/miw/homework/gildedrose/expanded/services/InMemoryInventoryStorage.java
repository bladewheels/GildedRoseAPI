package com.miw.homework.gildedrose.expanded.services;

import com.miw.homework.gildedrose.expanded.models.InventoryItem;
import com.miw.homework.gildedrose.expanded.models.Item;
import com.miw.homework.gildedrose.expanded.utils.StockLevelAwareLongAdder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
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


    /**
     * TODO: Move this to the data layer
     */
    @PostConstruct
    private void init() {
        putInventoryItem(4,
                createInventoryItem(4,
                        createItem(1, "Thing 0x001", "A reeallly nice thing!", 35_612))
        );
        putInventoryItem(5,
                createInventoryItem(5,
                        createItem(2, "A Shiny thing", "A really quite shiny thing!", 75_612))
        );
        putInventoryItem(6,
                createInventoryItem(6,
                        createItem(3, "Something else ENTIRELY", "WOW, what a thing to sell!", 45_612))
        );
        putInventoryItem(1,
                createInventoryItem(1,
                        createItem(4, "Yet Another Thing", "So plain, SO non-descriptive...", 55_612))
        );
        putInventoryItem(3,
                createInventoryItem(3,
                        createItem(5, "WTF", "Huh?", 5_612))
        );
        putInventoryItem(2,
                createInventoryItem(2,
                        createItem(6, "Such a thing", "Sheesh, what is this thing?", 15_309))
        );
    }

    private Item createItem(int id, String name, String description, int price) {
        return Item
                .builder()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .build();
    }

    private InventoryItem createInventoryItem(int id, Item item) {
        StockLevelAwareLongAdder quantity = new StockLevelAwareLongAdder();
//        if (OUT_OF_STOCK_INVENTORY_ID__FOR_DEMO_PURPOSES_ONLY != id) /* Make non-zero for the purposes of simple unit testing */ {
            quantity.add(new Random().nextInt(256) + 10);
//        }
        return InventoryItem
                .builder()
                .id(id)
                .stockLevel(quantity)
                .item(item)
                .build();
    }

}
