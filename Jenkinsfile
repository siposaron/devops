pipeline {
    agent none 
    stages {
        stage('Stage Hello') {
            agent {
                label 'aggregator-agent-local'
            }

            steps {
                echo 'Hello world!' 
            }
        }
    }
}