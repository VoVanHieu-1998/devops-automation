pipeline {
    agent any
    tools{
        maven 'maven_3_6_3'
    }
    environment {
        DOCKER_HOME = tool name: 'myDockerJenkins', type: 'org.jenkinsci.plugins.docker.commons.tools.DockerTool'
        PATH = "${DOCKER_HOME}/bin:${env.PATH}"
        DOCKER_COMPOSE_VERSION = ''
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
        stage('Read Config') {
            steps {
                script {
                    // Read the config.yml file
                    def config = readYaml file: 'config.yml'

                    // Set environment variables from config.yml
                    env.DOCKER_COMPOSE_VERSION = config.DOCKER_COMPOSE_VERSION

                    // Write the .env file
                    writeFile file: '.env', text: """\
                    DOCKER_COMPOSE_VERSION=${env.DOCKER_COMPOSE_VERSION}
                    """
                }
            }
        }
        stage('Build and Push Docker image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh 'docker login -u $DOCKER_USER -p $DOCKER_PASS'
                        sh "printenv"
                        sh 'docker-compose -f docker-compose.yml --env-file .env build identity-spring-container'
                        sh 'docker tag devops-automation-1_identity-spring-container:latest hieudocker2023/jenkins_first_project:latest'
                        sh 'docker push hieudocker2023/jenkins_first_project:latest'
                        sh 'docker logout'
                    }
                }
            }
        }
    }
}