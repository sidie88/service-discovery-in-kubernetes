node {
  def remote = [:]
  remote.name = 'MasterKubernetes'
  remote.host = '172.20.103.122'
  remote.port = 2220
  remote.user = 'tarsidi'
  remote.password = 'Di49tars'
  remote.allowAnyHosts = true
  stage('Sync source code') {
    sshCommand remote: remote, command: "cd service-discovery-in-kubernetes/ "+
        " && git pull origin master"
  }
  stage('Build currency-exchange') {
	withCredentials([usernamePassword(credentialsId: 'sidie88-hub.docker.com', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
		sshCommand remote: remote, command: "cd service-discovery-in-kubernetes/ && "+
			"./maven-build.sh currency-exchange-service && " + 
			"cd currency-exchange-service/ && " + 
			"docker image build -t sidie88/currency-exchange:$BUILD_TAG . && " +
			"docker login -u $USERNAME -p $PASSWORD && " + 
			"docker image push sidie88/currency-exchange:$BUILD_TAG"
	}
  }
  stage('Deploy currency-exchange') {
    sshCommand remote: remote, command: "kubectl --record "+
        " deployment.apps/currency-exchange-pod -n tarsidi"+
        " set image deployment.v1.apps/currency-exchange-pod " + 
        " currency-exchange=sidie88/currency-exchange:$BUILD_TAG"
  }
  stage('Build spring-api-gateway-server') {
	withCredentials([usernamePassword(credentialsId: 'sidie88-hub.docker.com', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
		sshCommand remote: remote, command: "cd service-discovery-in-kubernetes/ && "+
			"./maven-build.sh spring-api-gateway-server && " + 
			"cd spring-api-gateway-server/ && " + 
			"docker image build -t sidie88/spring-api-gateway-server:$BUILD_TAG . && " +
			"docker login -u $USERNAME -p $PASSWORD && " + 
			"docker image push sidie88/spring-api-gateway-server:$BUILD_TAG"
	}
  }
  stage('Deploy spring-api-gateway-server') {
    sshCommand remote: remote, command: "kubectl --record "+
        " deployment.apps/spring-api-gateway-server-pod -n tarsidi"+
        " set image deployment.v1.apps/spring-api-gateway-server-pod " + 
        " spring-api-gateway-server=sidie88/spring-api-gateway-server:$BUILD_TAG"
  }
  stage('Build currency-conversion-service') {
	withCredentials([usernamePassword(credentialsId: 'sidie88-hub.docker.com', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
		sshCommand remote: remote, command: "cd service-discovery-in-kubernetes/ && "+
			"./maven-build.sh currency-conversion-service && " + 
			"cd currency-conversion-service/ && " + 
			"docker image build -t sidie88/currency-conversion-service:$BUILD_TAG . && " +
			"docker login -u $USERNAME -p $PASSWORD && " + 
			"docker image push sidie88/currency-conversion-service:$BUILD_TAG"
	}
  }
  stage('Deploy currency-conversion-service') {
    sshCommand remote: remote, command: "kubectl --record "+
        " deployment.apps/currency-conversion-pod -n tarsidi"+
        " set image deployment.v1.apps/currency-conversion-pod " + 
        " currency-conversion=sidie88/currency-conversion-service:$BUILD_TAG"
  }
  stage('Build microservices-logging') {
	withCredentials([usernamePassword(credentialsId: 'sidie88-hub.docker.com', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
		sshCommand remote: remote, command: "cd service-discovery-in-kubernetes/ && "+
			"./maven-build.sh microservices-logging && " + 
			"cd microservices-logging/ && " + 
			"docker image build -t sidie88/microservices-logging:$BUILD_TAG . && " +
			"docker login -u $USERNAME -p $PASSWORD && " + 
			"docker image push sidie88/microservices-logging:$BUILD_TAG"
	}
  }
  stage('Deploy microservices-logging') {
    sshCommand remote: remote, command: "kubectl --record "+
        " deployment.apps/microservices-logging-pod -n tarsidi"+
        " set image deployment.v1.apps/microservices-logging-pod " + 
        " microservices-logging=sidie88/microservices-logging:$BUILD_TAG"
  }
}