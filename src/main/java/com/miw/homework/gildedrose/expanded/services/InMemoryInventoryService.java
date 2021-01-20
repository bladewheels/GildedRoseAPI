package com.miw.homework.gildedrose.expanded.services;

import com.miw.homework.gildedrose.expanded.models.InventoriedItem;
import com.miw.homework.gildedrose.expanded.models.Item;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
public class InMemoryInventoryService implements InventoryService {

    // These 2 collections share the same keys i.e. Item IDs:
    private final Map<String, Item> items = new ConcurrentHashMap<>();
    private final Map<String, LongAdder> itemCounts = new ConcurrentHashMap<>();

    @Override
    public Item save(Item item) { return items.put(item.getId(), item); }

    @Override
    public Optional<Item> find(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<Item> findByName(String email) {
        return Optional.empty();
    }

    @Override
    public List<InventoriedItem> findAll() {

        Map<String, Object> mergedMap = new HashMap<>(items);
        itemCounts
            .forEach((key, value) -> {
                mergedMap.merge(key, value, (item, longAdder) -> new InventoriedItem(((Item) item), ((LongAdder) longAdder).sum()));
            }
        );

    return mergedMap
            .values()
            .stream()
            .map((i) -> {
                return (InventoriedItem) i;
            })
            .sorted(Comparator.comparing((i) -> i.getItem().getName()))
            .collect(toList());

//        return Stream.of(items, itemCounts)
//                .flatMap(map -> map.entrySet().stream())
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
////                        Map.Entry::getValue,
//                        (item, longAdder) -> new InventoriedItem(item, longAdder.sum())));

//        return Stream
//                .concat(items.entrySet().stream(), itemCounts.entrySet().stream())
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        Map.Entry::getValue,
//                        (item, longAdder) -> new InventoriedItem()
//                 ))
//                .values().stream().sorted(Comparator.comparing(InventoriedItem::getName)).collect(toList());

//        Stream.concat(items.entrySet().stream(), itemCounts.entrySet().stream())
//                .collect(Collectors.toMap(
//                    Map.Entry::getKey,
//                    Map.Entry::getValue,
//                    (item, longAdder) -> new InventoriedItem(item, longAdder.sum())));

//        return items.values().stream().sorted(Comparator.comparing(InventoriedItem::getName)).collect(toList());

//        return items.values().stream().sorted(Comparator.comparing(Item::getName)).collect(toList());
        /**
         * Try introducing a new type e.g. InventoriedItem
         * Item item
         * long stockLevel
         *
         * and use streams to merge the 2 maps i.e. items & itemsCount
         * like so:
         *
         * Map<String, Employee> result = Stream.concat(map1.entrySet().stream(), map2.entrySet().stream())
         *   .collect(Collectors.toMap(
         *     Map.Entry::getKey,
         *     Map.Entry::getValue,
         *     (value1, value2) -> new Employee(value2.getId(), value1.getName())));
         *
         */
    }

    @Override
    public Optional<Item> remove(Item item) {
        return Optional.empty();
    }

    @Override
    public Optional<Item> decreaseStock(Item item, int decreaseBy) {
        return Optional.empty();
    }

    @Override
    public Optional<Item> increaseStock(Item item, int increaseBy) {
        return Optional.empty();
    }

    @PostConstruct
    public void init() {
        initItems();
        initItemCounts();
    }

    private void initItems() {
        save(
            Item
                .builder()
                .id(UUID.randomUUID().toString())
                .name("Thing 0x001")
                .description("A reeallly nice thing!")
                .price(35_612)
                .build()
        );
        save(
            Item
                .builder()
                .id(UUID.randomUUID().toString())
                .name("A Shiny thing")
                .description("A really quite shiny thing!")
                .price(75_612)
                .build()
        );
        save(
            Item
                .builder()
                .id(UUID.randomUUID().toString())
                .name("Something else ENTIRELY")
                .description("WOW, what a thing to sell!")
                .price(45_612)
                .build()
        );
        save(
            Item
                .builder()
                .id(UUID.randomUUID().toString())
                .name("Yet Another Thing")
                .description("So plain, SO non-descriptive...")
                .price(55_612)
                .build()
        );
        save(
            Item
                .builder()
                .id(UUID.randomUUID().toString())
                .name("WTF")
                .description("Huh?")
                .price(5_612)
                .build()
        );
        save(
            Item
                .builder()
                .id(UUID.randomUUID().toString())
                .name("Such a thing")
                .description("Sheesh, what is this thing?")
                .price(15_309)
                .build()
        );
    }

    private void initItemCounts() {
        // Add the Item keys to the Map:
        items
            .keySet()
            .stream()
            .forEach((key) -> {
                itemCounts.put(key, new LongAdder());
            });

        // Add random stock levels for each Item
        itemCounts
            .keySet()
            .stream()
            .forEach((key) -> {
                int max = 255;
                IntStream
                    .range(1, new Random().nextInt(max) + 1)
                    .forEach((i) -> {
                        itemCounts.get(key).increment();
                });
            });
    }
}
