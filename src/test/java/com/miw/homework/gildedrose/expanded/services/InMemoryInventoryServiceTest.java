package com.miw.homework.gildedrose.expanded.services;

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

import static com.miw.homework.gildedrose.expanded.services.InMemoryInventoryService.SURGE_MINUTES;
import static com.miw.homework.gildedrose.expanded.services.InMemoryInventoryService.SURGE_VIEW_COUNT;
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
    void buy() {
        // TODO: Implementation
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

//    private void retrieveInventoryMultipleTimes(int timesToCall) {
//        IntStream
//            .rangeClosed(1, timesToCall)
//            .parallel()
//            .forEach(i -> {
//                sut.findAll();
//            })
//        ;
//    }
}