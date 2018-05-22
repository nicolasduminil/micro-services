# Introduction
Welcome to my Microservices article series. This project aims at demonstrating some of the most basic microservices related topics. Here you will get introduced to Spring Boot and Spring Cloud quick-starts showing the following:

1.  A quite basic Spring Boot microservice exposing a REST API which emphasizes an ActiveMQ message broker.
2.  A Spring Cloud Configuration Server hosting the properties required by the microservice.
3.  Using auto-discovery with Spring Netflix Eureka server.
4.  Using client side load-balancing with Spring Cloud and Netflix Ribbon.
5.  Applying client resiiency design patterns with Spring Cloud and Netflix Hystrix
6.  Service routing with Spring Cloud and Zuul.
7.  Securing our microservice with OAuth 2.0 and OpenID Connect.
8.  Implementing an event-driven architecture with Spring Cloud Stream.
9.  Monitoring our microservice by distributed logging and tracing wth Spring Cloud Sleuth and Zipkin.
10. Deploying our microservice in the cloud with Amazon ECS.

# Software needed

1.  Java 8 (http://www.oracle.com)
2.  Apache Maven (http://maven.apache.org). 
3.  Docker (http://docker.com).
4.  Docker Compose ((http://docker.com/toolbox)
5.  Git Client (http://git-scm.com).

# Building the Docker Images 
To build the code examples as a docker image, open a command-line window change to the directory where you have downloaded the project source code.

Run the following maven command.  This command will execute the docker-compose-maven-plugin (https://github.com/dkanejs/docker-compose-maven-plugin) defined in the pom.xml file.  

   **mvn clean package docker-compose:up**

If everything starts correctly you should see a bunch of Spring Boot information fly by on standard out.  At this point all of the services needed for the chapter code examples will be running.
