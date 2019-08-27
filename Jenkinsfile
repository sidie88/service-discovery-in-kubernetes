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
  stage('Build docker') {
	withCredentials([usernamePassword(credentialsId: 'sidie88-hub.docker.com', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
		sshCommand remote: remote, command: "cd service-discovery-in-kubernetes/ && "+
			"./maven-build.sh currency-exchange-service && " +
			"cd currency-exchange-service/ && " +
			"docker image build -t sidie88/currency-exchange:$BUILD_TAG . && " +
			"docker login -u $USERNAME -p $PASSWORD && " +
			"docker image push sidie88/currency-exchange:$BUILD_TAG"
	}
  }
  stage('Deploy') {
    sshCommand remote: remote, command: "kubectl --record "+
        " deployment.apps/currency-exchange-pod -n tarsidi"+
        " set image deployment.v1.apps/currency-exchange-pod " +
        " currency-exchange=sidie88/currency-exchange:$BUILD_TAG"
  }
}
