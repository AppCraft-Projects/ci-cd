# Training Notes

## Docker

- Show how Docker Desktop integrates with WSL

## Installing Jenkins

- Explain how the `start-jenkins.sh` and `stop-jenkins.sh` files work.
- Explain *scripts to rule them all*
- Now call `script/start-jenkins.sh`
- Run the following command to see the password:

```bash
docker exec jenkins-blueocean cat /var/jenkins_home/secrets/initialAdminPassword
```
- Select the plugins you need on the installation screen
- Create an administrator user

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
    - Then create the token. It will look like this: `NTk5MDEyNTcwNzEyOqJHsz91td4WP3Si4TvklZ1qZos0`


## Configuring Continuous Integration (Jenkins)

- In *Jenkins* Install the following plugins:
    - `Bitbucket Server Integration`
    - `Bitbucket Server Notifier`
- Go to `Manage Jenkins > Configure System`
- In `Bitbucket Server integration`
    - Use `Bitubcket Server` as name
    - Instance URL is `http://bitbucket:7990`
    - Use the personal access token created above
    - Create a credential using your Bitbucket *username* and *password*
- Click **Save**
- Create a new *Pipeline* with name `CICD`
    - In *Build Triggers* select `Bitbucket Server trigger build after push`
    - In *Pipeline* select `Pipeline script from SCM`
    - Select `Bitbucket Server`
    - Use the credentials that were set up above
    - Project name is `CICD`
- 


## Configuring Continuous Integration (Bitbucket)


