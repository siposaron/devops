pipeline {
    agent none 
    
    stages {
    //     stage('OpenShift Check') {
    //         environment {
    //             OC_TOKEN = credentials('SA_OC_TOKEN')
    //         }
    //         agent {
    //             kubernetes {
    //                 label 'oc-agent'
    //                 defaultContainer 'builder'
    //                 yaml """
    //                     kind: Pod
    //                     metadata:
    //                       name: oc-agent
    //                     spec:
    //                       containers:
    //                       - name: builder
    //                         image: widerin/openshift-cli
    //                         imagePullPolicy: Always
    //                         tty: true
    //                     """
    //             } // kubernetes
    //         } // agent
    //         steps {
    //             script {
    //                 sh("oc login --token=$OC_TOKEN")
    //                 // sh "make deploy-services"
    //             } // container
    //         } // steps
    //     } // stage(deploy)

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
            environment {
                DOCKER_USER = credentials('SA_DOCKER_USERNAME')
                DOCKER_PASS = credentials('SA_DOCKER_PASS')
            }
            failFast true
            parallel {
                stage('Release Aggregator docker image') {
                    agent {
                        label 'aggregator-agent-local'
                    }
                    steps {
                        sh("docker login -u $DOCKER_USER -p $DOCKER_PASS")
                        sh 'make docker-release-aggregator'
                    }
                }
                stage('Release Sensor docker image') {
                    agent {
                        label 'aggregator-agent-local'
                    }
                    steps {
                        sh("docker login -u $DOCKER_USER -p $DOCKER_PASS")
                        sh 'make docker-release-sensor'
                    }
                }
            }
        }
        stage('Deploy services') {
            environment {
                OPENSHIFT_URL = 'https://api.okd.codespring.ro:6443'
                OC_TOKEN = credentials('SA_OC_TOKEN')
            }
            agent {
                label 'aggregator-agent-local'
            }        
            steps {
                script {
                    sh("oc login $OPENSHIFT_URL --token=$OC_TOKEN --insecure-skip-tls-verify")
                    sh "make deploy-services"
                } // container
            } // steps
        } // stage(deploy)
    }

}