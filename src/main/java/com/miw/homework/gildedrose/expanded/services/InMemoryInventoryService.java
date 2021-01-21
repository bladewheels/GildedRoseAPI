package com.miw.homework.gildedrose.expanded.services;

import com.miw.homework.gildedrose.expanded.models.InventoryItem;
import com.miw.homework.gildedrose.expanded.models.Item;
import com.miw.homework.gildedrose.expanded.models.ordered.OrderedItem;
import com.miw.homework.gildedrose.expanded.models.ordered.DiscontinuedItem;
import com.miw.homework.gildedrose.expanded.models.ordered.PurchasedItem;
import com.miw.homework.gildedrose.expanded.models.ordered.UnderStockedItem;
import com.miw.homework.gildedrose.expanded.utils.StockLevelAwareLongAdder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import static com.miw.homework.gildedrose.expanded.models.ordered.OrderedItem.INVALID_ORDER_ID;
import static java.util.stream.Collectors.toList;

/**
 * A service class that encapsulates some data structures for representing the inventory state
 * and offers methods to manipulate the same; implements surge pricing when applicable.
 */
@Service
public class InMemoryInventoryService implements InventoryService {

    public static final int SURGE_MINUTES = 60;
    public static final int SURGE_VIEW_COUNT = 10;
    public static final double SURGE_PRICE_MULTIPLIER = 1.1;

    private final Map<Integer, InventoryItem> inventoriedItems = new ConcurrentHashMap<>();
    // Currently, this List grows without bound...
    // TODO: Prune this list, we only need Dates from the last SURGE_MINUTES or so.
    private List<Date> inventoryViews = new CopyOnWriteArrayList<>();

    @Override
    public List<InventoryItem> findAll() {
        inventoryViews.add(new Date());
        return inventoriedItems
                .values()
                .stream()
                .sorted(Comparator.comparing((i) -> i.getItem().getName()))
                .collect(toList());
    }

    @Override
    public OrderedItem buy(int quantity, int inventoriedItemId) {

        // TODO: REVIEW: Consider using Optional here...
        InventoryItem inventoriedItem = inventoriedItems.get(inventoriedItemId);

        if (null != inventoriedItem) {
            int currentStockLevel = inventoriedItem.getStockLevel().intValue();

            if (currentStockLevel >= quantity) {
                inventoriedItem.getStockLevel().subtract(quantity);
                int priceEach = getSurgeOrRegularPrice(inventoriedItem.getItem().getPrice());
                return PurchasedItem
                        .builder()
                        .orderId(UUID.randomUUID().toString())
                        .quantity(quantity)
                        .itemFromInventory(inventoriedItem)
                        .priceEach(priceEach)
                        .totalCharged(quantity * priceEach)
                        .build();
            } else {
                return UnderStockedItem
                        .builder()
                        .orderId(INVALID_ORDER_ID)
                        .itemFromInventory(inventoriedItem)
                        .quantity(quantity)
                        .inStock(currentStockLevel)
                        .build();
            }
        } else {
            return new DiscontinuedItem();
        }
    }

    @PostConstruct
    void init() {
        save(
            createInventoryItem(4,
                createItem(1, "Thing 0x001", "A reeallly nice thing!", 35_612))
        );
        save(
            createInventoryItem(5,
                createItem(2, "A Shiny thing", "A really quite shiny thing!", 75_612))
        );
        save(
            createInventoryItem(6,
                createItem(3, "Something else ENTIRELY", "WOW, what a thing to sell!", 45_612))
        );
        save(
            createInventoryItem(1,
                createItem(4, "Yet Another Thing", "So plain, SO non-descriptive...", 55_612))
        );
        save(
            createInventoryItem(3,
                createItem(5, "WTF", "Huh?", 5_612))
        );

        save(
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
        quantity.add(new Random().nextInt(256));
        return InventoryItem
                .builder()
                .id(id)
                .stockLevel(quantity)
                .item(item)
                .build();
    }

    private void save(InventoryItem inventoriedItem) {
        inventoriedItems.put(inventoriedItem.getId(), inventoriedItem);
    }

    int findNumberOfViewsLastMinutes(int filterMinutes) {
        return inventoryViews
                .stream()
                .filter((d) -> {
                    return TimeUnit.MILLISECONDS.toMinutes(new Date().getTime() - d.getTime()) <= filterMinutes;
                })
                .collect(toList())
                .size();
    }

    int getSurgeOrRegularPrice(int price) {
        return (findNumberOfViewsLastMinutes(SURGE_MINUTES) > SURGE_VIEW_COUNT)
                ? (int) Math.floor(SURGE_PRICE_MULTIPLIER * price)
                : price;
    }
}
