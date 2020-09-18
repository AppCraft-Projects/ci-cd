package com.cicd.demo.product;

import com.cicd.demo.config.FeatureConfig;
import com.cicd.demo.dto.ProductDto;
import com.cicd.demo.shared.CurrencyConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.cicd.demo.shared.Currency.HUF;

@SuppressWarnings("unused")
@RestController
public class ProductController {

    private final ProductRepository productRepository;
    private final CurrencyConverter currencyConverter;
    private final FeatureConfig featureConfig;

    public ProductController(
            ProductRepository productRepository,
            CurrencyConverter currencyConverter,
            FeatureConfig featureConfig
    ) {
        this.productRepository = productRepository;
        this.currencyConverter = currencyConverter;
        this.featureConfig = featureConfig;
    }

    @GetMapping("/products")
    public Iterable<ProductDto> findProducts() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false).map(p -> new ProductDto(
                p.getId(),
                p.getName(),
                String.format("%d HUF", currencyConverter.convert(p.getCurrency(), HUF, p.getPrice())),
                p.getDescription()
        )).collect(Collectors.toList());
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (featureConfig.getDeleteProduct()) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
    }
}
