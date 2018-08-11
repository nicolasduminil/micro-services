Welcome to the 5th part of the Microservices article series. This 5th part shows how to use a 
Netflix Zuul service in order to implement a microservices gateway.  
Until now, while testing our microservices, we have either called them directly or via  the Eureka discovery service.  
A microservices gateway is a mediator between the microservices and their consumers. This way we have a single URL that the consumers call and this provides us one of the Graal of the SOA which is the location transparency.  
Another considerable advantage when using a microservices gateway is that it acts as a single, central, policy enforcement point. Hence, shuld we want to secure the access to the microservices, or should we want to perform advanced logging or tracing, this is the point where we can do it without affecting the global design.  
Spring Cloud integrates with the Zuul Netflix project. This is an open source project providing a microservices gateway such the one we described above. It is very simple to set it up via Sprong Cloud annotations, as follows.

To build this sample proceed as follows:
  - open a command-line window
  - create a directory
  - move to that directory
  - clone the repository by doing the following command:  
      ```git clone https://github.com/nicolasduminil/micro-services.git```
  - switch to the 5th part branch by doing the following command:  
      ``` git checkout routing ```
  - change to the right directory by doing the following command:  
      ```cd ms-core-config-discovery-resilience-routing ```
  - build the project by doing the following command:  
      ```mvn -DskipTests clean install```
  - start the docker containers by doing the following command:  
      ```docker-compose -f docker/common/docker-compose.yml up```  
After performing the operations above you'll get several docker containers running, as follows:
  - a container named active-mq running the messaging broker
  - a container named ms-config running the configuration microservice
  - a container named ms-discovery running the Eureka service
  - a container named ms-core running a first instance of the core microservice
  - a container named ms-core2 running a second instance of the core microservice
  - a container named ms-routing runningthe Zuul service
To test, do the following:
  - open a new command-line window
  - move to the project directory, for example  
  ```cd ms-core-config-discovery-resilience-routing```
  - run the following:  
    ```mvn -pl test ```  
We are running in this scenario a new microservice, as a Spring Boot application which main class is RoutingServerApplication.
This new Spring Boot application is annotated with the @EnableZuulProxy annotation which will take care of all the required details.
The configuration file bootstrap.yml instructs our Zuul service to use the Eureka service for autodiscovery purposes. 
Accordingly, the Eureka autodiscovery service will discover our two microservices instances
and will provide their respective IP address and TCP port to the Zuul service that will cache them.
Now, all that any consumer of our microservices has to do is to call the Zuul service which will route the call to the most appropriated instance of our microservices.  
You can look at the test class HmlRestControllerTest to see how a consumer is supposed to call our microservices.

