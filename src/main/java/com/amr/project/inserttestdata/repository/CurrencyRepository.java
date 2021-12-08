package com.amr.project.inserttestdata.repository;

import com.amr.project.model.entity.Currency;
import org.springframework.data.repository.CrudRepository;

/**
 * User: Hajimurad Suleymanov
 * Date: 10.11.2021
 */

public interface CurrencyRepository extends CrudRepository<Currency, Integer> {
}
