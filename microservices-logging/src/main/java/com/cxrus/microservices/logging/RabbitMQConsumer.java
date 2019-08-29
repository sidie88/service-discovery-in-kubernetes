package com.cxrus.microservices.logging;

import java.nio.charset.StandardCharsets;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {
	
	
	@RabbitListener(queues = "${logging.rabbitmq.queue}")
	public void recievedMessage(Message message) {
		byte[] body = message.getBody();
		String log = new String(body, StandardCharsets.UTF_8);
		System.out.println("Logging from RabbitMQ : " + log);
	}
}
