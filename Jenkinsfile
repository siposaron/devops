pipeline {
    agent none 
    
    stages {
        stage('OpenShift Check') {
            environment {
                OC_TOKEN = credentials('SA_OC_TOKEN')
            }
            agent {
                kubernetes {
                    label 'oc-agent'
                    defaultContainer 'builder'
                    yaml """
                        kind: Pod
                        metadata:
                          name: oc-agent
                        spec:
                          containers:
                          - name: builder
                            image: widerin/openshift-cli
                            imagePullPolicy: Always
                            tty: true
                        """
                } // kubernetes
            } // agent
            steps {
                script {
                    sh("oc login --token=$OC_TOKEN")
                    // sh "make deploy-services"
                } // container
            } // steps
        } // stage(deploy)

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
        // stage('Build services with Kube agents') {
        //     agent {
        //         kubernetes {
        //             label 'aggregator-build-agent'
        //             defaultContainer 'builder'
        //             yaml """
        //                 kind: Pod
        //                 metadata:
        //                   name: aggregator-build-agent
        //                 spec:
        //                   containers:
        //                   - name: builder
        //                     image: maven:3-adoptopenjdk-11
        //                     imagePullPolicy: Always
        //                     tty: true
        //             """
        //         } // kubernetes
        //     } // agent
        //     steps {
        //         script {
        //             sh 'make build-aggregator'
        //             sh 'make build-sensor'
        //         } // container
        //     } // steps
        // } // stage(build)

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
                OC_TOKEN = credentials('SA_OC_TOKEN')
            }
            agent {
                kubernetes {
                    label 'oc-agent'
                    defaultContainer 'builder'
                    yaml """
                        kind: Pod
                        metadata:
                          name: oc-agent
                        spec:
                          containers:
                          - name: builder
                            image: widerin/openshift-cli
                            imagePullPolicy: Always
                            tty: true
                        """
                } // kubernetes
            } // agent
            steps {
                script {
                    sh("oc login --token=$OC_TOKEN")
                    sh "make deploy-services"
                } // container
            } // steps
        } // stage(deploy)
    }

}