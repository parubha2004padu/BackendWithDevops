pipeline {

    agent {
        label 'linux-agent'
    }

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out backend source code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building Spring Boot backend with Java 17...'

                sh '''

		export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
                export PATH=$JAVA_HOME/bin:$PATH			

		
	         echo "Java used by Maven:"
            java --version

            echo "Javac used by Maven:"
            javac --version
		

                    chmod +x mvnw
                    ./mvnw clean package -DskipTests
                '''
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building Docker image...'

                sh '''
                    docker build -t backenddevops:${BUILD_NUMBER} .
                    docker tag backenddevops:${BUILD_NUMBER} backenddevops:latest
                '''
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying backend with Docker Compose...'

                sh '''
                    docker compose up -d --build
                '''
            }
        }

        stage('Verify') {
            steps {
                echo 'Checking backend API...'

                sh '''
                    sleep 15
                    curl -f http://localhost:8081/api/students
                '''
            }
        }
    }

    post {

        success {
            echo 'CI/CD pipeline completed successfully!'
        }

        failure {
            echo 'CI/CD pipeline failed!'
        }
    }
}
