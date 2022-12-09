
def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "docker build -t karimkhammassi/my-repo:spring-app-${IMAGE_NAME} ."
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "docker push karimkhammassi/my-repo:spring-app-${IMAGE_NAME}"
    }
}

def pushToNexus() {
    echo "pushing the jar file to Nexus maven-snapshots repo..."
    sh 'mvn clean deploy -Dmaven.test.skip=true'
}

def sonarScan(String serverIp, String serverUser) {
    echo "Running sonarQube scan..."
    def runSonar = '"export MYSQLDB_ROOT_PASSWORD=karim MYSQLDB_DATABASE=pet_store MYSQLDB_LOCAL_PORT=3306 MYSQLDB_DOCKER_PORT=3306 && bash runSonarQube.sh"'
    sh "docker exec -it sonarQube-container bash -c ${runSonar}"
    // sshagent (credentials: ['sonar-server']) {
    //     sh "ssh -o StrictHostKeyChecking=no ${serverUser}@${serverIp} ${runSonar}"
    // }
    }

def deployApp(String serverIp, String serverUser) {
    echo 'deploying the application...'
    def composeRun = '"export MYSQLDB_USER=root MYSQLDB_ROOT_PASSWORD=karim MYSQLDB_DATABASE=pet_store MYSQLDB_LOCAL_PORT=3306 MYSQLDB_DOCKER_PORT=3306 SPRING_LOCAL_PORT=8080 SPRING_DOCKER_PORT=8080 && docker-compose up -d"'
    sshagent (credentials: ['deployment-server']) {
        sh "ssh -o StrictHostKeyChecking=no ${serverUser}@${serverIp} ${composeRun}"
    }
}

def cleanUntaggedImages(String serverIp, String serverUser){
    def cleanImages = 'docker image prune --force --filter "dangling=true"'
    sshagent (credentials: ['jenkins-server']) {
        sh "ssh -o StrictHostKeyChecking=no ${serverUser}@${serverIp} ${cleanImages}"
    }
}

return this
