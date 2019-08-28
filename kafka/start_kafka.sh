#!/bin/bash
cd config 
if [ -z "$KAFKA_BROKER_ID" ]
then
      echo "\$KAFKA_BROKER_ID is empty"
	  KAFKA_BROKER_ID="0"
fi
if [ -z "$KAFKA_ZOOKEEPER_CONNECT_PORT" ]
then
      echo "\$KAFKA_ZOOKEEPER_CONNECT_PORT is empty"
	  KAFKA_ZOOKEEPER_CONNECT_PORT="2181"
fi
sed "s/broker\.id\=0/broker.id\=$KAFKA_BROKER_ID/g; s/localhost/$KAFKA_ZOOKEEPER_CONNECT_HOST/g; s/2181/$KAFKA_ZOOKEEPER_CONNECT_PORT/g; s/\#advertised\.listeners\=PLAINTEXT\:\/\/your\.host\.name\:9092/advertised\.listeners\=PLAINTEXT\:\/\/$KAFKA_ADVERTISED_HOST\:9092/g; s/\#listeners\=PLAINTEXT\:\/\/\:9092/listeners\=PLAINTEXT\:\/\/$KAFKA_ADVERTISED_HOST:9092/g" server.properties > server_active.properties

if [ -z "$KAFKA_ADVERTISED_HOST" ]
then
      echo "\$KAFKA_ZOOKEEPER_CONNECT_PORT is empty"
else
	  echo -e "\n" >> server_active.properties
	  echo "advertised.host.name=$KAFKA_ADVERTISED_HOST" >> server_active.properties
fi

cd ../
exec bin/kafka-server-start.sh config/server_active.properties