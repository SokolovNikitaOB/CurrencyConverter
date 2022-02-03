package org.sokolov.services;

import org.sokolov.domains.Currency;
import org.sokolov.microservices.CurrencyProxy;
import org.sokolov.repositories.ConversionHistoryRepository;
import org.sokolov.repositories.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CurrencyService {
    private final CurrencyProxy currencyProxy;

    private final CurrencyRepository currencyRepository;

    private final ConversionHistoryRepository historyRepository;

    private final CreationPOJOService pojoService;

    public CurrencyService(CurrencyProxy currencyProxy,
                           CurrencyRepository currencyRepository,
                           ConversionHistoryRepository historyRepository,
                           CreationPOJOService pojoService) {
        this.currencyProxy = currencyProxy;
        this.currencyRepository = currencyRepository;
        this.historyRepository = historyRepository;
        this.pojoService = pojoService;
    }

    public void addToDatabaseActualData(){
        currencyRepository.saveAll(
                currencyProxy
                        .getValCurs()
                        .getValutes()
                        .stream()
                        .map(pojoService::createCurrency)
                        .sorted(Comparator.comparing(Currency::getName))
                        .collect(Collectors.toList())
        );
    }

    public Set<Currency> getAllCurrencies(){
        return currencyRepository.findAll();
    }

    public Currency getActualCurrency(String id){
        Currency currency = currencyRepository.findById(id).orElseThrow();
        if(LocalDateTime.now().getDayOfMonth() != currency.getDate().getDayOfMonth()){
            addToDatabaseActualData();
            return currencyRepository.findById(currency.getId()).orElseThrow();
        }
        return currency;
    }

    public Long convertCurrency(Double inputNumber, Currency inputCurrency, Currency outputCurrency){
        Double outputNumber =  (inputCurrency.getValue() / inputCurrency.getNominal()) / (outputCurrency.getValue() / outputCurrency.getNominal()) * inputNumber;
        return historyRepository.save(pojoService.createConversionHistory(inputNumber,outputNumber,inputCurrency,outputCurrency)).getId();
    }
}
