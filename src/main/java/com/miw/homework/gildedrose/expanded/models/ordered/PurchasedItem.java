package com.miw.homework.gildedrose.expanded.models.ordered;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.miw.homework.gildedrose.expanded.models.InventoryItem;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;

/**
 * This class represents a response to an API user who has successfully bought an {@link InventoryItem}.
 */
@Value
@Builder
public class PurchasedItem implements Serializable, OrderedItem {
    private static final long serialVersionUID = 1L;
    private static final String SURGE_PRICING_WARNING = "Surge pricing may have inflated the list price of this Item.";

    String id;
    InventoryItem itemFromInventory;
    int quantity;
    double priceEach;
    double totalCharged;
    final String notice = SURGE_PRICING_WARNING;

    @JsonCreator
    public PurchasedItem(@JsonProperty("id") final String id,
                         @JsonProperty("itemFromInventory") final InventoryItem itemFromInventory,
                         @JsonProperty("quantity") final int quantity,
                         @JsonProperty("priceEach") final double priceEach,
                         @JsonProperty("totalCharged") final double totalCharged) {
        this.id = requireNonNull(id);
        this.itemFromInventory = requireNonNull(itemFromInventory);
        this.quantity = requireNonNull(quantity);
        this.priceEach = requireNonNull(priceEach);
        this.totalCharged = requireNonNull(totalCharged);
    }
}
