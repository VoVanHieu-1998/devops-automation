pipeline {
    agent any
    tools{
        maven 'maven_3_6_3'
    }
    environment {
        DOCKER_HOME = tool name: 'myDockerJenkins', type: 'org.jenkinsci.plugins.docker.commons.tools.DockerTool'
        PATH = "${DOCKER_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Build') {
            steps {
                checkout scmGit(branches: [[name: '*/develop']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/VoVanHieu-1998/devops-automation']])
                sh 'mvn clean install'
            }
        }
        stage('Login to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'docker login -u $DOCKER_USER -p $DOCKER_PASS'
                }
            }
        }
        stage('Build Docker image') {
            steps {
                sh 'docker build -t hieudocker2023/deploy-app .'
            }
        }

        stage('Push Docker image to Docker Hub') {
            steps {
                sh 'docker push hieudocker2023/deploy-app'
                sh 'docker logout'
            }
        }
    }
}