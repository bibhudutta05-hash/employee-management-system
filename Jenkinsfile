pipeline {
    agent any
    
    environment {
        APP_NAME = 'employee-management-system'
             }
    stages {

        stage('Build') {
            steps {
                sh './mvnw clean compile'
            }
        }

        stage('Environment Info') {
            steps {
                echo "Application: ${env.APP_NAME}"
	        echo "Build Number: ${env.BUILD_NUMBER}"
                echo "Job Name: ${env.JOB_NAME}"
                echo "Workspace: ${env.WORKSPACE}"
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Package') {
            steps {
                sh './mvnw package'
            }

            post {
                success {
                    archiveArtifacts artifacts: 'target/employee-management-system-0.0.1-SNAPSHOT.jar'
                }
            }
        }

    }
}
