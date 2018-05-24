Welcome to the first part of the Microservices article series. This first part shows how to implement a microservice with Spring Boot and Spring Cloud. 
Our sample microservice is wrapping an ActiveMQ topic and provides a REST interface to publish/subscribe messages on this topic.
Here are the components:
  - a Spring configuration class (HmlConfig) defining an JmsTemplate and a JMS connection factory.
  - a Spring controller (HmlRestController) which exposes a REST interface able to publish/subscribe messages to the JMS topic.
  - a JMS listener which listens of the topic and forward the received messages to a provided endpoint.
The exchanged messages are of the Java object type holding inside a HmlEvent instance. The domain package contains the HmlEvent 
class together with all the others domain objects.
An unit/integration test allows to validate the whole stuff.

To build this sample proceed as follows:
  - open a command-line window
  - create a directory
  - move to that directory
  - clone the repository by doing the following command:
      ** git clone https://github.com/nicolasduminil/micro-services.git **
  - switch to the first part branch by doing the following command:
      ** git checkout core **
  - change to the right directory by doing the following command:
      ** cd ms-basics
  - build the project by doing the following command:
      ** mvn -DskipTests clean install
  - start the docker containers by doing the following command:
      ** mvn -pl ms-core docker-compose:up
After performing the operations above you'll get two docker containers running, as follows:
  - a container named active-mq running the messaging broker
  - a container named ms-core running our microservice
To test, do the following:
  - open a new command-line window
  - move to the project directory
  - run the following:
    ** mvn test **
In order to get the conatainers IP addresses do the following:
    ** docker inspect <container-name>
where <container-name> is active-mq or ms-core.

You can connect now to the ActiveMQ console at http://<activemq-ip-address>:8161. You can also connect to the main page of the microservice at http://<ms-core-ip-address>:8080/hml

