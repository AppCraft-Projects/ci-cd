# init
echo "Creating network 'jenkins'..."
docker network create jenkins

echo "Creating Jenkins volumes..."
docker volume create jenkins-docker-certs
docker volume create jenkins-data

# This enables Jenkins to run Docker commands on its nodes.
echo "Starting Docker in Docker..."
docker container run --name jenkins-docker --detach \
  --privileged --network jenkins --network-alias docker \
  --env DOCKER_TLS_CERTDIR=/certs \
  --volume /f/dev/training/training360/projects/ci-cd:/git_repo:ro \
  --volume jenkins-data:/var/jenkins_home \
  --volume jenkins-docker-certs:/certs/client \
  --publish 2376:2376 docker:dind

echo "Starting Jenkins with BlueOcean..."
docker container run --name jenkins-blueocean --detach \
  --network jenkins --env DOCKER_HOST=tcp://docker:2376 \
  --env DOCKER_CERT_PATH=/certs/client --env DOCKER_TLS_VERIFY=1 \
  --volume /f/dev/training/training360/projects/ci-cd:/git_repo:ro \
  --volume jenkins-data:/var/jenkins_home \
  --volume jenkins-docker-certs:/certs/client:ro \
  --publish 8080:8080 --publish 50000:50000 jenkinsci/blueocean
