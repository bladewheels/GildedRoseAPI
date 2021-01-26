package com.miw.homework.gildedrose.expanded.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.miw.homework.gildedrose.expanded.utils.StockLevelAwareLongAdder;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;

/**
 * This class represents an {@link Item} that is in inventory,
 * i.e. potentially available for sale when in-stock.
 */
@Value
@NonFinal
@Builder
public class InventoryItem implements Serializable {
    private static final long serialVersionUID = 1L;

    int id;
    Item item;
    StockLevelAwareLongAdder stockLevel;

    @JsonCreator
    public InventoryItem(@JsonProperty("id") final int id,
                         @JsonProperty("item") final Item item,
                         @JsonProperty("stockLevel") final StockLevelAwareLongAdder stockLevel) {
        this.id = requireNonNull(id);
        this.item = requireNonNull(item);
        this.stockLevel = requireNonNull(stockLevel);
    }
}
