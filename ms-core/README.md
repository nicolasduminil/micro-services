Welcome to the second part of the Microservices article series. This second part shows how to use a Spring Cloud Config Server
in order to segregate the microservices from their properties.  
Our sample microservice presented in the first part used a property file to define its configuration. This property file belongs to the 
JAR or WAR archive hosting the microservice itself and, hence, in order to modify properties, one needs to rebuild the archive.
Another point is that, while a majority of a microservice properties can be defined in property files, there are always properties
which cannot, because they aren't known at the design/developent time. Take for example a password. A developer cannot know it such to define
it as a property in a property file. It is only known at the deployment time and it's the deployer responsability to define it.
But the build process is not the responsibility of the deployer, who even doesn't have the required tools. 
Hence Spring Cloud provides a configuration server, in the form of a standalone microservice, which defines configuration properties. 
The Spring Cloud Config Server supports a large variety of repositories, including file system based or GIT. In our sample we're using a GIT repository
as the backend for the properties definition.

To build this sample proceed as follows:
  - open a command-line window
  - create a directory
  - move to that directory
  - clone the repository by doing the following command:
      ** git clone https://github.com/nicolasduminil/micro-services.git **
  - switch to the second part branch by doing the following command:
      ** git checkout cconfig **
  - change to the right directory by doing the following command:
      ** cd ms-core-config
  - build the project by doing the following command:
      ** mvn -DskipTests clean install
  - start the docker containers by doing the following command:
      ** docker-compose -f docker/common/docker-compose.yml up
Here above "default" is the build profile. Three profiles are prided, common, dev and prod. In our sample they are all equivalent but,
in a real situation, propertis like the ActiveMQ broker URL, etc. will certainly be different in dev then in prod.
After performing the operations above you'll get two docker containers running, as follows:
  - a container named active-mq running the messaging broker
  - a container named ms-config running the configuration microservice
  - a container named ms-core running the core microservice as shown in the first part
To test, do the following:
  - open a new command-line window
  - move to the project directory
  - run the following:
    ** mvn test **
In order to get the conatainers IP addresses do the following:
    ** docker inspect <container-name>
where <container-name> is active-mq or ms-core.

You can connect now to the ActiveMQ console at http://<activemq-ip-address>:8161. You can also connect to the main page of the microservice at http://<ms-core-ip-address>:8080/hml.
You can also connect to the config server at http://<ms-config-ip-address>:8888/hmlConfig/<profile-name> in order to see the propeties set associated to the given profile.

