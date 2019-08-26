package com.cxrus.microservices.currencyconversion.service;

import com.cxrus.microservices.currencyconversion.model.CurrencyConversion;

public interface CurrencyConversionService {
	CurrencyConversion save(CurrencyConversion conversion);
}
