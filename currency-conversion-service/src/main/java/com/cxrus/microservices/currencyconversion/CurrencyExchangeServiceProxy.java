package com.cxrus.microservices.currencyconversion;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cxrus.microservices.currencyconversion.model.CurrencyConversion;

@FeignClient(name="spring-api-gateway-services", url="${api.gateway.server.url}")
public interface CurrencyExchangeServiceProxy {
	
	@GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion retrieveExchangeValue
		(@PathVariable("from") String from, @PathVariable("to") String to);
	
}
