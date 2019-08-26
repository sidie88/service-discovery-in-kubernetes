package com.cxrus.microservices.currencyconversion.kafka.consume;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cxrus.microservices.currencyconversion.model.CurrencyConversion;
import com.cxrus.microservices.currencyconversion.model.ExchangeValue;
import com.cxrus.microservices.currencyconversion.service.CurrencyConversionService;

@Component
public class MessageListener {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CurrencyConversionService service;
	
	@KafkaListener(topics = "${currency.exchange.topic.name}", containerFactory = "requestObjetListenerContainerFactory")
	public void requestObjectListener(ExchangeValue response) {
		logger.info("Received Messasge : " + response);
		BigDecimal qty = new BigDecimal(response.getQuantity());
		CurrencyConversion result = new CurrencyConversion(response.getFrom(), response.getTo(), response.getConversionMultiple(), 
				qty, qty.multiply(response.getConversionMultiple()), response.getPort(), response.getIpAddress());
		
		service.save(result);
	}
	
}
