Welcome to the fourth part of the Microservices article series. This fourth part shows how to use a 
Netflix Hystrix Circuit Breaker service in order to fail fast and/or fallback microservices.  
When used for failing fast, the Hystrix Circuit Breaker will avoid calling remote services which
experience degradations, preventing this way resource exhaustion.
When used for fallback, the Hystrix CircuitBreaker will fail gracefully by giving the developer
the possibility to specify an alternative endpoint to the degradated one.

To build this sample proceed as follows:
  - open a command-line window
  - create a directory
  - move to that directory
  - clone the repository by doing the following command:
      ** git clone https://github.com/nicolasduminil/micro-services.git **
  - switch to the third part branch by doing the following command:
      ** git checkout resilience **
  - change to the right directory by doing the following command:
      ** cd ms-core-config-discovery-resilience
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
The remote service has been modified such to simulate a partial degradated service which experiences
performance problems. Hence, the remote endpoint won't be executed but instead the provided alternative
endpoint which simply displays the following message:
"*** HmlRestController.gtsFallbackSubscribe(): No subscribe service available"
This message says that the subscribe service takes too long to answer and hence
an alternative endpoint is called.
Now, you can comment out the fallbackMethod = "gtsFallbackSubscribe" property in the @HystrixCommand
annotation and to build and run again the test. This time you should experience a timeout exception.
This is because there is no anymore alternative endpoint, as you commented out the fallback option, and
hence the Circuit Breaker interrupts the execution of the too long responding endpoint raising a timeout exception.

In order to monitor the Circuit Breaker you can connect to the Hystrix Dashboard at 
http://<service-ip-address><service-tcp-port>/hystrix, where <service-ip-address> is the IP address of the
docker container that you can find using the docker inspect command. <service-tcp-port> is, in our case,
8080 or 8081 for the ms-core and respectivelly ms-core2 microservices.

