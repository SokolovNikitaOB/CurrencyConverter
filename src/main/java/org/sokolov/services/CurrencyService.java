package org.sokolov.services;

import org.sokolov.domains.Currency;
import org.sokolov.microservices.CurrencyProxy;
import org.sokolov.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class CurrencyService {
    @Autowired
    private CurrencyProxy currencyProxy;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CreationPOJOService pojoService;

    public void addToDatabaseActualData(){
        currencyRepository.saveAll(
                currencyProxy.getValCurs().getValutes()
                        .stream()
                        .map(valute -> pojoService.createCurrency(valute))
                        .sorted(Comparator.comparing(Currency::getName))
                        .collect(Collectors.toList())
        );
    }
}
