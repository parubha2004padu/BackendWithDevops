# BackendWithDevops
add the project codein github and create a docker image for this project

./mvnw clean package -DskipTests

docker network create backend-network


docker run -d \
  --name mysql-backend \
  --network backend-network \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=backenddevops \
  -p 3306:3306 \
  mysql:latest

docker build -t backenddevops:1.0 .




docker run -d \
  --name backenddevops-container \
  --network backend-network \
  -p 8080:8080 \
  backenddevops:1.0
  
