pipeline {
    agent any

    environment {
        DOCKER_HUB_CREDS = 'docker-hub-credentials'
        DOCKER_IMAGE_NAME = 'yguptaji/calculator-devops'
        NOTIFICATION_EMAIL = 'yashgupta35xc@gmail.com'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE_NAME}:${BUILD_NUMBER} -t ${DOCKER_IMAGE_NAME}:latest ."
            }
        }

        stage('Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${DOCKER_HUB_CREDS}", passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                    sh "echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin"
                    sh "docker push ${DOCKER_IMAGE_NAME}:${BUILD_NUMBER}"
                    sh "docker push ${DOCKER_IMAGE_NAME}:latest"
                }
            }
        }

        // stage('Deploy') {
        //     steps {
        //         sh "BUILD_NUMBER=${BUILD_NUMBER} DOCKER_IMAGE_NAME=${DOCKER_IMAGE_NAME} docker compose down"
        //         sh "BUILD_NUMBER=${BUILD_NUMBER} DOCKER_IMAGE_NAME=${DOCKER_IMAGE_NAME} docker compose up -d"
        //         sh 'sleep 10'
        //         sh 'curl -f http://localhost:80/actuator/health'
        //     }
        // }
        stage('Deploy') {
            steps {
                sh "BUILD_NUMBER=${BUILD_NUMBER} DOCKER_IMAGE_NAME=${DOCKER_IMAGE_NAME} docker-compose down"
                sh "BUILD_NUMBER=${BUILD_NUMBER} DOCKER_IMAGE_NAME=${DOCKER_IMAGE_NAME} docker-compose up -d"
                sh 'sleep 10'
                sh 'curl -f http://localhost:80/actuator/health'
            }
        }
    }

    post {
        success {
            mail to: "${NOTIFICATION_EMAIL}",
                 subject: "SUCCESS: Jenkins Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Build #${env.BUILD_NUMBER} completed successfully.\nURL: ${env.BUILD_URL}"
        }
        failure {
            mail to: "${NOTIFICATION_EMAIL}",
                 subject: "FAILURE: Jenkins Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Build #${env.BUILD_NUMBER} failed.\nURL: ${env.BUILD_URL}"
        }
    }
}