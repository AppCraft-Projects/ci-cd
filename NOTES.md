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
- Create an administrator user

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
- Set permission for Anonymous User to `Read`, `Create` and `Build`e
