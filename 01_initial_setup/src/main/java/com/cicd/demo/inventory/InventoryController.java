package com.cicd.demo.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping("/inventory/{productId}")
    public Iterable<Inventory> findInventoryForProduct(
            @PathVariable Long productId
    ) {
        return inventoryRepository.findByProductId(productId);
    }
}
