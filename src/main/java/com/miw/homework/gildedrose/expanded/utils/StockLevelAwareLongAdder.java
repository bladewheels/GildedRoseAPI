package com.miw.homework.gildedrose.expanded.utils;

import lombok.Value;

import java.io.Serializable;
import java.util.concurrent.atomic.LongAdder;

/**
 * A wrapper around {@LongAdder} that doesn't allow inventory stock levels to be dropped below zero.
 */
@Value
public class StockLevelAwareLongAdder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The following variable is named somewhat cryptically
     * so that customer-facing JSON includes:
     * e.g. "stockLevel":{"current":34}
     */
    LongAdder current = new LongAdder();

    public synchronized void add(long x) {
        int stockLevel = intValue();
        if ((stockLevel + x) < 0) {
            throw new IllegalArgumentException(String.format("Adding x=%s to inventory would result in a negative stock level, sorry!", x));
        }
        current.add(x);
    }

    public synchronized void subtract(long x) {
        int currentStockLevel = intValue();
        if ((currentStockLevel - x) < 0) {
            throw new IllegalArgumentException(String.format("Subtracting x=%s from inventory would result in a negative stock level, sorry!", x));
        }
        current.add(-1 * x);
    }

    public int intValue() {
        return current.intValue();
    }
}
