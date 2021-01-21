package com.miw.homework.gildedrose.expanded.services;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static com.miw.homework.gildedrose.expanded.services.InMemoryInventoryService.SURGE_MINUTES;
import static com.miw.homework.gildedrose.expanded.services.InMemoryInventoryService.SURGE_VIEW_COUNT;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryInventoryServiceTest {

    InMemoryInventoryService sut;

    @BeforeEach
    void init() {
        sut = new InMemoryInventoryService();
        sut.init();
    }

    private void listInventory(int timesToCall) {
        IntStream
            .rangeClosed(1, timesToCall)
            .parallel()
            .forEach(i -> {
                sut.findAll();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    // no-op
                }
            });
    }

    @Test
    void buy() {
    }

    @Test
    void findNumberOfViewsLastMinutes() {
        int expected = 20;
        listInventory(expected);
        int actual = sut.findNumberOfViewsLastMinutes(SURGE_MINUTES);
        assertEquals(expected, actual, expected + " Dates should have been immediately added.");
    }

    @Test
    void getRegularPrice() {
        int listPrice = 20;
        int expected = 20;
        listInventory(SURGE_VIEW_COUNT);
        int actual = sut.getSurgeOrRegularPrice(listPrice);
        assertEquals(expected, actual, expected + "List price should have been returned.");
    }

    @Test
    void getSurgePrice() {
        int listPrice = 20;
        int expected = 22;
        listInventory(SURGE_VIEW_COUNT + 1);
        int actual = sut.getSurgeOrRegularPrice(listPrice);
        assertEquals(expected, actual, expected + "Surge price should have been returned.");
    }
}