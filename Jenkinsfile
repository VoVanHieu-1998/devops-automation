pipeline {
    agent any
    tools {
        maven 'maven_3_6_3'
    }
    environment {
        DOCKER_HOME = tool name: 'myDockerJenkins', type: 'org.jenkinsci.plugins.docker.commons.tools.DockerTool'
        PATH = "${DOCKER_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout SCM') {
            steps {
                script {
                    // Checkout the repository containing the .env and docker-compose.yml file
                    checkout([$class: 'GitSCM', branches: [[name: '*/develop']], userRemoteConfigs: [[url: 'https://github.com/VoVanHieu-1998/devops-automation']]])
                }
            }
        }
        stage('Read .env') {
            steps {
                script {
                    def envFile = readFile '.env'
                    def envVars = envFile.split("\n").collect { line ->
                        def parts = line.split('=')
                        if (parts.length == 2) {
                            return "${parts[0].trim()}=${parts[1].trim()}"
                        }
                    }.join('\n')
                    writeFile file: 'parsed_env', text: envVars
                    def parsedEnv = readFile('parsed_env').trim().split('\n')
                    parsedEnv.each { line ->
                        def parts = line.split('=')
                        env[parts[0]] = parts[1]
                    }
                }
            }
        }
        stage('Set Environment Variables') {
            steps {
                script {
                    // Echo the specific environment variables to verify
                    echo "DOCKER_COMPOSE_VERSION: ${env.DOCKER_COMPOSE_VERSION}"
                    echo "MYSQL_ROOT_PASSWORD: ${env.MYSQL_ROOT_PASSWORD}"
                }
            }
        }
        stage('Build and Push Docker image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh 'docker login -u $DOCKER_USER -p $DOCKER_PASS'
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
