node {
  def remote = [:]
  remote.name = 'MasterKubernetes'
  remote.host = '172.20.103.122'
  remote.port = 2220
  remote.user = 'tarsidi'
  remote.password = 'Di49tars'
  remote.allowAnyHosts = true
  stage('Git pull') {
    sshCommand remote: remote, command: "cd kubernetes-service/ && "+
        "git pull origin master"
  }
  stage('Build image') {
    sshCommand remote: remote, command: "cd kubernetes-service/ && " +
        " ./build-maven.sh rnd-angular7 sidie88/backend-service:$BUILD_TAG"
  }
  stage('Push image') {
      withCredentials([usernamePassword(credentialsId: 'sidie88-hub.docker.com', 
        usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        sshCommand remote: remote, command: "cd kubernetes-service/ && " +
            "docker login -u $USERNAME -p $PASSWORD && " +
			"docker image push sidie88/backend-service:$BUILD_TAG"  
      }
  }
  stage('Update image tag') {
    sshCommand remote: remote, command:"sed \"s/IMAGE_TAG/sidie88\\/backend-service:$BUILD_TAG/g\" "+
        "kubernetes-service/rnd-angular7/backend-service.yaml.bak > "+
        "kubernetes-service/rnd-angular7/backend-service.yaml"
  }
  stage('Deploy') {
    sshCommand remote: remote, command: "kubectl apply -f "+
        "kubernetes-service/rnd-angular7/backend-service.yaml"
  }
}
