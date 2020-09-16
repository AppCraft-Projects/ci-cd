echo "Stopping services..."
docker container stop jenkins-docker
docker container stop jenkins-blueocean
docker container stop bitbucket
docker container stop sonarqube
