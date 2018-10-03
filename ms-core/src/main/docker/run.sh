#!/bin/sh
echo "********************************************************"
echo "Waiting for the configuration server to start on port $CONFIGSERVER_PORT"
echo "********************************************************"
while ! `nc -z ms-config $CONFIGSERVER_PORT `; do sleep 3; done
echo ">>>>>>>>>>>> The Configuration Server has started"

echo "********************************************************"
echo "Waiting for the discovery service to start on port $DISCOVERYSERVER_PORT"
echo "********************************************************"
while ! `nc -z ms-discovery  $DISCOVERYSERVER_PORT`; do sleep 3; done
echo "******* The Discovery Service has started"

echo "********************************************************"
echo "Waiting for the routing service to start on port $ROUTINGSERVER_PORT"
echo "********************************************************"
while ! `nc -z ms-routing  $ROUTINGSERVER_PORT`; do sleep 3; done
echo "******* The Routing Service has started"

echo "********************************************************"
echo "Waiting for the keycloak service to start on port $KEYCLOAKSERVER_PORT"
echo "********************************************************"
while ! `nc -z keycloak  $KEYCLOAKSERVER_PORT`; do sleep 3; done
echo "******* The Keycloak Service has started"

echo "********************************************************"
echo "Waiting for the ms-keycloak service to start on port $MS_KEYCLOAKSERVER_PORT"
echo "********************************************************"
while ! `nc -z ms-keycloak  $MS_KEYCLOAKSERVER_PORT`; do sleep 3; done
echo "******* The MS-Keycloak Service has started"

echo "********************************************************"
echo "Starting the core service with configuration on :  $CONFIGSERVER_URI"
echo "********************************************************"
java -Dserver.port=$SERVER_PORT -Dspring.cloud.config.uri=$CONFIGSERVER_URI -Dspring.cloud.config.profile=$PROFILE -Dspring.profiles.active=$PROFILE -Dspring.cloud.config.label=$CONFIGSERVER_LABEL -jar /usr/local/share/hml/ms-core.jar
