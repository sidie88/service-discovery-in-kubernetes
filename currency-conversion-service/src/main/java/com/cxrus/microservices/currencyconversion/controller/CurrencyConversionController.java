package com.cxrus.microservices.currencyconversion.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cxrus.microservices.currencyconversion.CurrencyExchangeServiceProxy;
import com.cxrus.microservices.currencyconversion.kafka.produce.MessageProducer;
import com.cxrus.microservices.currencyconversion.model.CurrencyConversion;
import com.cxrus.microservices.currencyconversion.model.RequestObject;

@RestController
public class CurrencyConversionController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	
	@Autowired
	private MessageProducer producer;
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion convertCurrencyFeign(@PathVariable String from, 
			@PathVariable String to, @PathVariable BigDecimal quantity ) {
		
		CurrencyConversion response = proxy.retrieveExchangeValue(from, to);
		
		CurrencyConversion result = new CurrencyConversion(from, to, response.getConversionMultiple(), 
				quantity, quantity.multiply(response.getConversionMultiple()), response.getPort(), response.getIpAddress());
		logger.info("{}", result);
		return result;
	}
	
	@GetMapping("/currency-converter-kafka/from/{from}/to/{to}/quantity/{quantity}")
	public ResponseEntity<String> convertCurrencyKafka(@PathVariable String from, 
			@PathVariable String to, @PathVariable Integer quantity ) {
		RequestObject requestObject = new RequestObject(from, to, quantity);
		producer.sendRequestObjectMessage(requestObject );
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
