package com.cxrus.microservices.currencyconversion.kafka.consume;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.http.util.ExceptionUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cxrus.microservices.currencyconversion.model.CurrencyConversion;
import com.cxrus.microservices.currencyconversion.model.ExchangeValue;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MessageListener {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value(value = "${elasticsearch.url}")
	private String elasticsearchUrl;

	@Value(value = "${elasticsearch.index.name}")
	private String elasticsearchIndexName;

	@Value(value = "${currency.conversion.topic.name}")
	private String elasticsearchIndexType;

	@Value(value = "${elasticsearch.port}")
	private int elasticsearchPort;
	
	@KafkaListener(topics = "${currency.exchange.topic.name}", groupId = "${currency.exchange.group.id}", containerFactory = "requestObjetListenerContainerFactory")
	public void requestObjectListener(ExchangeValue response) {
		logger.info("Received Messasge : " + response);
		BigDecimal qty = new BigDecimal(response.getQuantity());
		CurrencyConversion result = new CurrencyConversion(response.getFrom(), response.getTo(), response.getConversionMultiple(), 
				qty, qty.multiply(response.getConversionMultiple()), response.getPort(), response.getIpAddress());
		putIndexToElasticsearch(result);
	}

	private void putIndexToElasticsearch(CurrencyConversion result) {
		Client client = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			Settings settings = Settings.builder().put("client.transport.sniff", true)
			                    .put("cluster.name","dxp-cluster").build();
			client = new PreBuiltTransportClient(settings) 
					  .addTransportAddress(new TransportAddress(InetAddress.getByName(elasticsearchUrl), elasticsearchPort));
			String jsonResult = mapper.writeValueAsString(result);
			IndexResponse esResponse = client.prepareIndex(elasticsearchIndexName, elasticsearchIndexType)
				      .setSource(jsonResult, XContentType.JSON).get();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if(client != null) {
				client.close();
			}
		}
	}
	
}
