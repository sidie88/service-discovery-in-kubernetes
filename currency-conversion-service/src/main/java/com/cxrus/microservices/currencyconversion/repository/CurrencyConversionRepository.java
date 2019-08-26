package com.cxrus.microservices.currencyconversion.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.cxrus.microservices.currencyconversion.model.CurrencyConversion;

public interface CurrencyConversionRepository extends ElasticsearchRepository<CurrencyConversion, String>{

}
