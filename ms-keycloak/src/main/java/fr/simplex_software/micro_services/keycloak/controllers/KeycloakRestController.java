package fr.simplex_software.micro_services.keycloak.controllers;

import fr.simplex_software.micro_services.keycloak.services.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class KeycloakRestController
{
  private static Logger slf4jLogger = LoggerFactory.getLogger(KeycloakRestController.class);
  @Value("${keycloak.auth-server-url}")
  private String serverUrl;
  @Value("${keycloak.realm}")
  private String realm;
  @Value("${ms-keycloak.user-name}")
  private String userName;
  @Value("${ms-keycloak.user-password}")
  private String userPassword;
  @Value("${ms-keycloak.client-id}")
  private String clientId;
  @Autowired
  private KeycloakService ks;

  @RequestMapping(value = "/accessToken", method = RequestMethod.GET)
  public String getAccessToken()
  {
    slf4jLogger.debug ("*** KeycloakRestController.getAccessToken: Entry {}, {}, {}, {}, {}", serverUrl, realm, userName, userPassword, clientId);
    String accessToken = ks.getAccessToken(serverUrl, realm, userName, userPassword, clientId);
    slf4jLogger.debug ("*** KeycloakRestController.getAccessToken: access token: {}", accessToken);
    return accessToken;
  }
}
