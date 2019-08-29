package com.cxrus.microservices.currencyexchange.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cxrus.microservices.currencyexchange.model.ExchangeValue;
import com.cxrus.microservices.currencyexchange.repository.ExchangeValueRepository;

@RestController
public class CurrencyExchangeController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ExchangeValueRepository repository;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		ExchangeValue exchangeValue = 
				repository.findByFromAndTo(from, to);
		
		exchangeValue.setPort(
				Integer.parseInt(environment.getProperty("CURRENCY_EXCHANGE_SERVICE_PORT_80_TCP_PORT")));
		exchangeValue.setIpAddress(environment.getProperty("CURRENCY_EXCHANGE_SERVICE_PORT_80_TCP_ADDR"));
		logger.info("Pod name = " + environment.getProperty("HOSTNAME"));
		logger.info("{}", exchangeValue);
		
		return exchangeValue;
	}
	
	@GetMapping("/hello")
	public String hello() {
		return "HELLO . . .";
	}
}
