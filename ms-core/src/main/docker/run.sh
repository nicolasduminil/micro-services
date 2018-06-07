#!/bin/sh
echo "********************************************************"
echo "Waiting for the configuration server to start on port $CONFIGSERVER_PORT"
echo "********************************************************"
while ! `nc -z ms-config $CONFIGSERVER_PORT `; do sleep 3; done
echo ">>>>>>>>>>>> The Configuration Server has started"

echo "********************************************************"
echo "Waiting for the discovery service to start on port $EUREKASERVER_PORT"
echo "********************************************************"
while ! `nc -z ms-discovery  $EUREKASERVER_PORT`; do sleep 3; done
echo "******* The Discovery Service has started"

echo "********************************************************"
echo "Starting the core service with configuration on :  $CONFIGSERVER_URI"
echo "********************************************************"
java -Dserver.port=$SERVER_PORT -Dspring.cloud.config.uri=$CONFIGSERVER_URI -Dspring.profiles.active=$PROFILE -Dspring.cloud.config.label=$CONFIGSERVER_LABEL -jar /usr/local/share/hml/ms-core.jar
