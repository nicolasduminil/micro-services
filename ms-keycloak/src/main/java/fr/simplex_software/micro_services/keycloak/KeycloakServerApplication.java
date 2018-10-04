package fr.simplex_software.micro_services.keycloak;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;

@SpringBootApplication
public class KeycloakServerApplication
{
  @LoadBalanced
  @Bean
  public RestTemplate getRestTemplate()
  {
    return new RestTemplate();
  }

  public static void main(String[] args)
  {
    SpringApplication.run(KeycloakServerApplication.class, args);
  }
}
