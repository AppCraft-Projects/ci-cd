node {
    docker.image('maven:3-alpine').withRun('-v /root/.m2:/root/.m2') {}
    stage('Build') {
        sh 'mvn -B -DskipTests clean package'
    }
    stage('Test') {
        sh 'mvn test'
        post {
            always {
                junit 'target/surefire-reports/*.xml'
            }
        }
    }
    stage('Jacoco') {
        sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent test --fail-at-end -DskipTests=false -am'
    }
    stage('Sonar') {
        withSonarQubeEnv('sonarqube') {
            sh 'mvn -e sonar:sonar'
        } // submitted SonarQube taskId is automatically attached to the pipeline context
    }
    stage("Quality Gate"){
        timeout(time: 5, unit: 'MINUTES') {
            def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
            if (qg.status != 'OK') {
                error "Pipeline aborted due to quality gate failure: ${qg.status}"
            }
        }
    }
}
