package org.sokolov.repositories;

import org.sokolov.domains.Currency;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CurrencyRepository extends CrudRepository<Currency, String> {
    Set<Currency> findAll();
}