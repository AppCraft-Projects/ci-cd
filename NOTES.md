# Training Notes

## Docker

- Show how Docker Desktop integrates with WSL

## Starting Jenkins

- Explain how the `start-jenkins.sh` and `stop-jenkins.sh` files work.
- Explain *scripts to rule them all*
- Now call `script/start-jenkins.sh`
- Run the following command to see the password:

```bash
docker exec jenkins-blueocean cat /var/jenkins_home/secrets/initialAdminPassword
```
- Select the plugins you need on the installation screen
- Create an administrator user



