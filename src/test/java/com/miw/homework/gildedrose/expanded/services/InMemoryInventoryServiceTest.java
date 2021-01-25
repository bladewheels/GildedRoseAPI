package com.miw.homework.gildedrose.expanded.services;

import com.miw.homework.gildedrose.expanded.models.InventoryItem;
import com.miw.homework.gildedrose.expanded.models.Item;
import com.miw.homework.gildedrose.expanded.models.ordered.DiscontinuedItem;
import com.miw.homework.gildedrose.expanded.models.ordered.PurchasedItem;
import com.miw.homework.gildedrose.expanded.models.ordered.UnderStockedItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.miw.homework.gildedrose.expanded.models.ordered.OrderedItem.INVALID_ORDER_ID;
import static com.miw.homework.gildedrose.expanded.services.InMemoryInventoryService.SURGE_MINUTES;
import static com.miw.homework.gildedrose.expanded.services.InMemoryInventoryService.SURGE_VIEW_COUNT;
import static com.miw.homework.gildedrose.expanded.services.InventoryService.OUT_OF_STOCK_INVENTORY_ID__FOR_DEMO_PURPOSES_ONLY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class InMemoryInventoryServiceTest {

    InMemoryInventoryStorage db = Mockito.mock(InMemoryInventoryStorage.class);
    InMemoryInventoryService sut; // i.e. System Under Test
    LocalDateTime now ;

    @BeforeEach
    void init() {
        sut = new InMemoryInventoryService(db);
        sut.init();
        now = LocalDateTime.now();
    }

    @Test
    void buyOutOfStockItem() {
        int price = 333;
        int quantity = 7;
        Item item = sut.createItem(66, "Foo", "Bar, bar none.", price);
        InventoryItem inventoryItem = sut.createInventoryItem(OUT_OF_STOCK_INVENTORY_ID__FOR_DEMO_PURPOSES_ONLY, item);

        UnderStockedItem expected = new UnderStockedItem(INVALID_ORDER_ID, inventoryItem, quantity, 0);
        when(db.getInventoryItemById(OUT_OF_STOCK_INVENTORY_ID__FOR_DEMO_PURPOSES_ONLY)).thenReturn(inventoryItem);
        UnderStockedItem actual = (UnderStockedItem) sut.buy(quantity, OUT_OF_STOCK_INVENTORY_ID__FOR_DEMO_PURPOSES_ONLY);

        assertEquals(expected, actual,  "It should be equal.");
    }

    @Test
    void buyTooManyItems() {
        int price = 333;
        int quantity = 777_777;
        Item item = sut.createItem(66, "Foo", "Bar, bar none.", price);
        InventoryItem inventoryItem = sut.createInventoryItem(3, item);

        UnderStockedItem expected = new UnderStockedItem(INVALID_ORDER_ID, inventoryItem, quantity, 0);
        when(db.getInventoryItemById(3)).thenReturn(inventoryItem);
        UnderStockedItem actual = (UnderStockedItem) sut.buy(quantity, 3);

        assertEquals(expected, actual,  "It should be equal.");
    }

    @Test
    void buyNeverStockedItem() {
        int id = 333_333;
        int price = 333;
        int quantity = 666;
        Item item = sut.createItem(66, "Foo", "Bar, bar none.", price);
        InventoryItem inventoryItem = sut.createInventoryItem(id, item);

        DiscontinuedItem expected = new DiscontinuedItem();
        when(db.getInventoryItemById(3)).thenReturn(inventoryItem);
        DiscontinuedItem actual = (DiscontinuedItem) sut.buy(quantity, id);

        assertEquals(expected, actual,  "It should be equal.");
    }

    @Test
    void buy_1_ItemAtListPrice() {
        buyItemAndAssertEquals(666, 1234, 1);
    }

    @Test
    void buy_10_ItemsAtListPrice() {
        buyItemAndAssertEquals(2, 1234, 10);
    }

    @Test
    void find_1_View() {
        int expected = 1;
        when(db.getViews()).thenReturn(mockInventoryViews(expected, 0, now));
        int actual = sut.findNumberOfViews(SURGE_MINUTES, now);
        assertEquals(expected, actual, expected + " Dates should have been immediately added.");
    }

    @Test
    void find_5_Views() {
        int expected = 5;
        when(db.getViews()).thenReturn(mockInventoryViews(expected, 15, now));
        int actual = sut.findNumberOfViews(SURGE_MINUTES, now);
        assertEquals(expected, actual, expected + " Dates should have been retrieved.");
    }

    @Test
    void find_100_Views() {
        int expected = 100;
        when(db.getViews()).thenReturn(mockInventoryViews(expected, 15, now));
        int actual = sut.findNumberOfViews(SURGE_MINUTES, now);
        assertEquals(expected, actual, expected + " Dates should have been retrieved.");
    }

    @Test
    void getRegularPrice() {
        int listPrice = 20;
        when(db.getViews()).thenReturn(mockInventoryViews(SURGE_VIEW_COUNT, 5, now));
        int expected = 20;
        int actual = sut.getSurgeOrListPrice(listPrice, now);
        assertEquals(expected, actual, expected + "List price should have been returned.");
    }

    @Test
    void getSurgePrice() {
        int listPrice = 20;
        when(db.getViews()).thenReturn(mockInventoryViews(SURGE_VIEW_COUNT + 1, 3, now));
        int expected = 22;
        int actual = sut.getSurgeOrListPrice(listPrice, now);
        assertEquals(expected, actual, expected + "Surge price should have been returned.");
    }

    private void buyItemAndAssertEquals(int id, int price, int quantity) {
        // TODO: Move the construction of these types from the Service layer to the DB layer:
        Item item = sut.createItem(66, "Foo", "Bar, bar none.", price);
        InventoryItem inventoryItem = sut.createInventoryItem(id, item);

        PurchasedItem expected = new PurchasedItem("Baz", inventoryItem, quantity, price, quantity * price);
        when(db.getInventoryItemById(id)).thenReturn(inventoryItem);
        PurchasedItem actual = (PurchasedItem) sut.buy(quantity, id);

        ignoreOrderId(expected, actual);
        assertEquals(expected, actual);
    }

    /**
     * Workaround for unequal OrderIds in tests,
     * simply copies the service-generated OrderId
     * from the actual PurchasedItem to the expected PurchasedItem.
     *
     * @param expected the expected PurchasedItem
     * @param actual the actual PurchasedItem
     */
    private void ignoreOrderId(PurchasedItem expected, PurchasedItem actual) {
        expected.setOrderId(actual.getOrderId());
    }

    /**
     * Mock the representation of a number of views of the inventory over a period of time.
     *
     * @param views total number of views since now
     * @param minutesBetweenViews number of minutes between views
     * @param now date/time to start from
     * @return A {@List} of LocalDateTimes that correspond to Inventory views
     */
    private List<LocalDateTime> mockInventoryViews(int views, int minutesBetweenViews, LocalDateTime now) {
        return (1 == views)
                ? Arrays.asList(now)
                : IntStream.rangeClosed(0, views - 1).mapToObj(i -> { return now.minusMinutes(i); }).collect(Collectors.toList())
        ;
    }
}