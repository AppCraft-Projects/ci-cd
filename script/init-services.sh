# init
echo "Creating network 'cicd'..."
docker network create cicd

echo "Creating volumes..."
docker volume create jenkins-docker-certs
docker volume create jenkins-data
docker volume create bitbucket
docker volume create nexus-data

# This enables Jenkins to run Docker commands on its nodes.
echo "Starting Docker in Docker..."
docker run --name jenkins-docker --detach \
  --privileged --network cicd --network-alias docker \
  --env DOCKER_TLS_CERTDIR=/certs \
  --volume jenkins-data:/var/jenkins_home \
  --volume jenkins-docker-certs:/certs/client \
  --publish 2376:2376 docker:dind

echo "Starting Jenkins with BlueOcean..."
docker run --name jenkins-blueocean --detach \
  --network cicd --env DOCKER_HOST=tcp://docker:2376 \
  --env DOCKER_CERT_PATH=/certs/client --env DOCKER_TLS_VERIFY=1 \
  --volume jenkins-data:/var/jenkins_home \
  --volume jenkins-docker-certs:/certs/client:ro \
  --publish 8080:8080 --publish 50000:50000 jenkinsci/blueocean

echo "Starting BitBucket..."
docker run --name bitbucket --detach \
  --network cicd \
  --privileged \
  --volume bitbucket:/var/atlassian/application-data/bitbucket \
  --publish 7990:7990 \
  --publish 7999:7999 \
  atlassian/bitbucket-server \

echo "Starting SonarQube..."
docker run --name sonarqube --detach \
  --network cicd \
  --publish 9000:9000 \
  sonarqube:7.9.4-community

echo "Starting Nexus..."
docker run --name nexus --detach \
  --network cicd \
  --publish 8081:8081 \
  --volume nexus-data:/nexus-data \
  sonatype/nexus3
