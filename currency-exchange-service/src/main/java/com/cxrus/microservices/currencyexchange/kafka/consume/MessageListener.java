package com.cxrus.microservices.currencyexchange.kafka.consume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cxrus.microservices.currencyexchange.kafka.produce.MessageProducer;
import com.cxrus.microservices.currencyexchange.model.ExchangeValue;
import com.cxrus.microservices.currencyexchange.model.RequestObject;
import com.cxrus.microservices.currencyexchange.repository.ExchangeValueRepository;

@Component
public class MessageListener {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private MessageProducer producer;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ExchangeValueRepository repository;
	
	@KafkaListener(topics = "${currency.conversion.topic.name}", groupId = "${currency.conversion.group.id}", containerFactory = "requestObjetListenerContainerFactory")
	public void requestObjectListener(RequestObject message) {
		logger.info("Received Messasge : " + message);
		ExchangeValue exchangeValue = 
				repository.findByFromAndTo(message.getFrom(), message.getTo());
		if (exchangeValue != null) {
			exchangeValue.setQuantity(message.getQuantity());
//			exchangeValue
//					.setPort(Integer.parseInt(environment.getProperty("CURRENCY_EXCHANGE_SERVICE_PORT_80_TCP_PORT")));
//			exchangeValue.setIpAddress(environment.getProperty("CURRENCY_EXCHANGE_SERVICE_PORT_80_TCP_ADDR"));
			logger.info("Pod name = " + environment.getProperty("HOSTNAME"));
			logger.info("{}", exchangeValue);
			producer.sendMessage(exchangeValue);
		}
	}
	
}
