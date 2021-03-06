# Training Exercise Notes

## Maven

- Install Maven (if needed)
- Check if Maven is working

```shell script
mvn --version
```

- Create a new project from an archetype

> Note that Gradle doesn't have archetypes!

```shell script
mvn archetype:generate -DgroupId=com.example.app \
 -DartifactId=example-app \
 -DarchetypeArtifactId=maven-archetype-quickstart \
 -DarchetypeVersion=1.4 \
 -DinteractiveMode=false
```

- `cd` into the newly created directory and run `tree`

```shell script
cd example-app
tree
```

- `cat` the contents of `pom.xml`

```shell script
cat pom.xml
```
- Explain what's in there
- Explain Maven's *phases*:
    - `validate`: validate the project is correct and all necessary information is available
    - `compile`:  compile the source code of the project
    - `test`: test the compiled source code using a suitable unit testing framework. These tests
              should not require the code be packaged or deployed
    - `package`: take the compiled code and package it in its distributable format, such as a JAR.
    - `verify`: run any checks on results of integration tests to ensure quality criteria are met
    - `install`: install the package into the local repository, for use as a dependency in other projects locally
    - `deploy`: done in the build environment, copies the final package to the remote repository for sharing
                with other developers and projects.
- Explain how a *phase* consists of *goal*s.
- Explain how *phase*s are executed **in order**
- Install the Maven Bash Completion from 
  [here](https://github.com/juven/maven-bash-completion/blob/master/bash_completion.bash)
- Show the list of phases by using it.
- Show the list of goals in a plugin:

```shell script
mvn help:describe -Dplugin=archetype
```
- Explain plugins
- Add the Maven Shade Plugin with the Manifest Transformer
- Execute the build: `mvn clean package`
- Now the app can be run from `target/`
- Questions?

## Gradle

- Initialize the project:

```shell script
mkdir gradle-demo
cd gradle-demo
gradle init
```
- Look at the project and explain the config
- Explain the *Gradle Wrapper*
- Explain *Project*s and *Tasks*
- Create `myfile.txt` in `src` and add `Hello, World!` to it.
- Create a new task `copy`:

```kotlin
tasks.create<Copy>("copy") {
    description = "Copies sources to the dest directory"
    group = "Custom"

    from("src")
    into("dest")
}
```
- Now execute the task: `./gradlew copy`
- Now apply the `base` plugin that supplies `Zip`

```kotlin
plugins {
    id("base")
}
```
- Now create a `zip` archive from the `src` folder with a task:

```kotlin
tasks.create<Zip>("zip") {
    description = "Archives sources in a zip file"
    group = "Archive"

    from("src")
    archiveFileName.set("basic-demo-1.0.zip")
}
```

- Discover tasks with `./gradlew tasks`
- Discover Gradle properties: `./gradlew properties`
- Change properties in `gradle.properties`:

```properties
description = "A trivial Gradle build"
version = "1.0"
```

- Show a multi-project build and explain `allprojects` and `subprojects`
- Explain the advantages of Gradle:
    - Flexibility (Configuration as code)
    - Performance (Much faster than Maven even without parallel builds)
    - User Experiences is better (discover tasks, scan builds)
    - Dependency Management
- Explain the differences
    - Imperative vs declarative and the loss of effective pom
    - No parent in Gradle
    - No lifecycle (only plugin lifecycle). Show the image

## Docker

- Ask them to pick a tool (like Jenkins, Bitbucket, etc)
- Go do Dockerhub and find the corresponding image
- Use it. Example (GitLab):
- Go to the GitLab site: `https://docs.gitlab.com/omnibus/docker/` and follow the instructions
- Take a look at the running containers: `docker ps`
- Check the logs: `docker logs gitlab --follow`
- Open GitLab in the browser at `http://gitlab` (check `/etc/hosts`!)
- Stop the container: `docker stop gitlab`
- Start it again: `docker start gitlab`
- Get a shell in `gitlab`: `docker exec -it gitlab bash`
- Stop and delete the container: `docker stop gitlab && docker container rm gitlab`
- List remaining containers: `docker container list`
- Now we'll look at running *PostgreSQL* in *Docker*:
- Find the PGSQL docker image on *DockerHub*: `https://hub.docker.com/_/postgres`
- Run Postgres:

```shell script
docker run --name pg --env POSTGRES_PASSWORD=password --detach postgres
```
- Get a shell: `docker exec -it pg bash`
- Run Postgres commands: `psql -U postgres`
- Run commands like `\db`, `\c postgres`, `\q`
- Now let's create a *Docker* image. Run the following commands:

```shell script
git clone https://github.com/dockersamples/node-bulletin-board
cd node-bulletin-board/bulletin-board-app
```
- Create an image from our project:

```shell script
docker build --tag bulletinboard:1.0 .
```

> Note that `.` stands for current directory

- Now create a *container* from the *image*:

```shell script
docker run --publish 8000:8080 --detach --name bb bulletinboard:1.0
```
- Explain what `publish`, `detach` and `name` does
- Open the app!
- Now delete the container with `docker rm --force bb`
- Explain how `--force` doesn't need us to `stop` the container
- Explain the *Dockerfile*:
    - Start `FROM` the pre-existing `node:current-slim` image. This is an official image, built by the node.js vendors 
      and validated by Docker to be a high-quality image containing the Node.js Long Term Support (LTS) interpreter and
      basic dependencies.
    - Use `WORKDIR` to specify that all subsequent actions should be taken from the directory `/usr/src/app` in your image
      filesystem (never the host’s filesystem).
    - `COPY` the file `package.json` from your host to the present location (`.`) in your image (so in this case,
      to `/usr/src/app/package.json`)
    - `RUN` the command npm install inside your image filesystem (which will read `package.json` to determine your
      app’s node dependencies, and install them)
    - `COPY` in the rest of your app’s source code from your host to your image filesystem.
- Mention *Docker Compose* and *Kubernetes*


## Installing Jenkins

- Explain how the script files work.
- Explain *scripts to rule them all*
- Now call `script/init-services.sh`
- Run the following command to see the password:

```shell script
docker exec jenkins-blueocean cat /var/jenkins_home/secrets/initialAdminPassword
```
- Select the plugins you need on the installation screen
- Create an administrator user using password `bwD2!b8BJGEHz6H`

> Note: use the same email + password combination throughout this tutorial!
> Explain why ssh keys won't work

## Installing Bitbucket

- Navigate to `localhost:7990`
- Set up a license
- Use password: `bwD2!b8BJGEHz6H`
- Create a new project named `CICD`
- Create a new repository named `CICD`
- Add or create a ssh key
- Push the project
- Talk about *Git* remotes


## Configuring Bitbucket for Jenkins

- In *Bitbucket* create an access token in `Manage Account > Personal access tokens`
    - Add a name
    - Add `Admin` permissions
    - Then create the token. It will look like this: `NDQ1NDI0MzQzMzAzOl2T3EO1kLEaGjeoViIyVYChPNXb`

## Configuring Continuous Integration

- Add the following `Jenkinsfile` to the project:

```groovy
pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }
}
```

- Explain how *Pipeline*s work in Jenkins
    - `Jenkinsfile`
    - [docs](https://www.jenkins.io/doc/book/pipeline/)
    - Declarative vs scripted (Groovy)
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
- Install the `SonarQube` plugin in Bitbucket and set it up with the token above
    - Set up a trial license (disable Privacy Badger and other similar tools)
- Add Sonar to the `Jenkinsfile`:

```shell script
stage('Sonar') {
    steps {
        sh 'mvn -e sonar:sonar \
              -Dsonar.projectKey=cicd \
              -Dsonar.host.url=http://172.20.0.5:9000 \
              -Dsonar.login=f529a61c801dfe9e1e848a74f543ea129e4bdfc1'
    }
}
```
- Now push the code. We'll see 0% coverage in SonarQube
- Set up Jacoco **before** sonar:

```shell script
stage('Jacoco') {
    steps {
        sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent test --fail-at-end -DskipTests=false -am'
    }
}
```

> Note that the proper reports won't be present in BitBucket because we don't have a license for SonarQube

- Push it and now we'll see proper coverage reports in SonarQube

## Setting Up SonarQube Scanner

- Install the `SonarQube Scanner` plugin to *Jenkins*
    - In `Manage Jenkins > Configure System` go to `SonarQube servers` and set it up
    - Use `Secret Test` for the token
    - Check `Enable injection of SonarQube server configuration as build environment variables`
    - Create a webhook using `http://jenkins-blueocean:8080/sonarqube-webhook/` in *SonarQube*
    - Now replace the `Sonar` stage with the following:

```shell script
stage('Sonar') {
    steps {
        withSonarQubeEnv('sonarqube') {
            sh 'mvn -e sonar:sonar'
        }
    }
}
```

## Heroku Deployment

> We need to install the Heroku CLI here to handle login (we also need to register probably).

- Add the Heroku Maven plugin:

```xml
<plugin>
    <groupId>com.heroku.sdk</groupId>
    <artifactId>heroku-maven-plugin</artifactId>
    <version>3.0.4</version>
    <configuration>
        <jdkVersion>1.8</jdkVersion>
        <processTypes>
            <web>
                java -Dserver.port=$PORT $JAVA_OPTS -jar target/*.jar
            </web>
        </processTypes>
    </configuration>
</plugin>
```
- Add profiles for `staging` and `prod` (also explain profiles):

```xml
<profiles>
	<profile>
		<id>staging</id>
		<build>
			<plugins>
				<plugin>
					<groupId>com.heroku.sdk</groupId>
					<artifactId>heroku-maven-plugin</artifactId>
					<configuration>
						<appName>t360-cicd-staging</appName>
					</configuration>
				</plugin>
			</plugins>
		</build>
	</profile>
	<profile>
		<id>prod</id>
		<build>
			<plugins>
				<plugin>
					<groupId>com.heroku.sdk</groupId>
					<artifactId>heroku-maven-plugin</artifactId>
					<configuration>
						<appName>t360-cicd-prod</appName>
					</configuration>
				</plugin>
			</plugins>
		</build>
	</profile>
</profiles>
```
- Create staging and prod apps:
```shell script
heroku apps:create --region eu t360-cicd-staging --remote staging
heroku apps:create --region eu t360-cicd-prod --remote prod
```

> Note that they'll need to create apps with different names

- Test deployment to `staging`: `mvn heroku:deploy -P staging`

## Continuous Deployment with Jenkins

- Create a Heroku API key
- Add it as a credential to *Jenkins* with the key `HEROKU_API_KEY`
- Add a new stage to the `Jenkinsfile`:

```groovy
stage('Deploy to Staging') {
    environment {
        HEROKU_API_KEY = credentials('HEROKU_API_KEY')
    }
    when {
        branch 'dev'
    }
    steps {
        echo 'Deploying to Staging'
        sh 'mvn heroku:deploy -DskipTests=true -P staging'
    }
}
```
- Add an index page to our app:

> Note: we should talk about **feature branching** here

```java
package com.cicd.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public String hello() {
        return "Hello, world!";
    }
}
```

- Try to deploy it to staging
- Add a new stage for `prod`:

```groovy
stage('Deploy to Prod') {
    environment {
        HEROKU_API_KEY = credentials('HEROKU_API_KEY')
    }
    when {
        beforeInput true
        branch 'master'
    }
    input {
        message "Deploy to production?"
        id "simple-input"
    }
    steps {
        echo 'Deploying to Prod'
        sh 'mvn heroku:deploy -DskipTests=true -P prod'
    }
}
```
- Try it by creating and merging a PR to `master`
- Talk about how rollback can be performed (`git revert` or `reset --hard` to previous commit)
- Show a complex config: `https://gist.github.com/mskutin/3b13e089eedcd365ac9a9af30ef503d2`

## Adding Profiles to Spring

- Refactor our profiles to pass the profile to the Java app:

```xml
<configuration>
	<appName>t360-cicd-prod</appName>
	<processTypes>
		<web>
			java -Dserver.port=$PORT $JAVA_OPTS -jar target/*.jar --spring.profiles.active=prod
		</web>
	</processTypes>
</configuration>
```

- Add initial profiles:

```yaml
---

spring:
  profiles: staging
features:
  profile: staging

---

spring:
  profiles: prod
features:
  profile: prod
```

- Create `FeatureConfig`:

```java
package com.cicd.demo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix="features")
public class FeatureConfig {
    private String profile;
}
```

- Add it to our `IndexController`:

```java
@RestController
public class IndexController {

    private final FeatureConfig featureConfig;

    public IndexController(FeatureConfig featureConfig) {
        this.featureConfig = featureConfig;
    }

    @GetMapping("/")
    public String hello() {
        return String.format("Hello! Profile is: %s, Features are: %s", featureConfig.getProfile(), featureConfig);
    }
}
```

- Build the app, with `mvn clean package`
- Run it with `java -jar target/ci-cd-demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=staging`
- See the results

## Feature Toggles

- Add a new feature to `FeatureConfig`:

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix="features")
public class FeatureConfig {
    private String profile;
    private Boolean deleteProduct;
}
```

- Add the setting in `application.yml`:

```yaml
---

spring:
  profiles: staging
features:
  profile: staging
  deleteProduct: true
---

spring:
  profiles: prod
features:
  profile: prod
  deleteProduct: false
```

- Implement the feature in `ProductController`:

````java
@RestController
public class ProductController {
    
    // ...

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (featureConfig.getDeleteProduct()) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
    }
}
````
- Try it with
    - ` ./mvnw spring-boot:run -Dspring-boot.run.profiles=staging`
    - ` ./mvnw spring-boot:run -Dspring-boot.run.profiles=prod`
- Tell them why `@ConditionalOnProperty` is trash

## Shared Libraries

- Take a look at `CurrencyConverter` and talk about why it is hard to maintain this code if it not shared properly.
- Open nexus at `nexus:8081`
- Find the password on the volume by running

```shell script
docker exec -it nexus cat nexus-data/admin.password
```
- Talk about volumes and how to find info about them:
    - List volumes with `docker volume ls`
    - Talk about cleanup (`docker volume prune`)
    - Inspect a volume with `docker volume inspect nexus-data`
- Now log in to *Nexus* and
    - Set a new password: `bwD2!b8BJGEHz6H`
    - Enable anonymous access
    - Create a new repository called `ci-cd-shared`
    - With *Layout Policy* `Permissive`
    - *Online* `true`
- Then we clone the project `https://github.com/AppCraft-Projects/ci-cd-shared.git`
- Add the *maven-deploy-plugin*:

```xml
<plugin>
	<artifactId>maven-deploy-plugin</artifactId>
	<version>2.8.1</version>
	<executions>
		<execution>
			<id>default-deploy</id>
			<phase>deploy</phase>
			<goals>
				<goal>deploy</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```

- And the snapshot repository:

```xml
<distributionManagement>
    <snapshotRepository>
        <id>nexus</id>
        <url>http://nexus:8081/repository/ci-cd-shared/</url>
    </snapshotRepository>
</distributionManagement>
```

- Then add the username/password to `~/.m2/settings.xml`:

```xml
<servers>
    <server>
        <id>nexus</id>
        <username>admin</username>
        <password>{password here}</password>
    </server>
</servers>
```

- We can also talk about password encryption
- Now try it with `./mvnw clean deploy`
- We can already use this in the original project with the new repository:

```xml
<repositories>
    <repository>
        <id>ci-cd-shared</id>
        <name>ci-cd-shared</name>
        <url>http://nexus:8081/repository/ci-cd-shared/</url>
    </repository>
</repositories>
```
- We have to look for the dependency on *Nexus*
    
## Misc Topics (Optional)

- Implement feature toggle next steps
- Show how accretion works
- Implement API versioning
- Show a Protobuf example

