package org.sokolov.services;

import org.sokolov.domains.Currency;
import org.sokolov.microservices.CurrencyProxy;
import org.sokolov.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
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

    public Set<Currency> getAllCurrencies(){
        return currencyRepository.findAll();
    }

    public Currency getActualCurrency(String id){
        Currency currency = currencyRepository.findById(id).get();
        if(LocalDateTime.now().getDayOfMonth() != currency.getDate().getDayOfMonth()){
            addToDatabaseActualData();
            return currencyRepository.findById(currency.getId()).get();
        }
        return currency;
    }

    public Double convertCurrency(Double inputNumber, Currency inputCurrency, Currency outputCurrency){
        return (inputCurrency.getValue() / inputCurrency.getNominal()) / (outputCurrency.getValue() / outputCurrency.getNominal()) * inputNumber;
    }
}
