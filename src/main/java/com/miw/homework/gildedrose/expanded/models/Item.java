package com.miw.homework.gildedrose.expanded.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static java.util.Objects.requireNonNull;

/**
 * This class represents an item that can be considered for inclusion in inventory,
 * i.e. when included as part of an {@link InventoryItem}
 */
@Value
@NonFinal
@Builder
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    // The property is not included in serialization
    // i.e. so that API users will only see/use the ID
    // of an (enclosing) InventoryItem when choosing what to buy
    @JsonProperty(access = WRITE_ONLY)
    int id;

    String name;
    String description;
    int price;

    @JsonCreator
    public Item(@JsonProperty("id") final int id,
                @JsonProperty("name") final String name,
                @JsonProperty("description") final String description,
                @JsonProperty("price") final int price) {
        this.id = requireNonNull(id);
        this.name = requireNonNull(name);
        this.description = requireNonNull(description);
        this.price = requireNonNull(price);
    }
}
