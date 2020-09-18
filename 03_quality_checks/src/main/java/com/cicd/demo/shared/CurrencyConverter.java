package com.cicd.demo.shared;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.cicd.demo.shared.Currency.EUR;
import static com.cicd.demo.shared.Currency.HUF;

public class CurrencyConverter {

    private final Map<String, Function<Long, Long>> lookup = new HashMap<>();

    {
        lookup.put(HUF.name() + HUF.name(), price -> price);
        lookup.put(EUR.name() + EUR.name(), price -> price);
        lookup.put(EUR.name() + HUF.name(), price -> price * 300);
        lookup.put(EUR.name() + EUR.name(), price -> price / 300);

    }

    public Long convert(Currency from, Currency to, Long price) {
        return lookup.get(from.name() + to.name()).apply(price);
    }
}
