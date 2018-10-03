package fr.simplex_software.micro_services.keycloak.services;

import org.keycloak.admin.client.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.stereotype.*;

@Service
public class KeycloakService
{
  private static Logger slf4jLogger = LoggerFactory.getLogger(KeycloakService.class);

  public String getAccessToken(String serverUrl, String realm, String userName, String password, String clientId)
  {
    return Keycloak.getInstance(serverUrl, realm, userName, password, clientId).tokenManager().getAccessToken().getToken();
  }
}
