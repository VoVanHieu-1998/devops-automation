pipeline {
    agent any
    tools{
        maven 'maven_3_6_3'
    }
    environment {
        DOCKER_HOME = tool name: 'myDockerJenkins', type: 'org.jenkinsci.plugins.docker.commons.tools.DockerTool'
        PATH = "${DOCKER_HOME}/bin:${env.PATH}"
        DOCKER_COMPOSE_VERSION = ''
        MYSQL_ROOT_PASSWORD = ''
    }

    stages {
//         stage('Build') {
//             steps {
//                 checkout scmGit(branches: [[name: '*/develop']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/VoVanHieu-1998/devops-automation']])
//                 sh 'mvn clean install'
//             }
//         }
        stage('Read .env') {
            steps {
                script {
                    // Read .env file
                    def envFile = readFile '.env'

                    // Print content of .env file
                    echo "Content of .env file:\n${envFile}"

                    // Parse .env file and create environment variables
                    def envVars = envFile.split("\n").collect { line ->
                        def parts = line.split('=')
                        if (parts.length == 2) {
                            return "${parts[0].trim()}=${parts[1].trim()}"
                        }
                    }.join('\n')

                    // Write parsed environment variables to a temporary .env file
                    writeFile file: 'parsed_env', text: envVars

                    // Load the environment variables from the temporary .env file
                    script {
                        def parsedEnv = readFile('parsed_env').trim().split('\n')
                        echo "Content of .parsedEnv file:\n${parsedEnv}"
                        parsedEnv.each { line ->
                            def parts = line.split('=')
                            echo "Content of .parts file:\n${parts}"
                            env[parts[0]] = parts[1]
                            def key = parts[0].trim()
                            def value = parts[1].trim()
                            // Set environment variable
                            sh "export ${key}=${value}"
                            // Echo the environment variable
                            echo "${key}: ${value}"
                        }
                    }

                    // Echo the specific environment variables to verify
                    echo "DOCKER_COMPOSE_VERSION: ${env.DOCKER_COMPOSE_VERSION}"
                    echo "MYSQL_ROOT_PASSWORD: ${env.MYSQL_ROOT_PASSWORD}"
                }
            }
        }
        stage('Read Config') {
            steps {
                script {
                    echo "Reading config env file from: ${env.WORKSPACE}/config.yml"
                    echo "Reading config file from: ${WORKSPACE}/config.yml"
                    // Read the config.yml file
                    // Print the content of config.yml
                    sh 'cat /var/jenkins_home/workspace/devops-automation-1/config.yml'

                    // Read the config.yml file
                    def config = readYaml file: '/var/jenkins_home/workspace/devops-automation-1/config.yml'

                    // Set environment variables from config.yml
                    env.DOCKER_COMPOSE_VERSION = config.DOCKER_COMPOSE_VERSION
                    env.MYSQL_ROOT_PASSWORD = config.MYSQL_ROOT_PASSWORD
                    echo "DOCKER_COMPOSE_VERSION: ${env.DOCKER_COMPOSE_VERSION}"
                    echo "MYSQL_ROOT_PASSWORD: ${env.MYSQL_ROOT_PASSWORD}"
                }
            }
        }
//         stage('Build and Push Docker image') {
//             steps {
//                 script {
//                     withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
//                         sh 'docker login -u $DOCKER_USER -p $DOCKER_PASS'
//                         sh 'docker-compose -f docker-compose.yml --env-file .env build identity-spring-container'
//                         sh 'docker tag devops-automation-1_identity-spring-container:latest hieudocker2023/jenkins_first_project:latest'
//                         sh 'docker push hieudocker2023/jenkins_first_project:latest'
//                         sh 'docker logout'
//                     }
//                 }
//             }
//         }
    }
}