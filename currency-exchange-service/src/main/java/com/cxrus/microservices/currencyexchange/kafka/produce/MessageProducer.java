package com.cxrus.microservices.currencyexchange.kafka.produce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.cxrus.microservices.currencyexchange.model.ExchangeValue;

@Component
public class MessageProducer {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private KafkaTemplate<String, ExchangeValue> currencyExchangeKafkaTemplate;
	
	@Value(value = "${currency.exchange.topic.name}")
    private String currencyExchangeTopicName;

	
	public void sendMessage(ExchangeValue message) {

		
        ListenableFuture<SendResult<String, ExchangeValue>> future = currencyExchangeKafkaTemplate.send(currencyExchangeTopicName, message);
        
        future.addCallback(new ListenableFutureCallback<SendResult<String, ExchangeValue>>() {

            @Override
            public void onSuccess(SendResult<String, ExchangeValue> result) {
            	logger.info("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
            	logger.info("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }
}
