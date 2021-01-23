package com.miw.homework.gildedrose.expanded.services;

import com.miw.homework.gildedrose.expanded.models.InventoryItem;
import com.miw.homework.gildedrose.expanded.models.Item;
import com.miw.homework.gildedrose.expanded.models.ordered.OrderedItem;
import com.miw.homework.gildedrose.expanded.models.ordered.DiscontinuedItem;
import com.miw.homework.gildedrose.expanded.models.ordered.PurchasedItem;
import com.miw.homework.gildedrose.expanded.models.ordered.UnderStockedItem;
import com.miw.homework.gildedrose.expanded.utils.StockLevelAwareLongAdder;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.miw.homework.gildedrose.expanded.models.ordered.OrderedItem.INVALID_ORDER_ID;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

/**
 * A service that supports retrieving the current inventory state and buying items; implements surge pricing when applicable.
 */
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
@Service
public class InMemoryInventoryService implements InventoryService {

    public static final int SURGE_MINUTES = 60;
    public static final int SURGE_VIEW_COUNT = 10;
    public static final double SURGE_PRICE_MULTIPLIER = 1.1;

    @NonNull
    InMemoryInventoryStorage storage;

    @Override
    public List<InventoryItem> findAll() {
        storage.incrementViews(LocalDateTime.now());
        return storage.getAllInventoryItems()
                .values()
                .stream()
                .sorted(Comparator.comparing((i) -> i.getItem().getName()))
                .collect(toList());
    }

    @Override
    public OrderedItem buy(int quantity, int id) {
        InventoryItem inventoriedItem = storage.getInventoryItemById(id);
        // TODO: REVIEW: Consider using Optional here...
        if (null != inventoriedItem) {
            int currentStockLevel = inventoriedItem.getStockLevel().intValue();

            if (currentStockLevel >= quantity) { // Happy path...
                inventoriedItem.getStockLevel().subtract(quantity);
                int priceEach = getSurgeOrListPrice(inventoriedItem.getItem().getPrice(), LocalDateTime.now());
                return new PurchasedItem(UUID.randomUUID().toString(), inventoriedItem, quantity, priceEach, quantity*priceEach);
            }
            else { // TODO: REVIEW: Consider throwing custom Exceptions here instead and let the controller formulate an appropriate response
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

    Item createItem(int id, String name, String description, int price) {
        return Item
                .builder()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .build();
    }

    InventoryItem createInventoryItem(int id, Item item) {
        StockLevelAwareLongAdder quantity = new StockLevelAwareLongAdder();
        if (OUT_OF_STOCK_INVENTORY_ID__FOR_DEMO_PURPOSES_ONLY != id) /* Make non-zero for the purposes of simple unit testing */ { quantity.add(new Random().nextInt(256) + 10); }
        return InventoryItem
                .builder()
                .id(id)
                .stockLevel(quantity)
                .item(item)
                .build();
    }

    private void save(InventoryItem inventoriedItem) {
        storage.addInventoryItem(inventoriedItem.getId(), inventoriedItem);
    }

    /**
     * Return a count of the number of times the Inventory was viewed over a period of time.
     *
     * @param maxMinutes number of minutes before latestTime to filter the view date/time(s)
     * @param latestTime end of the period of time to consider
     * @return
     */
    int findNumberOfViews(int maxMinutes, LocalDateTime latestTime) {
        return storage.getViews()
                .stream()
                .filter((d) -> {
                    return ChronoUnit.MINUTES.between(latestTime.minusHours(1), latestTime) <= maxMinutes;
//                    return TimeUnit.MILLISECONDS.toMinutes(dateTime.getTime() - d.getTime()) <= filterMinutes;
                })
                .collect(toList())
                .size();
    }

    /**
     *
     * @param price
     * @param dateTime
     * @return
     */
    int getSurgeOrListPrice(int price, LocalDateTime dateTime) {
        return (findNumberOfViews(SURGE_MINUTES, dateTime) > SURGE_VIEW_COUNT)
                ? (int) Math.floor(SURGE_PRICE_MULTIPLIER * price)
                : price;
    }

}
