package com.cicd.demo.config;

import com.cicd.demo.inventory.Inventory;
import com.cicd.demo.inventory.InventoryRepository;
import com.cicd.demo.product.Currency;
import com.cicd.demo.product.Product;
import com.cicd.demo.product.ProductRepository;
import com.cicd.demo.shared.CurrencyConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Configuration
public class BeanConfiguration {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    public BeanConfiguration(
            ProductRepository productRepository,
            InventoryRepository inventoryRepository
    ) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Bean
    public CurrencyConverter currencyConverter() {
        return new CurrencyConverter();
    }

    @PostConstruct
    public void createStubs() {
        Product fcond = new Product(
                null,
                "Fuel Conditioner",
                40000L,
                Currency.HUF,
                "Improves the quality of diesel fuel.",
                Collections.emptyList()
        );
        Product paracord = new Product(
                null,
                "Paracord",
                85000L,
                Currency.HUF,
                "A lightweight polymeric cord made of nylon, originally used in parachute slings.",
                Collections.emptyList()
        );
        Product cofdm = new Product(
                null,
                "COFDM wireless Signal Transmitter",
                98000L,
                Currency.HUF,
                "Two-way Speaking Military COFDM wireless AV+data Signal Transmitter SG-C10",
                Collections.emptyList()
        );

        productRepository.save(fcond);
        productRepository.save(paracord);
        productRepository.save(cofdm);

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
