package org.sokolov.services;

import org.sokolov.domains.ConversionHistory;
import org.sokolov.domains.Currency;
import org.sokolov.microservices.xmlclasses.Valute;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreationPOJOService {

    public Currency createCurrency(Valute valute){
        LocalDateTime now = LocalDateTime.now();
        Currency currency = new Currency();
        currency.setId(valute.getID());
        currency.setNumCode(valute.getNumCode());
        currency.setCharCode(valute.getCharCode());
        currency.setNominal(valute.getNominal());
        currency.setName(valute.getName());
        currency.setValue(Double.parseDouble(valute.getValue().replace(",", ".")));
        currency.setDate(now);
        return currency;
    }

    public ConversionHistory createConversionHistory(Double inputNumber, Double outputNumber,
                                                     Currency inputCurrency, Currency outputCurrency){
        ConversionHistory conversionHistory = new ConversionHistory();
        conversionHistory.setConversionDate(LocalDateTime.now());
        conversionHistory.setInputCurrency(inputCurrency);
        conversionHistory.setOutputCurrency(outputCurrency);
        conversionHistory.setInputValue(inputNumber);
        conversionHistory.setOutputValue(outputNumber);
        return conversionHistory;
    }
}
