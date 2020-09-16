pipeline {
    agent {
        docker {
            image 'openjdk:8'
            args '--volume /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh './mvnv -B -DskipTests clean package'
            }
        }
    }
}
