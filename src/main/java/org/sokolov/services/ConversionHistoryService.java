package org.sokolov.services;

import org.sokolov.domains.ConversionHistory;
import org.sokolov.domains.Currency;
import org.sokolov.repositories.ConversionHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversionHistoryService {
    private final ConversionHistoryRepository historyRepository;

    public ConversionHistoryService(ConversionHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    /**
     * At the start, this method get list of all exchange rate between input and output currencies.
     * Further, to this list is added the same list but input and output currencies change places.
     * Resulting list is used to calculate average exchange rate.
     * @return average exchange rate between input and output currencies
     */
    public double getAverageRate(Currency inputCurrency, Currency outputCurrency){
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

        return coursesOfConversion.stream().mapToDouble(el -> el).average().orElseThrow();
    }

    /**
     * This method get history list of conversion of certain currency when it was initial or final currency
     * and map these list to informative string list.
     */
    public List<String> getCurrencyHistory(Currency currency){
        return historyRepository.findAllByInputCurrencyOrOutputCurrency(currency, currency)
                .stream()
                .map(el -> el.getInputValue() + " " + el.getInputCurrency().getCharCode() + " = "
                        + el.getOutputValue() + " " + el.getOutputCurrency().getCharCode()
                        + " Date: " + el.getConversionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                .collect(Collectors.toList());
    }

    public ConversionHistory getHistoryById(Long id){
        return historyRepository.findById(id).orElseThrow();
    }
}
