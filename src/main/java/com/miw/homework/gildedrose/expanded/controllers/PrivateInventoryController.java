package com.miw.homework.gildedrose.expanded.controllers;

import com.miw.homework.gildedrose.expanded.models.InventoriedItem;
import com.miw.homework.gildedrose.expanded.services.InventoryService;
import com.miw.homework.gildedrose.expanded.security.user.User;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/private/inventory")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PrivateInventoryController {

//  @NonNull
//  AuthenticationService authentication;

  @NonNull
  InventoryService itemsSrvc;

  @GetMapping("/list")
  List<InventoriedItem> list(@AuthenticationPrincipal final User user) {
    return itemsSrvc.findAll();
//    return Item
//            .builder()
//            .id(UUID.randomUUID().toString())
//            .name("Thing 0x001")
//            .description("A reeallly nice thing!")
//            .price(35_612)
//            .build();
  }

}
