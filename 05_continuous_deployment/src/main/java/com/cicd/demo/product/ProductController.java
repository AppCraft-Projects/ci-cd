package com.cicd.demo.product;

import com.cicd.demo.dto.ProductDto;
import com.cicd.demo.shared.CurrencyConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.cicd.demo.shared.Currency.HUF;

@SuppressWarnings("unused")
@RestController
public class ProductController {

    private final ProductRepository productRepository;
    private final CurrencyConverter currencyConverter;

    public ProductController(
            ProductRepository productRepository,
            CurrencyConverter currencyConverter
    ) {
        this.productRepository = productRepository;
        this.currencyConverter = currencyConverter;
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
}
