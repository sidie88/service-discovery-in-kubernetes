package com.cxrus.microservices.currencyconversion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxrus.microservices.currencyconversion.model.CurrencyConversion;
import com.cxrus.microservices.currencyconversion.repository.CurrencyConversionRepository;

@Service
public class CurrencyConversionServiceImpl implements CurrencyConversionService{

	@Autowired
	private CurrencyConversionRepository repo;
	
	@Override
	public CurrencyConversion save(CurrencyConversion conversion) {
		return repo.save(conversion);
	}

}
