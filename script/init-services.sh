# init
echo "Creating network 'cicd'..."
docker network create cicd

echo "Creating Jenkins volumes..."
docker volume create jenkins-docker-certs
docker volume create jenkins-data

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

echo "Starting GitLab..."
docker run --name gitlab --detach \
  --network cicd \
  --privileged \
  --env GITLAB_ROOT_EMAIL=adam.arold@gmail.com \
  --hostname localhost \
  --env GITLAB_OMNIBUS_CONFIG="external_url 'http://localhost'; gitlab_rails['lfs_enabled'] = true;" \
  --publish 8443:443 --publish 8082:80 --publish 22:22 \
  --volume "$GITLAB_HOME"/config:/etc/gitlab \
  --volume "$GITLAB_HOME"/logs:/var/log/gitlab \
  --volume "$GITLAB_HOME"/data:/var/opt/gitlab \
  gitlab/gitlab-ee:latest
