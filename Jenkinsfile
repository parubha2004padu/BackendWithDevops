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
                    set -eu

                    export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
                    export PATH="$JAVA_HOME/bin:$PATH"

                    echo "Java version:"
                    java --version

                    echo "Java compiler version:"
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
                    set -eu

                    docker build -t backenddevops:${BUILD_NUMBER} .
                    docker tag backenddevops:${BUILD_NUMBER} backenddevops:latest
                '''
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying backend and MySQL with Docker Compose...'

                sh '''
                    set -eu
                    docker compose up -d --build
                    docker compose ps
                '''
            }
        }

        stage('Verify') {
            steps {
                echo 'Waiting for the backend API to respond...'

                sh '''
                    set -eu

                    attempt=1
                    max_attempts=24

                    while [ "$attempt" -le "$max_attempts" ]; do

                        status=$(curl -sS \
                            --connect-timeout 3 \
                            --max-time 10 \
                            -o /tmp/backend-api-response.json \
                            -w '%{http_code}' \
                            http://localhost:8081/api/students 2>/dev/null || true)

                        case "$status" in
                            2[0-9][0-9]|3[0-9][0-9])
                                echo "API responded with HTTP $status"
                                cat /tmp/backend-api-response.json
                                exit 0
                                ;;
                        esac

                        echo "Attempt $attempt/$max_attempts: API not ready yet (HTTP $status)"
                        sleep 5
                        attempt=$((attempt + 1))
                    done

                    echo "API verification failed."
                    echo "Recent backend logs:"
                    docker logs backend-compose --tail 100 || true
                    exit 1
                '''
            }
        }
    }

    post {
        success {
            echo 'CI/CD pipeline completed successfully!'
        }

        failure {
            echo 'CI/CD pipeline failed. Check the stage and Console Output.'
        }

        always {
            echo 'Pipeline execution finished.'
        }
    }
}
