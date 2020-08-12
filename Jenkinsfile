pipeline {
    // agent none 
    agent {
        image 'maven:3-alpine'
        args '-u root'
    }
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
            steps {
                sh 'mvn -B -DskipTests clean install'
            }
        }
    }
}