package com.cicd.demo;

import com.cicd.demo.inventory.Inventory;
import com.cicd.demo.inventory.InventoryRepository;
import com.cicd.demo.product.Product;
import com.cicd.demo.product.ProductRepository;
import com.cicd.demo.shared.user.User;
import com.cicd.demo.shared.user.UserRepository;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class BeanConfiguration {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final InventoryRepository inventoryRepository;

    public BeanConfiguration(
            ProductRepository productRepository,
            UserRepository userRepository,
            InventoryRepository inventoryRepository
    ) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @PostConstruct
    public void createStubs() {
        Product fcond = new Product(
                null,
                "Fuel Conditioner",
                "Improves the quality of diesel fuel."
        );
        Product paracord = new Product(
                null,
                "Paracord",
                "A lightweight polymeric cord made of nylon, originally used in parachute slings."
        );
        Product cofdm = new Product(
                null,
                "COFDM wireless Signal Transmitter",
                "Two-way Speaking Military COFDM wireless AV+data Signal Transmitter SG-C10"
        );

        productRepository.save(fcond);
        productRepository.save(paracord);
        productRepository.save(cofdm);

        userRepository.save(new User(null, "Skier"));
        userRepository.save(new User(null, "Ragman"));

        inventoryRepository.save(new Inventory(
                null, fcond, 648L
        ));
        inventoryRepository.save(new Inventory(
                null, paracord, 362L
        ));
        inventoryRepository.save(new Inventory(
                null, cofdm, 18L
        ));
    }
}
