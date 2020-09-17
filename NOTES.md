# Training Notes

## Docker

- Show how Docker Desktop integrates with WSL

## Installing Jenkins

- Explain how the script files work.
- Explain *scripts to rule them all*
- Now call `script/init-services.sh`
- Run the following command to see the password:

```bash
docker exec jenkins-blueocean cat /var/jenkins_home/secrets/initialAdminPassword
```
- Select the plugins you need on the installation screen
- Create an administrator user using password `bwD2!b8BJGEHz6H`

> Note: use the same email + password combination throughout this tutorial!
> Also create a ssh key for that email!

## Installing Bitbucket

- Navigate to `localhost:7990`
- Set up a license
- Use password: `bwD2!b8BJGEHz6H`
- Create a new project named `CICD`
- Create a new repository named `CICD`
- Add or create a ssh key
- Push the project

## Configuring Bitbucket for Jenkins

- In *Bitbucket* create an access token in `Manage Account > Personal access tokens`
    - Add a name
    - Add `Admin` permissions
    - Then create the token. It will look like this: `NDQ1NDI0MzQzMzAzOl2T3EO1kLEaGjeoViIyVYChPNXb`

## Configuring Continuous Integration

- In *Jenkins* Install `Bitbucket Server Integration`
- Go to `Manage Jenkins > Configure System`
- In `Bitbucket Server integration`
    - Use `Bitubcket Server` as name
    - Instance URL is `http://bitbucket:7990`
    - Use the personal access token created above
    - Create a credential using your Bitbucket *username* and *password*
- Click **Save**
- Create a new *Multibranch Pipeline* with name `CICD`
    - In *Branch Sources* use the previously configured Bitbucket Server
    - In *Build Configuration* use mode `by Jenkinsfile`
    - In *Scan Multibranch Pipeline Triggers* select `Bitbucket Server trigger scan after push`
- Click **Save**
- This will trigger a *build*
- Now let's check the webhook that was created
- Check the running *Docker* process with `docker ps`
- Check the logs with `docker logs bitbucket --follow`
- Log into the *Bitbucket* server with `docker exec -it bitbucket bash`
- Try to `curl` the target server (we'll see an authentication problem)
- In *Jenkins* go to `Manage Jenkins > Configure Global Security`
- Select `Project-based Matrix Authorization Strategy` under `Authorization`
- Set permission for Anonymous User to `Read`, `Create` and `Build`

## Configuring PR Workflow

- In *Bitbucket* go to *Branch Permissions* in the `cicd` repository
- Prevent `Changes without a pull request` for the `master` branch
- Try to push to master
- Create a new branch `dev` and create a PR from there
- We will see that the Jenkins build is in progress
- Now break the code and push it again
- This will trigger another build that will fail
- In *Repository Settings* add *Merge checks*
- Now try to merge again

## Setting up Sonarqube Checks

- Log in to SonarQube at `http://sonarqube:9000` with `admin`/`admin`
- Create a project and generate a token. It will look like this: `f529a61c801dfe9e1e848a74f543ea129e4bdfc1`
- Install the `SonarQube Scanner` plugin to *Jenkins*
- Install the `SonarQube` plugin in Bitbucket and set it up with the token above
- Add Sonar to the `Jenkinsfile`:

```
stage('Sonar') {
    steps {
        sh 'mvn -e sonar:sonar \
              -Dsonar.projectKey=cicd \
              -Dsonar.host.url=http://sonarqube:9000 \
              -Dsonar.login=f529a61c801dfe9e1e848a74f543ea129e4bdfc1'
    }
}
```
- Now push the code. We'll see 0% coverage in SonarQube
- Set up Jacoco:

```
stage('Jacoco') {
    steps {
        sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent test --fail-at-end -DskipTests=false -am'
    }
}
```

- Push it and now we'll see proper coverage reports in SonarQube

