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
                withSonarQubeEnv('sonarqube') {
                    sh 'mvn -e sonar:sonar'
                } // submitted SonarQube taskId is automatically attached to the pipeline context
            }
        }
        stage("Quality Gate"){
          timeout(time: 5, unit: 'MINUTES') {
              // we wait for quality gate to finish
          }
        }
    }
}
