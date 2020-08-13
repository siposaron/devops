pipeline {
    agent none 
    
    stages {
        stage('Build') {
            agent {
                label 'aggregator-agent-local'
            }
            steps {
                sh 'make build'
            }
        }
    }

}