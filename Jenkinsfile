pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Sonar') {
            steps {
                sh 'mvn sonar:sonar \
                      -Dsonar.projectKey=cicd \
                      -Dsonar.host.url=http://sonarqube:9000 \
                      -Dsonar.login=f529a61c801dfe9e1e848a74f543ea129e4bdfc1'
            }
        }
    }
}
