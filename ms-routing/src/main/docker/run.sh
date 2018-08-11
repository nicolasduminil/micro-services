#!/bin/sh
echo "********************************************************"
echo "Waiting for the configuration server to start on port $CONFIGSERVER_PORT"
echo "********************************************************"
while ! `nc -z ms-config $CONFIGSERVER_PORT `; do sleep 3; done
echo ">>>>>>>>>>>> Configuration Server has started"

echo "********************************************************"
echo "Waiting for the discovery server to start on port $DISCOVERY_PORT"
echo "********************************************************"
while ! `nc -z ms-discovery $DISCOVERY_PORT `; do sleep 3; done
echo ">>>>>>>>>>>> Discovery Server has started "

echo "********************************************************"
echo "Starting Routing Service with config on $CONFIGSERVER_URI"
echo "********************************************************"
java -Dserver.port=$SERVER_PORT -Dspring.cloud.config.uri=$CONFIGSERVER_URI -Deureka.client.serviceUrl.defaultZone=$DISCOVERYSERVER_URI -Dspring.profiles.active=$PROFILE -Dspring.cloud.config.label=$CONFIGSERVER_LABEL -jar /usr/local/share/hml/ms-routing.jar
