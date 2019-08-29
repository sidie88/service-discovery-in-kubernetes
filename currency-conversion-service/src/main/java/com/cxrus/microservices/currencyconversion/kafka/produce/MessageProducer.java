package com.cxrus.microservices.currencyconversion.kafka.produce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.cxrus.microservices.currencyconversion.model.RequestObject;

@Component
public class MessageProducer {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private KafkaTemplate<String, RequestObject> requestObjectTemplate;

	@Value(value = "${currency.conversion.topic.name}")
	private String currencyConversionTopicName;
	
	
	public void sendRequestObjectMessage(RequestObject requestObject) {
		ListenableFuture<SendResult<String, RequestObject>> future = requestObjectTemplate.send(currencyConversionTopicName, requestObject);
		future.addCallback(new ListenableFutureCallback<SendResult<String, RequestObject>>() {

            @Override
            public void onSuccess(SendResult<String, RequestObject> result) {
            	logger.info("Sent message=[" + requestObject + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
            	logger.info("Unable to send message=[" + requestObject + "] due to : " + ex.getMessage());
            }
        });
	}
}
