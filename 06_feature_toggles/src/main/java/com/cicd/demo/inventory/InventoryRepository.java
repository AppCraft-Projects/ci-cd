package com.cicd.demo.inventory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Long> {

    List<Inventory> findByProductId(Long productId);
}
