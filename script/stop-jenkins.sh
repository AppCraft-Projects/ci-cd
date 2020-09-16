echo "Stopping Jenkins..."
docker container stop jenkins-docker || true
docker container stop jenkins-blueocean || true
