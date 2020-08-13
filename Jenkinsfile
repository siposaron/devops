pipeline {
    agent none 
    
    stages {
        stage('Build') {
            agent {
                label 'aggregator-agent-local'
            }
            steps {
                sh 'mvn -B -DskipTests clean install'
            }
        }
    }
    
}