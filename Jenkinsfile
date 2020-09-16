pipeline {
    agent {
        docker {
            image 'adoptopenjdk/openjdk8'
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
