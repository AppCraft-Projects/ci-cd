echo "Stopping Jenkins..."
docker container stop jenkins-docker || true
docker container stop jenkins-blueocean || true

echo "Cleaning up Jenkins resources..."
docker network rm jenkins || true
docker volume rm jenkins-docker-certs || true
docker volume rm jenkins-data || true


