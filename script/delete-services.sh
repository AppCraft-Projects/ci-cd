# we need pwd to be the script's dir
cd "$(dirname "$0")" || exit 1

bash ./stop-services.sh

echo "Deleting containers..."
docker container rm jenkins-docker
docker container rm jenkins-blueocean
docker container rm bitbucket
docker container rm sonarqube
docker container rm nexus

echo "Cleaning up resources..."
docker network rm cicd
docker volume rm jenkins-docker-certs
docker volume rm jenkins-data
docker volume rm nexus-data
