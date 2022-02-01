package org.sokolov.repositories;

import org.sokolov.domains.ConversionHistory;
import org.sokolov.domains.Currency;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConversionHistoryRepository extends CrudRepository<ConversionHistory, Long> {
    List<ConversionHistory> findAllByInputCurrencyAndOutputCurrency(Currency inputCurrency, Currency outputCurrency);
    List<ConversionHistory> findAllByInputCurrencyOrOutputCurrency(Currency inputCurrency, Currency outputCurrency);
}

