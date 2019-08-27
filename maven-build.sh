#!/bin/bash
echo "***************************"
echo "******* Building jar ******"
echo "***************************"
PROJECT=$1
echo $PROJECT
docker run --rm  -v  $(pwd)/$PROJECT:/app -v /root/.m2/:/root/.m2/ -w /app maven:3-alpine mvn install
