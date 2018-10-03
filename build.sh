docker stop ms-routing ms-discovery ms-config ms-core ms-core2 active-mq keycloak ms-keycloak
docker rm ms-routing ms-discovery ms-config ms-core ms-core2 active-mq keycloak ms-keycloak
mvn -DskipTests clean install
docker-compose -f docker/common/docker-compose.yml up -d
for cname in active-mq keycloak ms-config ms-discovery ms-routing ms-keycloak ms-core ms-core2
do
  while ! docker ps -q -f name=$cname > /dev/null 2>&1
  do
    sleep 3
  done
done
sleep 3
docker exec -it keycloak keycloak/customization/customize.sh
#mvn -Dspring.cloud.config.uri=http://192.19.0.2:8888 -Dspring.profiles.active=default -Dspring.cloud.config.label=routing -Deureka.client.serviceUrl.defaultZone=http://192.19.0.5:8761/eureka test
