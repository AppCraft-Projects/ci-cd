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

## Installing GitLab

- Set `GITLAB_HOME` in `~/.bashrc`

```bash
export GITLAB_HOME=/srv/gitlab
```

- Show how `ssh` keys can be used
- Show how we can push `master` to GitLab now
- Protect `master` branch
- Show how we can't push anymore

## Configuring GitLab for Jenkins

- Create a new user `jenkins`, use password `bwD2!b8BJGEHz6H`
- Add `jenkins` to the `cicd` project
- Impersonate `jenkins`
- Go to `Avatar > Settings > Access Tokens` and create an access token with the `api` scope. It will
  look like this: `BpoqsrgQsVF-gxFkHppb`
- In Jenkins go to `Manage Jenkins > Configure System`
- In the `GitLab` section add credentials using the *Jenkins Credential Provider* and choose *GitLab API Token*
- Paste the API Token
- Save and choose the credential we created. Use `gitlab` as connection name and `http://gitlab` as URL
- Test the connection
- **Save** the config

## Configuring Continuous Integration (Jenkins)

- In *Jenkins* go to *New Item*
- Enter `CICD Demo` as name
- Choose *Freestyle Project*
- Choose the *GitLab* connection
- Check the *Build when a change is pushed to GitLab* checkbox.
- Check the following checkboxes:
  - Accepted Merge Request Events
  - Closed Merge Request Events
- In the Post-build Actions section, choose *Publish build status to GitLab*
- **Save**

## Configuring Continuous Integration (GitLab)

- On *GitLab* go to `Admin Area > Service Templates`
- Pick `Jenkins CI`
- Toggle `Enable Integration`
- Choose `Push`, `Merge Request` and `Tag Push` as *triggers*
- Set *Jenkins url* to `http://jenkins-blueocean:8080`
- Set *Project name* to `CICD Demo`
- Set `username/password`


