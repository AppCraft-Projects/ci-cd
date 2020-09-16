# we need pwd to be the script's dir
cd "$(dirname "$0")" || exit 1

bash ./stop-jenkins.sh

echo "Deleting containers..."
docker container rm jenkins-docker
docker container rm jenkins-blueocean

echo "Cleaning up Jenkins resources..."
docker network rm jenkins || true
docker volume rm jenkins-docker-certs || true
docker volume rm jenkins-data || true
