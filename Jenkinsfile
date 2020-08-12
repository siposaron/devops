pipeline {
    agent none 
    stages {
        // stage('Stage Hello') {
        //     agent {
        //         label 'aggregator-agent-local'
        //     }

        //     steps {
        //         echo 'Hello world!' 
        //     }
        // }
        stage('Build') {
            agent {
                image 'maven:3-alpine'
                args '-u root'
            }
            steps {
                sh 'mvn -B -DskipTests clean install'
            }
        }
    }
}