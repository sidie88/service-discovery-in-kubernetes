package com.cxrus.microservices.currencyexchange.kafka.consume;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.cxrus.microservices.currencyexchange.model.RequestObject;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;
	
	@Bean
	public ConsumerFactory<String, RequestObject> requestObjetFactory(){
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return new DefaultKafkaConsumerFactory<>(
			      props,
			      new StringDeserializer(), 
			      new JsonDeserializer<>(RequestObject.class));
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, RequestObject> requestObjetListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, RequestObject> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(requestObjetFactory());
		return factory;
	}
}
