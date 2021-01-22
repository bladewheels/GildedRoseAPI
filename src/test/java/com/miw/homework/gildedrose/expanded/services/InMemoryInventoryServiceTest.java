package com.miw.homework.gildedrose.expanded.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static com.miw.homework.gildedrose.expanded.services.InMemoryInventoryService.SURGE_MINUTES;
import static com.miw.homework.gildedrose.expanded.services.InMemoryInventoryService.SURGE_VIEW_COUNT;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryInventoryServiceTest {

    InMemoryInventoryStorage db = new InMemoryInventoryStorage();
    InMemoryInventoryService sut;

    @BeforeEach
    void init() {
        sut = new InMemoryInventoryService(db);
        sut.init();
    }

    @Test
    void buy() {
    }

    @Test
    void findNumberOfViewsLastMinutes() {
        int expected = 20;
        listInventoryMultipleTimes(expected);
        int actual = sut.findNumberOfViewsLastMinutes(SURGE_MINUTES);
        assertEquals(expected, actual, expected + " Dates should have been immediately added.");
    }

    @Test
    void getRegularPrice() {
        int listPrice = 20;
        int expected = 20;
        listInventoryMultipleTimes(SURGE_VIEW_COUNT);
        int actual = sut.getSurgeOrRegularPrice(listPrice);
        assertEquals(expected, actual, expected + "List price should have been returned.");
    }

    @Test
    void getSurgePrice() {
        int listPrice = 20;
        int expected = 22;
        listInventoryMultipleTimes(SURGE_VIEW_COUNT + 1);
        int actual = sut.getSurgeOrRegularPrice(listPrice);
        assertEquals(expected, actual, expected + "Surge price should have been returned.");
    }

    private void listInventoryMultipleTimes(int timesToCall) {
        IntStream
                .rangeClosed(1, timesToCall)
                .parallel()
                .forEach(i -> {
                    sut.findAll();
                    // The following should become unnecessary once I complete the data layer mocking
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        // no-op
                    }
                });
    }
}