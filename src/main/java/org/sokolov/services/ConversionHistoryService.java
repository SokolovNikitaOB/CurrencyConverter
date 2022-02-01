package org.sokolov.services;

import org.sokolov.domains.Currency;
import org.sokolov.repositories.ConversionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversionHistoryService {
    @Autowired
    private ConversionHistoryRepository historyRepository;

    public double getAverageCourse(Currency inputCurrency, Currency outputCurrency){
        List<Double> coursesOfConversion = historyRepository
                .findAllByInputCurrencyAndOutputCurrency(inputCurrency, outputCurrency)
                .stream()
                .map(el -> el.getOutputValue() / el.getInputValue()).collect(Collectors.toList());

        coursesOfConversion.addAll(
                historyRepository
                        .findAllByInputCurrencyAndOutputCurrency(outputCurrency, inputCurrency)
                        .stream()
                        .map(el -> el.getInputValue() / el.getOutputValue()).collect(Collectors.toList())
        );

        return coursesOfConversion.stream().mapToDouble(el -> el).average().getAsDouble();
    }

    public List<String> getCurrencyHistory(Currency currency){
        return historyRepository.findAllByInputCurrencyOrOutputCurrency(currency, currency)
                .stream()
                .map(el -> el.getInputValue() + " " + el.getInputCurrency().getCharCode() + " = "
                        + el.getOutputValue() + " " + el.getOutputCurrency().getCharCode()
                        + " Date: " + el.getConversionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                .collect(Collectors.toList());
    }
}