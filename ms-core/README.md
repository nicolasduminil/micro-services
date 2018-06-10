Welcome to the third part of the Microservices article series. This third part shows how to use a Netflix Eureka service
in order to abstract microservices physical location to consumers.  
Our sample microservice presented in the first and second part was consumed by invoking explicitly its endpoints.
These endpoints consists in physical address (the IP address of the host and the TCP port number), followed by the logical one 
(webapp root context, service URI, etc.).
But for microservices running in a cloud and, hence, having several instances each one running of its own host, with its own TCP
port number, a consumer cannot know the physical location. 
Hence, Spring Cloud provides a full integration with the Netflix Eureka service, which is an auto-discovery service, which responsability
is to serve as a registry of all the running micrservices. At the startime, each microservice registers with the Eureka service which
cashes their physical address. Further, the consumer don't invoke the microservices endpoints directly, but through the Netflix Ribbon API,
which is fully integrated with Spring Cloud as well. This API is responsible to map a logical endpoint to a physical one, performing in the 
same time a load balancing process.

To build this sample proceed as follows:
  - open a command-line window
  - create a directory
  - move to that directory
  - clone the repository by doing the following command:
      ** git clone https://github.com/nicolasduminil/micro-services.git **
  - switch to the third part branch by doing the following command:
      ** git checkout discovery **
  - change to the right directory by doing the following command:
      ** cd ms-core-config-discovery
  - build the project by doing the following command:
      ** mvn -DskipTests clean install
  - start the docker containers by doing the following command:
      ** docker-compose -f docker/common/docker-compose.yml up
After performing the operations above you'll get two docker containers running, as follows:
  - a container named active-mq running the messaging broker
  - a container named ms-config running the configuration microservice
  - a container named ms-discovery running the Eurela service
  - a container named ms-core running a first instance of the core microservice
  - a container named ms-core2 running a second instance of the core microservice
To test, do the following:
  - open a new command-line window
  - move to the project directory
  - run the following:
    ** mvn test **
In order to get the conatainers IP addresses do the following:
    ** docker inspect <container-name>
where <container-name> is active-mq or ms-core.

You can connect now to the ActiveMQ console at http://<activemq-ip-address>:8161. 
You can also connect to the main page of the microservice at http://<ms-core-ip-address>:8080/hml.
You can also connect to the config server at http://<ms-config-ip-address>:8888/hmlConfig/<profile-name> 
in order to see the propeties set associated to the given profile. 
You can connect to the Eureka server at http://<ms-discovery-ip-address>:8761

