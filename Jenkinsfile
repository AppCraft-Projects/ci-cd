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
        stage('Jacoco') {
            steps {
                sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent test --fail-at-end -DskipTests=false -am'
            }
        }
        stage('Sonar') {
            steps {
                sh 'mvn -e sonar:sonar \
                      -Dsonar.projectKey=cicd \
                      -Dsonar.host.url=http://sonarqube:9000 \
                      -Dsonar.login=f529a61c801dfe9e1e848a74f543ea129e4bdfc1'
            }
        }
    }
}
