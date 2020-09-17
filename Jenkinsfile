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
        stage('Coverage') {
            steps {
                sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent test --fail-at-end -DskipTests=false -am'
            }
        }
        stage('Code Quality') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh 'mvn -e sonar:sonar'
                }
            }
        }
        stage('Deploy to Staging') {
            when {
                branch 'dev'
            }
            steps {
                echo 'Deploying to Staging'
            }
        }

    }
}
