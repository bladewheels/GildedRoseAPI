package com.miw.homework.gildedrose.expanded.models.ordered;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.miw.homework.gildedrose.expanded.models.InventoryItem;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;

/**
 * This class represents a response to an API user who has tried to buy an {@link InventoryItem}
 * but the quantity they requested exceeded the available stock in inventory.
 */
@Value
@Builder
public class UnderStockedItem implements Serializable, OrderedItem {
    private static final long serialVersionUID = 1L;
    // TODO: REVIEW: Introduce 18n messaging here and elsewhere...
    private static final String UNDER_STOCKED_WARNING = "Sorry, we don't have enough of this Item in stock at the moment - please try again later or purchase less Items.";

    String id;
    InventoryItem itemFromInventory;
    int quantity;
    int inStock;
    final String notice = UNDER_STOCKED_WARNING;

    @JsonCreator
    public UnderStockedItem(@JsonProperty("id") final String id,
                         @JsonProperty("itemFromInventory") final InventoryItem itemFromInventory,
                         @JsonProperty("quantity") final int quantity,
                         @JsonProperty("inStock") final int inStock) {
        this.id = requireNonNull(id);
        this.itemFromInventory = requireNonNull(itemFromInventory);
        this.quantity = requireNonNull(quantity);
        this.inStock = requireNonNull(inStock);
    }
}
