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

## 

