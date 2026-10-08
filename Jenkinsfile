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

    post {
        success {
            echo 'Build stage completed successfully!'
        }

        failure {
            echo 'Build stage FAILED!'
        }
    }
}		

        stage('Environment Info') {
	    environment {
                DEMO_ENV = 'stage-specific'
		  }
            steps {
                echo "Application: ${env.APP_NAME}"
	        echo "Build Number: ${env.BUILD_NUMBER}"
                echo "Job Name: ${env.JOB_NAME}"
                echo "Workspace: ${env.WORKSPACE}"
		sh 'echo "Application from shell: $APP_NAME"'
		echo "Demo Environment: ${env.DEMO_ENV}"
		echo "Build Version: ${params.BUILD_VERSION}"
            }
        }
        stage('Credentials Test') {
            steps {
               withCredentials([
                      string(credentialsId: 'demo-secret', variable: 'MY_SECRET')
                               ]) {
                      sh 'echo "Secret is available to the shell, but we will not print it."'
                                   }
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
