RESULT=$(curl --data "grant_type=password&client_id=curl&username=customer-admin&password=admin" http://192.19.0.3:8080/auth/realms/master/protocol/openid-connect/token)
echo $RESULT
TOKEN=$(echo $RESULT | sed 's/.*access_token":"//g' | sed 's/".*//g')
echo $TOKEN
curl -i -v http://192.19.0.8:8080/api/services --header "Authorization: Bearer $TOKEN"
#curl -i -v http://localhost:8080/api/services