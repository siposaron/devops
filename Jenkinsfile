pipeline {
    agent none 
    
    stages {
        stage('Building parallel') {
            failFast true
            parallel {
                stage('Build Aggregator') {
                    agent {
                        label 'aggregator-agent-local'
                    }
                    steps {
                        sh 'make build-aggregator'
                    }
                }
                stage('Build Sensor') {
                    agent {
                        label 'aggregator-agent-local'
                    }
                    steps {
                        sh 'make build-sensor'
                    }
                }
            }
        }
        stage('Docker release parallel') {
            failFast true
            parallel {
                stage('Release Aggregator docker image') {
                    agent {
                        label 'aggregator-agent-local'
                    }
                    steps {
                        sh 'make docker-release-aggregator'
                    }
                }
                stage('Release Sensor docker image') {
                    agent {
                        label 'aggregator-agent-local'
                    }
                    steps {
                        sh 'make docker-release-sensor'
                    }
                }
            }
        }
    }

}