package com.miw.homework.gildedrose.expanded.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import static java.util.Objects.requireNonNull;

@Value
@Builder
public class InventoriedItem {
    private static final long serialVersionUID = 1_111_222_666_777_888_999L;

    Item item;
    Long stockLevel;

    @JsonCreator
    public InventoriedItem(@JsonProperty("item") final Item item,
                           @JsonProperty("stockLevel") final Long stockLevel) {
        this.item = requireNonNull(item);
        this.stockLevel = requireNonNull(stockLevel);
    }
//
//    public String getName() {
//        return item.getName();
//    }
}
