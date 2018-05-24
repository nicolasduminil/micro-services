#!/bin/sh
echo "********************************************************"
echo "Waiting for the ActiveMQ broker to start on port $BROKER_PORT"
echo "********************************************************"
while ! `nc -z active-mq $BROKER_PORT `; do sleep 3; done
echo ">>>>>>>>>>>> ActiveMQ Broker has started"
java -jar /usr/local/share/hml/ms-core.jar
