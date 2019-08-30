# Installation

## Sync source code

`cd service-discovery-in-kubernetes/ \ `

 `        && git pull origin master`
  
  
## Build currency-exchange 

`cd service-discovery-in-kubernetes/ && \ `

`			./maven-build.sh currency-exchange-service && \ `

`			cd currency-exchange-service/ && \ `

`			docker image build -t sidie88/currency-exchange:$BUILD_TAG . && \ `

`			docker login -u $USERNAME -p $PASSWORD &&  \ `

`			docker image push sidie88/currency-exchange:$BUILD_TAG `
			
## Deploy currency-exchange

`kubectl apply -f service-discovery-in-kubernetes/currency-exchange-service/currency-exchange-service.yaml`

## Build spring-api-gateway-server 

`cd service-discovery-in-kubernetes/ && \ `

`			./maven-build.sh spring-api-gateway-server &&  \ `

`			cd spring-api-gateway-server/ &&  \ `

`			docker image build -t sidie88/spring-api-gateway-server:$BUILD_TAG . && \ `

`			docker login -u $USERNAME -p $PASSWORD &&  \ `

`			docker image push sidie88/spring-api-gateway-server:$BUILD_TAG `
			
## Deploy spring-api-gateway-server

`kubectl apply -f service-discovery-in-kubernetes/spring-api-gateway-server/spring-api-gateway-server.yaml`
  
## Build currency-conversion-service 

`cd service-discovery-in-kubernetes/ && \ `

`			./maven-build.sh currency-conversion-service &&  \ `

`			cd currency-conversion-service/ &&  \ `

`			docker image build -t sidie88/currency-conversion-service:$BUILD_TAG . && \ `

`			docker login -u $USERNAME -p $PASSWORD &&  \ `

`			docker image push sidie88/currency-conversion-service:$BUILD_TAG `
			
## Deploy currency-conversion-service

`kubectl apply -f service-discovery-in-kubernetes/currency-conversion-service/currency-conversion-service.yaml`
  
## Build microservices-logging 

`cd service-discovery-in-kubernetes/ && \ `

`			./maven-build.sh microservices-logging &&  \ `

`			cd microservices-logging/ &&  \ `

`			docker image build -t sidie88/microservices-logging:$BUILD_TAG . && \ `

`			docker login -u $USERNAME -p $PASSWORD &&  \ `

`			docker image push sidie88/microservices-logging:$BUILD_TAG  `

## Deploy microservices-logging

`kubectl apply -f service-discovery-in-kubernetes/microservices-logging/microservices-logging.yaml`
  
# Deployment using jenkins

If there any code changes, you can deploy new changes using jenkins by following these steps below :

1. Create your docker hub credential at > jenkins > credentials > System > Global credentials (unrestricted) > Add Credentials

2. Type your Username, Password, and ID. ID will be used at pipeline script

3. Create deployment at Jenkins > New Item > type deloyment name at "Enter an item name"

4. choose Pipeline and click OK

5. Go to Pipeline at the botom of the page, copy & paste all code from Jenkinsfile into "Script" text area, and click Save

6. Click Build Now at the left of the page to start deployment

