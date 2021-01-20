package com.miw.homework.gildedrose.expanded.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import static java.util.Objects.requireNonNull;

@Value
@Builder
public class Item {
    private static final long serialVersionUID = 2_222_333_777_000_222_111L;

    String id;
    String name;
    String description;
    int price;

    @JsonCreator
    Item(@JsonProperty("id") final String id,
         @JsonProperty("name") final String name,
         @JsonProperty("description") final String description,
         @JsonProperty("price") final int price) {
        this.id = requireNonNull(id);
        this.name = requireNonNull(name);
        this.description = requireNonNull(description);
        this.price = requireNonNull(price);
    }
}
