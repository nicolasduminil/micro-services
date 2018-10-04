Welcome to the 6th part of the Microservices article series. This 6th part shows how to use a 
Netflix Zuul filters in order to secure micro-services.  
The micro-services used until now were publicly accessible resourcers. In this example we will secure them by 
using the OAuth 2.0 protocol. Please notice that this is not an OAuth 2.0 tutorial and it assumes that the
reader is familiar with it. For more information please see https://oauth.net/2/.  
There are several possible approaches to use the OAuth 2.0 protocol. Here we choose to use the Keycloak implementation.
Please notice also that this is neither a Keycloak tutorial and that we assume the reader is familiar with it. For
more information please see https://www.keycloak.org/.  

To build this sample proceed as follows:
  - open a command-line window
  - create a directory
  - move to that directory
  - clone the repository by doing the following command:  
      ```git clone https://github.com/nicolasduminil/micro-services.git```
  - switch to the 6th part branch by doing the following command:  
      ``` git checkout oauth ```
  - change to the right directory by doing the following command:  
      ```cd ms-core-config-discovery-resilience-routing-oauth ```
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
  - a container named keycloak running the Keycloak 3.4.1 server
  - a container named ms-keycloak which exposes the Keycloak service as a microservice.
To test, do the following:
  - open a new command-line window
  - move to the project directory, for example  
  ```cd ms-core-config-discovery-resilience-routing-oauth```
  - run the following:  
    ```mvn test ```  
The new elements of this 6th part are the two docker containers running the Keycloak server and
the Keycloak microservice. This second one is a Spring Boot application exposing the Keycloak administration client API.
The Zuul microservice has been modified also such that to invoke the Keycloak microservice in order to
obtain an OAuth 2.0 Bearer token. Once obtained on the behalf of the Keycloak microservice, via the Keycloak
administration client API, this token is inserted by the Zuul filter into the HTTP requests. This way the microservices are protected from the public access.We are running in this scenario a new microservice, as a Spring Boot application which main class is RoutingServerApplication.

