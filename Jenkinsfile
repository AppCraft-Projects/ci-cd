pipeline {
    stages {
        stage('Build') {
            steps {
                sh './mvnv -B -DskipTests clean package'
            }
        }
    }
}
