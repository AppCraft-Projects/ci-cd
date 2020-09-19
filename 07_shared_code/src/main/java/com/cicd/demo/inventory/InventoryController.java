package com.cicd.demo.inventory;

import com.cicd.demo.dto.InventoryDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @GetMapping("/inventory/{productId}")
    public Iterable<InventoryDto> findInventoryForProduct(
            @PathVariable Long productId
    ) {
        return inventoryRepository.findByProductId(productId).stream().map(i -> new InventoryDto(
                i.getId(),
                i.getCount()
        )).collect(Collectors.toList());
    }
}
